package com.inspekpro.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.findViewTreeLifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.inspekpro.R
import com.inspekpro.data.local.entity.InspectionSessionEntity
import com.inspekpro.databinding.ReportItemBinding
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class ReportAdapter(
    private val onItemClick: (InspectionSessionEntity) -> Unit,
    private val onPdfClick: (InspectionSessionEntity) -> Unit,
    private val loadCoverPhoto: suspend (Long) -> String?
) : ListAdapter<InspectionSessionEntity, ReportAdapter.ViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ReportItemBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val session = getItem(position)
        holder.bind(session, onItemClick, onPdfClick, loadCoverPhoto)
    }

    class ViewHolder(private val binding: ReportItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        private val dateFormat = SimpleDateFormat("dd MMM yyyy", Locale.getDefault())

        fun bind(
            session: InspectionSessionEntity,
            onItemClick: (InspectionSessionEntity) -> Unit,
            onPdfClick: (InspectionSessionEntity) -> Unit,
            loadCoverPhoto: suspend (Long) -> String?
        ) {
            val context = itemView.context
            binding.tvMachineName.text = session.title
            binding.tvLocation.text = session.locationName
            
            val formattedDate = dateFormat.format(Date(session.scheduledDate))
            val timeFormat = SimpleDateFormat("HH:mm", Locale.getDefault())
            val formattedTime = timeFormat.format(Date(session.scheduledDate))
            binding.tvDate.text = "$formattedDate, $formattedTime"
            binding.tvInspector.text = session.inspectorName

            // Setup cover image loading asynchronously using lifecycle scope of ViewTree
            binding.ivCover.setImageResource(R.drawable.ic_report)
            binding.ivCover.imageTintList = android.content.res.ColorStateList.valueOf(android.graphics.Color.parseColor("#64748B"))
            
            itemView.findViewTreeLifecycleOwner()?.lifecycleScope?.launch {
                val path = loadCoverPhoto(session.sessionId)
                if (path != null) {
                    binding.ivCover.imageTintList = null
                    Glide.with(context)
                        .load(path)
                        .placeholder(R.drawable.ic_report)
                        .error(R.drawable.ic_report)
                        .centerCrop()
                        .into(binding.ivCover)
                } else {
                    binding.ivCover.setImageResource(R.drawable.ic_report)
                    binding.ivCover.imageTintList = android.content.res.ColorStateList.valueOf(android.graphics.Color.parseColor("#64748B"))
                }
            }

            // Click listener settings
            binding.btnView.setOnClickListener { onItemClick(session) }
            binding.btnPdf.setOnClickListener { onPdfClick(session) }
            binding.cardReport.setOnClickListener { onItemClick(session) }
        }
    }

    companion object DiffCallback : DiffUtil.ItemCallback<InspectionSessionEntity>() {
        override fun areItemsTheSame(oldItem: InspectionSessionEntity, newItem: InspectionSessionEntity): Boolean {
            return oldItem.sessionId == newItem.sessionId
        }

        override fun areContentsTheSame(oldItem: InspectionSessionEntity, newItem: InspectionSessionEntity): Boolean {
            return oldItem == newItem
        }
    }
}
