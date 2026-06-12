package com.inspekpro.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.inspekpro.R
import com.inspekpro.data.local.entity.FindingSeverity
import com.inspekpro.data.local.entity.FindingStatus
import com.inspekpro.data.local.entity.InspectionFindingEntity
import com.inspekpro.databinding.ItemNewFindingBinding
import java.text.SimpleDateFormat
import java.util.*

class NewFindingAdapter(
    private val onItemClick: (InspectionFindingEntity) -> Unit
) : ListAdapter<InspectionFindingEntity, NewFindingAdapter.ViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemNewFindingBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val finding = getItem(position)
        holder.bind(finding, onItemClick)
    }

    class ViewHolder(private val binding: ItemNewFindingBinding) :
        RecyclerView.ViewHolder(binding.root) {

        private val dateFormat = SimpleDateFormat("dd MMMM yyyy", Locale.getDefault())

        fun bind(finding: InspectionFindingEntity, onItemClick: (InspectionFindingEntity) -> Unit) {
            val context = itemView.context
            binding.tvFindingTitle.text = finding.title
            binding.tvFindingCategory.text = finding.category
            binding.tvFindingDate.text = dateFormat.format(Date(finding.createdAt))

            // Severity Badge Color Setup
            val (sevBg, sevText) = when (finding.severity) {
                FindingSeverity.CRITICAL -> Pair(R.color.status_critical_bg, R.color.status_critical_text)
                FindingSeverity.MAJOR -> Pair(R.color.status_high_bg, R.color.status_high_text)
                FindingSeverity.MINOR -> Pair(R.color.status_medium_bg, R.color.status_medium_text)
                FindingSeverity.OBSERVATION -> Pair(R.color.status_low_bg, R.color.status_low_text)
            }
            binding.severityBadge.text = when (finding.severity) {
                FindingSeverity.CRITICAL -> "Critical"
                FindingSeverity.MAJOR -> "High"
                FindingSeverity.MINOR -> "Medium"
                FindingSeverity.OBSERVATION -> "Low"
            }
            binding.severityBadge.backgroundTintList = ContextCompat.getColorStateList(context, sevBg)
            binding.severityBadge.setTextColor(ContextCompat.getColor(context, sevText))

            // Status Badge Color Setup
            val (statBg, statText) = when (finding.status) {
                FindingStatus.OPEN -> Pair(R.color.status_progress_bg, R.color.status_progress_text)
                FindingStatus.IN_PROGRESS -> Pair(R.color.status_progress_bg, R.color.status_progress_text)
                FindingStatus.RESOLVED -> Pair(R.color.status_completed_bg, R.color.status_completed_text)
                FindingStatus.CLOSED -> Pair(R.color.status_completed_bg, R.color.status_completed_text)
                FindingStatus.DEFERRED -> Pair(R.color.status_pending_bg, R.color.status_pending_text)
            }
            binding.statusBadge.text = when (finding.status) {
                FindingStatus.OPEN -> "Open"
                FindingStatus.IN_PROGRESS -> "In Review" // or In Progress
                FindingStatus.RESOLVED -> "Resolved"
                FindingStatus.CLOSED -> "Closed"
                FindingStatus.DEFERRED -> "Deferred"
            }
            binding.statusBadge.backgroundTintList = ContextCompat.getColorStateList(context, statBg)
            binding.statusBadge.setTextColor(ContextCompat.getColor(context, statText))

            itemView.setOnClickListener { onItemClick(finding) }
        }
    }

    companion object DiffCallback : DiffUtil.ItemCallback<InspectionFindingEntity>() {
        override fun areItemsTheSame(oldItem: InspectionFindingEntity, newItem: InspectionFindingEntity): Boolean {
            return oldItem.findingId == newItem.findingId
        }

        override fun areContentsTheSame(oldItem: InspectionFindingEntity, newItem: InspectionFindingEntity): Boolean {
            return oldItem == newItem
        }
    }
}
