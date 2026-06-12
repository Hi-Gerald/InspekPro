package com.inspekpro.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.inspekpro.databinding.ItemChecklistFormBinding

class ChecklistItemAdapter(
    private val onItemCheckedChange: (Int, Boolean) -> Unit
) : ListAdapter<Pair<String, Boolean>, ChecklistItemAdapter.ViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemChecklistFormBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item, position, onItemCheckedChange)
    }

    class ViewHolder(private val binding: ItemChecklistFormBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(
            item: Pair<String, Boolean>,
            position: Int,
            onItemCheckedChange: (Int, Boolean) -> Unit
        ) {
            binding.tvChecklistItemTitle.text = item.first
            binding.cbChecklistItem.setOnCheckedChangeListener(null)
            binding.cbChecklistItem.isChecked = item.second
            binding.cbChecklistItem.setOnCheckedChangeListener { _, isChecked ->
                onItemCheckedChange(position, isChecked)
            }
        }
    }

    companion object DiffCallback : DiffUtil.ItemCallback<Pair<String, Boolean>>() {
        override fun areItemsTheSame(oldItem: Pair<String, Boolean>, newItem: Pair<String, Boolean>): Boolean {
            return oldItem.first == newItem.first
        }

        override fun areContentsTheSame(oldItem: Pair<String, Boolean>, newItem: Pair<String, Boolean>): Boolean {
            return oldItem == newItem
        }
    }
}
