package com.inspekpro.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.inspekpro.data.local.entity.InspectionSessionEntity
import com.inspekpro.databinding.ItemScheduleCardBinding
import java.text.SimpleDateFormat
import java.util.Locale

/**
 * Bagian Billy: Adapter untuk list jadwal inspeksi horizontal (Today's Schedule)
 */
class ScheduleTodayAdapter(private val onItemClick: (InspectionSessionEntity) -> Unit) :
    ListAdapter<InspectionSessionEntity, ScheduleTodayAdapter.ViewHolder>(DiffCallback) {

    class ViewHolder(private val binding: ItemScheduleCardBinding) :
        RecyclerView.ViewHolder(binding.root) {
        
        private val timeFormat = SimpleDateFormat("HH.mm", Locale.getDefault())

        fun bind(session: InspectionSessionEntity, onItemClick: (InspectionSessionEntity) -> Unit) {
            binding.tvScheduleTitle.text = session.title
            binding.tvScheduleLocation.text = session.locationName
            binding.tvScheduleTime.text = timeFormat.format(session.scheduledDate)
            
            binding.root.setOnClickListener { onItemClick(session) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemScheduleCardBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position), onItemClick)
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
