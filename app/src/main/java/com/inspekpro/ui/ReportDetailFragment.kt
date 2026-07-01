package com.inspekpro.ui

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.print.PageRange
import android.print.PrintAttributes
import android.print.PrintDocumentAdapter
import android.print.PrintDocumentInfo
import android.print.PrintManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.inspekpro.R
import com.inspekpro.data.local.entity.InspectionFindingEntity
import com.inspekpro.data.local.entity.InspectionSessionEntity
import com.inspekpro.data.local.entity.SessionSummaryEntity
import com.inspekpro.databinding.FragmentReportDetailBinding
import com.inspekpro.ui.viewmodel.ProfileViewModel
import com.inspekpro.ui.viewmodel.ReportViewModel
import com.inspekpro.util.PdfGeneratorService
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

@AndroidEntryPoint
class ReportDetailFragment : Fragment() {

    private var _binding: FragmentReportDetailBinding? = null
    private val binding get() = _binding!!

    private val viewModel: ReportViewModel by viewModels()
    private val profileViewModel: ProfileViewModel by viewModels()

    private var sessionId: Long = -1L
    private var currentSession: InspectionSessionEntity? = null
    private var currentSummary: SessionSummaryEntity? = null
    private var currentFindings: List<InspectionFindingEntity> = emptyList()

    private lateinit var photoAdapter: PhotoAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentReportDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        sessionId = arguments?.getLong("sessionId") ?: -1L
        if (sessionId == -1L) {
            Toast.makeText(requireContext(), "Laporan tidak ditemukan", Toast.LENGTH_SHORT).show()
            findNavController().popBackStack()
            return
        }

        setupRecyclerView()
        setupClickListeners()
        observeViewModel()
    }

    private fun setupRecyclerView() {
        photoAdapter = PhotoAdapter(
            onRemoveClick = null, // Detail view, no remove option
            onItemClick = { path ->
                // Visualise photo preview
                showPhotoPreview(path)
            }
        )
        binding.rvPhotos.apply {
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            adapter = photoAdapter
        }
    }

    private fun setupClickListeners() {
        binding.btnBack.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.btnDownloadPdf.setOnClickListener {
            generateAndOpenPdf()
        }

        binding.btnPrint.setOnClickListener {
            printPdfReport()
        }
    }

    private fun observeViewModel() {
        // Observe Session Details
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.getSessionById(sessionId).collectLatest { session ->
                    session?.let {
                        currentSession = it
                        bindSessionDetails(it)
                    }
                }
            }
        }

        // Observe Session Summary
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.getSessionSummary(sessionId).collectLatest { summary ->
                    summary?.let {
                        currentSummary = it
                        bindSummaryDetails(it)
                    }
                }
            }
        }

        // Observe Findings and Photos
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.getFindingsForSession(sessionId).collectLatest { findings ->
                    currentFindings = findings
                    
                    // Bind finding counts & details
                    val photoPaths = findings.flatMap { parsePhotoPaths(it.photoPaths) }.distinct()
                    photoAdapter.submitList(photoPaths)

                    // Bind random cover image
                    if (photoPaths.isNotEmpty()) {
                        val randomCover = photoPaths.random()
                        Glide.with(requireContext())
                            .load(randomCover)
                            .placeholder(R.drawable.ic_report)
                            .into(binding.ivCover)
                    } else {
                        binding.ivCover.setImageResource(R.drawable.ic_report)
                    }

                    // Display priority
                    if (findings.isNotEmpty()) {
                        val highestPriority = findings.maxByOrNull { it.severity.ordinal }?.severity?.name ?: "MINOR"
                        binding.tvPriority.text = when (highestPriority) {
                            "CRITICAL" -> "Kritis"
                            "MAJOR" -> "Mayor"
                            "MINOR" -> "Minor"
                            else -> "Observasi"
                        }
                    } else {
                        binding.tvPriority.text = "Normal"
                    }
                }
            }
        }
    }

    private fun bindSessionDetails(session: InspectionSessionEntity) {
        val dateFormat = SimpleDateFormat("dd MMMM yyyy", Locale("in", "ID"))
        val timeFormat = SimpleDateFormat("HH:mm", Locale("in", "ID"))
        val yearStr = SimpleDateFormat("yyyy", Locale.getDefault()).format(Date(session.scheduledDate))

        binding.tvReportId.text = "REP-$yearStr-${String.format("%04d", session.sessionId)}"
        binding.tvMachineName.text = session.title
        binding.tvLocation.text = session.locationName
        binding.tvInspector.text = session.inspectorName
        binding.tvDate.text = dateFormat.format(Date(session.scheduledDate))
        binding.tvNotes.text = session.notes.ifBlank { "Tidak ada catatan kesimpulan tambahan." }
    }

    private fun bindSummaryDetails(summary: SessionSummaryEntity) {
        binding.tvChecklistSummary.text = "${summary.passCount} dari ${summary.totalFindings} pemeriksaan lolos"
        binding.tvFindingsSummary.text = "${summary.failCount} temuan tercatat"
    }

    private fun generateAndOpenPdf() {
        val session = currentSession ?: return
        
        binding.loadingOverlay.visibility = View.VISIBLE
        
        viewLifecycleOwner.lifecycleScope.launch {
            try {
                val user = profileViewModel.activeUser.value
                val userId = user?.userId ?: 0L
                val companyName = user?.companyName ?: "InspekPro Client"

                val result = PdfGeneratorService.generateAndOpenReport(
                    context = requireContext(),
                    session = session,
                    summary = currentSummary,
                    findings = currentFindings,
                    userId = userId,
                    companyName = companyName
                )

                binding.loadingOverlay.visibility = View.GONE
                if (result.isSuccess) {
                    Toast.makeText(requireContext(), "PDF Laporan berhasil dibuat!", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(requireContext(), "Gagal: ${result.exceptionOrNull()?.message}", Toast.LENGTH_LONG).show()
                }
            } catch (e: Exception) {
                binding.loadingOverlay.visibility = View.GONE
                Toast.makeText(requireContext(), "Error: ${e.message}", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun printPdfReport() {
        val session = currentSession ?: return
        
        Toast.makeText(requireContext(), "Menyiapkan Dokumen Cetak...", Toast.LENGTH_SHORT).show()
        
        viewLifecycleOwner.lifecycleScope.launch {
            try {
                val user = profileViewModel.activeUser.value
                val userId = user?.userId ?: 0L
                val companyName = user?.companyName ?: "InspekPro Client"

                // Create the PDF file first
                val file = com.inspekpro.util.PdfGenerator.generatePdf(
                    context = requireContext(),
                    session = session,
                    summary = currentSummary,
                    findings = currentFindings,
                    userId = userId,
                    companyName = companyName
                )

                // Trigger Android Print Framework
                val printManager = requireContext().getSystemService(Context.PRINT_SERVICE) as PrintManager
                val jobName = "${getString(R.string.app_name)} Report ${session.sessionCode}"
                val printAdapter = object : PrintDocumentAdapter() {
                    override fun onLayout(
                        oldAttributes: PrintAttributes?,
                        newAttributes: PrintAttributes?,
                        cancellationSignal: android.os.CancellationSignal?,
                        callback: LayoutResultCallback?,
                        extras: Bundle?
                    ) {
                        if (cancellationSignal?.isCanceled == true) {
                            callback?.onLayoutCancelled()
                            return
                        }
                        val info = PrintDocumentInfo.Builder(file.name)
                            .setContentType(PrintDocumentInfo.CONTENT_TYPE_DOCUMENT)
                            .setPageCount(PrintDocumentInfo.PAGE_COUNT_UNKNOWN)
                            .build()
                        callback?.onLayoutFinished(info, true)
                    }

                    override fun onWrite(
                        pages: Array<out PageRange>?,
                        destination: android.os.ParcelFileDescriptor?,
                        cancellationSignal: android.os.CancellationSignal?,
                        callback: WriteResultCallback?
                    ) {
                        var input: java.io.InputStream? = null
                        var output: java.io.OutputStream? = null
                        try {
                            input = java.io.FileInputStream(file)
                            output = java.io.FileOutputStream(destination?.fileDescriptor)
                            val buf = ByteArray(1024)
                            var bytesRead: Int
                            while (input.read(buf).also { bytesRead = it } >= 0) {
                                output.write(buf, 0, bytesRead)
                            }
                            callback?.onWriteFinished(arrayOf(PageRange.ALL_PAGES))
                        } catch (e: Exception) {
                            callback?.onWriteFailed(e.message)
                        } finally {
                            input?.close()
                            output?.close()
                        }
                    }
                }
                
                printManager.print(jobName, printAdapter, PrintAttributes.Builder().build())
            } catch (e: Exception) {
                Toast.makeText(requireContext(), "Gagal mencetak: ${e.message}", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun showPhotoPreview(path: String) {
        val dialog = android.app.Dialog(requireContext(), android.R.style.Theme_Black_NoTitleBar_Fullscreen)
        dialog.setContentView(R.layout.dialog_media_preview)
        
        val imageView = dialog.findViewById<android.widget.ImageView>(R.id.ivPreview)
        val videoView = dialog.findViewById<android.widget.VideoView>(R.id.vvPreview)
        val btnClose = dialog.findViewById<android.widget.ImageButton>(R.id.btnClose)
        val root = dialog.findViewById<View>(R.id.previewRoot)

        dialog.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)

        imageView.visibility = View.VISIBLE
        videoView.visibility = View.GONE
        
        Glide.with(requireContext())
            .load(path)
            .into(imageView)

        btnClose.setOnClickListener { dialog.dismiss() }
        dialog.show()
    }

    private fun parsePhotoPaths(jsonStr: String): List<String> {
        if (jsonStr.isBlank()) return emptyList()
        return try {
            val jsonArray = org.json.JSONArray(jsonStr)
            val list = mutableListOf<String>()
            for (i in 0 until jsonArray.length()) {
                list.add(jsonArray.getString(i))
            }
            list
        } catch (e: Exception) {
            if (jsonStr.contains(",")) {
                jsonStr.split(",").map { it.trim() }.filter { it.isNotBlank() }
            } else {
                listOf(jsonStr.trim()).filter { it.isNotBlank() }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
