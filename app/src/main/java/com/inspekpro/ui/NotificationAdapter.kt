package com.inspekpro.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.inspekpro.data.local.entity.InspectionSessionEntity
import com.inspekpro.databinding.ItemNotificationBinding

/**
 * Bagian Billy: Adapter untuk menampilkan notifikasi jadwal inspeksi mendatang
 */
class NotificationAdapter(private val onItemClick: (InspectionSessionEntity) -> Unit) :
    ListAdapter<InspectionSessionEntity, NotificationAdapter.ViewHolder>(DiffCallback) {

    class ViewHolder(private val binding: ItemNotificationBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(session: InspectionSessionEntity, onItemClick: (InspectionSessionEntity) -> Unit) {
            binding.tvTitle.text = "Upcoming Schedule: ${session.title}"
            binding.tvSubtitle.text = "Inspeksi akan dimulai dalam 15 menit - ${session.locationName}"
            
            binding.root.setOnClickListener { onItemClick(session) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemNotificationBinding.inflate(
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
