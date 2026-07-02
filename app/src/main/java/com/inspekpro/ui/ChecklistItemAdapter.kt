package com.inspekpro.ui

import android.annotation.SuppressLint
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.inspekpro.databinding.ItemChecklistFormBinding

import java.util.UUID

/**
 * Bagian Billy: Adapter Item Checklist
 * Fitur: Menampilkan daftar item pemeriksaan dinamis pada form tambah jadwal.
 */
data class ChecklistItem(
    val id: String = UUID.randomUUID().toString(),
    var title: String,
    var isChecked: Boolean
)

class ChecklistItemAdapter(
    private val onStartDrag: (RecyclerView.ViewHolder) -> Unit,
    private val onItemChanged: (Int, String, Boolean) -> Unit
) : ListAdapter<ChecklistItem, ChecklistItemAdapter.ViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemChecklistFormBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item, onStartDrag, onItemChanged)
    }

    class ViewHolder(private val binding: ItemChecklistFormBinding) :
        RecyclerView.ViewHolder(binding.root) {

        private var textWatcher: TextWatcher? = null

        @SuppressLint("ClickableViewAccessibility")
        fun bind(
            item: ChecklistItem,
            onStartDrag: (RecyclerView.ViewHolder) -> Unit,
            onItemChanged: (Int, String, Boolean) -> Unit
        ) {
            // Remove previous watcher to avoid multiple triggers during recycling
            textWatcher?.let { binding.etChecklistItemTitle.removeTextChangedListener(it) }
            
            binding.etChecklistItemTitle.setText(item.title)
            binding.cbChecklistItem.setOnCheckedChangeListener(null)
            binding.cbChecklistItem.isChecked = item.isChecked

            textWatcher = object : TextWatcher {
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    val pos = adapterPosition
                    if (pos != RecyclerView.NO_POSITION) {
                        item.title = s.toString()
                        onItemChanged(pos, s.toString(), binding.cbChecklistItem.isChecked)
                    }
                }
                override fun afterTextChanged(s: Editable?) {}
            }
            binding.etChecklistItemTitle.addTextChangedListener(textWatcher)

            binding.cbChecklistItem.setOnCheckedChangeListener { _, isChecked ->
                val pos = adapterPosition
                if (pos != RecyclerView.NO_POSITION) {
                    item.isChecked = isChecked
                    onItemChanged(pos, binding.etChecklistItemTitle.text.toString(), isChecked)
                }
            }

            binding.ivDragHandle.setOnTouchListener { _, event ->
                if (event.actionMasked == MotionEvent.ACTION_DOWN) {
                    onStartDrag(this)
                }
                false
            }
        }
    }

    companion object DiffCallback : DiffUtil.ItemCallback<ChecklistItem>() {
        override fun areItemsTheSame(oldItem: ChecklistItem, newItem: ChecklistItem): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: ChecklistItem, newItem: ChecklistItem): Boolean {
            return oldItem == newItem
        }
    }
}
