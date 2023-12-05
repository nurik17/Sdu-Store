package com.example.sdustore.ui.category.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.sdustore.databinding.ScrollItemBinding

class ScrollImageAdapter() :
    ListAdapter<String, ScrollImageAdapter.ScrollImageViewHolder>(DiffUtilCallBack()) {

    fun setData(image: List<String>) {
        submitList(image)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ScrollImageViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ScrollItemBinding.inflate(inflater, parent, false)
        return ScrollImageViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ScrollImageViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    class ScrollImageViewHolder(private val binding: ScrollItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: String) {
            Glide.with(itemView)
                .load(item)
                .into(binding.scrollImage)
        }
    }

    class DiffUtilCallBack : DiffUtil.ItemCallback<String>() {
        override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem == newItem
        }
    }
}

