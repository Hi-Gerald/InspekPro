package com.inspekpro.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.inspekpro.databinding.ItemPhotoFormBinding

/**
 * Bagian Billy: Adapter Foto Dokumentasi
 * Fitur: Menampilkan preview foto-foto lampiran pada laporan inspeksi.
 */
class PhotoAdapter(
    private val onRemoveClick: (Int) -> Unit
) : ListAdapter<String, PhotoAdapter.ViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemPhotoFormBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val path = getItem(position)
        holder.bind(path, position, onRemoveClick)
    }

    class ViewHolder(private val binding: ItemPhotoFormBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(path: String, position: Int, onRemoveClick: (Int) -> Unit) {
            Glide.with(itemView.context)
                .load(path)
                .centerCrop()
                .into(binding.ivThumbnail)

            binding.btnRemovePhoto.setOnClickListener {
                onRemoveClick(position)
            }
        }
    }

    companion object DiffCallback : DiffUtil.ItemCallback<String>() {
        override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem == newItem
        }
    }
}
