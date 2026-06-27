package com.inspekpro.ui

import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.inspekpro.databinding.ItemChecklistFormBinding

/**
 * Bagian Billy: Adapter Item Checklist
 * Fitur: Menampilkan daftar item pemeriksaan dinamis pada form tambah jadwal.
 */
class ChecklistItemAdapter(
    private val onItemChanged: (Int, String, Boolean) -> Unit
) : ListAdapter<Pair<String, Boolean>, ChecklistItemAdapter.ViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemChecklistFormBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item, position, onItemChanged)
    }

    class ViewHolder(private val binding: ItemChecklistFormBinding) :
        RecyclerView.ViewHolder(binding.root) {

        private var textWatcher: TextWatcher? = null

        fun bind(
            item: Pair<String, Boolean>,
            position: Int,
            onItemChanged: (Int, String, Boolean) -> Unit
        ) {
            // Remove previous watcher to avoid multiple triggers during recycling
            textWatcher?.let { binding.etChecklistItemTitle.removeTextChangedListener(it) }
            
            binding.etChecklistItemTitle.setText(item.first)
            binding.cbChecklistItem.setOnCheckedChangeListener(null)
            binding.cbChecklistItem.isChecked = item.second

            textWatcher = object : TextWatcher {
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    onItemChanged(adapterPosition, s.toString(), binding.cbChecklistItem.isChecked)
                }
                override fun afterTextChanged(s: Editable?) {}
            }
            binding.etChecklistItemTitle.addTextChangedListener(textWatcher)

            binding.cbChecklistItem.setOnCheckedChangeListener { _, isChecked ->
                onItemChanged(adapterPosition, binding.etChecklistItemTitle.text.toString(), isChecked)
            }
        }
    }

    companion object DiffCallback : DiffUtil.ItemCallback<Pair<String, Boolean>>() {
        override fun areItemsTheSame(oldItem: Pair<String, Boolean>, newItem: Pair<String, Boolean>): Boolean {
            // In a real app, use a unique ID. Here we use position or content with caution.
            return false // Force rebind for simplicity in this demo form
        }

        override fun areContentsTheSame(oldItem: Pair<String, Boolean>, newItem: Pair<String, Boolean>): Boolean {
            return oldItem == newItem
        }
    }
}
