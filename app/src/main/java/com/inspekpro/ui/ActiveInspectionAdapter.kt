package com.inspekpro.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.inspekpro.R
import com.inspekpro.data.local.entity.InspectionSessionEntity
import com.inspekpro.data.local.entity.SessionStatus
import com.inspekpro.databinding.ItemActiveInspectionBinding
import java.text.SimpleDateFormat
import java.util.*

class ActiveInspectionAdapter(
    private val onItemClick: (InspectionSessionEntity) -> Unit
) : ListAdapter<InspectionSessionEntity, ActiveInspectionAdapter.ViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemActiveInspectionBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return ViewHolder(binding)
     }

    // Correct Kotlin ListAdapter binding
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val session = getItem(position)
        holder.bind(session, onItemClick)
    }

    class ViewHolder(private val binding: ItemActiveInspectionBinding) :
        RecyclerView.ViewHolder(binding.root) {

        private val dateFormat = SimpleDateFormat("dd MMM yyyy, HH:mm", Locale.getDefault())

        fun bind(session: InspectionSessionEntity, onItemClick: (InspectionSessionEntity) -> Unit) {
            val context = itemView.context
            binding.tvSessionTitle.text = session.title
            binding.tvSessionLocation.text = session.locationName
            binding.tvSessionDate.text = dateFormat.format(Date(session.scheduledDate))

            // Progress calculation
            val progress = if (session.totalItems > 0) {
                ((session.passedItems + session.failedItems).toDouble() / session.totalItems * 100).toInt()
            } else {
                // If it is 0, let's show a simulated progress if status is completed, or standard 0%
                if (session.status == SessionStatus.COMPLETED) 100 else 0
            }

            binding.sessionProgressBar.progress = progress
            binding.tvProgressPercentage.text = "$progress%"

            // Badges & Colors based on Status
            val (bgColor, textColor, progressColor) = when (session.status) {
                SessionStatus.COMPLETED -> Triple(
                    R.color.status_completed_bg,
                    R.color.status_completed_text,
                    R.color.status_completed_text
                )
                SessionStatus.IN_PROGRESS -> Triple(
                    R.color.status_progress_bg,
                    R.color.status_progress_text,
                    R.color.primary
                )
                SessionStatus.DRAFT -> Triple(
                    R.color.status_pending_bg,
                    R.color.status_pending_text,
                    R.color.status_pending_text
                )
                SessionStatus.CANCELLED -> Triple(
                    R.color.border_light,
                    R.color.text_secondary,
                    R.color.text_secondary
                )
            }

            binding.tvStatusBadge.text = when (session.status) {
                SessionStatus.COMPLETED -> "Completed"
                SessionStatus.IN_PROGRESS -> "In Progress"
                SessionStatus.DRAFT -> "Pending"
                SessionStatus.CANCELLED -> "Cancelled"
            }

            binding.tvStatusBadge.backgroundTintList = ContextCompat.getColorStateList(context, bgColor)
            binding.tvStatusBadge.setTextColor(ContextCompat.getColor(context, textColor))
            binding.sessionProgressBar.progressTintList = ContextCompat.getColorStateList(context, progressColor)

            binding.btnDetails.setOnClickListener { onItemClick(session) }
            itemView.setOnClickListener { onItemClick(session) }
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
