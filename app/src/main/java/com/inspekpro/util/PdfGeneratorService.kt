package com.inspekpro.util

import android.content.Context
import android.content.Intent
import androidx.core.content.FileProvider
import com.inspekpro.data.local.entity.InspectionFindingEntity
import com.inspekpro.data.local.entity.InspectionSessionEntity
import com.inspekpro.data.local.entity.SessionSummaryEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File

object PdfGeneratorService {

    suspend fun generateAndOpenReport(
        context: Context,
        session: InspectionSessionEntity,
        summary: SessionSummaryEntity?,
        findings: List<InspectionFindingEntity>,
        userId: Long,
        companyName: String
    ): Result<File> = withContext(Dispatchers.IO) {
        try {
            val file = PdfGenerator.generatePdf(
                context = context,
                session = session,
                summary = summary,
                findings = findings,
                userId = userId,
                companyName = companyName
            )
            
            // Open the PDF directly using FileProvider
            openPdfFile(context, file)
            
            Result.success(file)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    fun openPdfFile(context: Context, file: File) {
        try {
            val authority = "${context.packageName}.fileprovider"
            val uri = FileProvider.getUriForFile(context, authority, file)
            val intent = Intent(Intent.ACTION_VIEW).apply {
                setDataAndType(uri, "application/pdf")
                addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            }
            context.startActivity(intent)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}
