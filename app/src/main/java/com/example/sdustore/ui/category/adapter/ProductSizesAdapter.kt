package com.example.sdustore.ui.category.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.sdustore.R
import com.example.sdustore.databinding.SizesItemBinding

class ProductSizesAdapter(): ListAdapter<String, ProductSizesAdapter.SizeViewHolder>(DiffUtilCallBack()) {

    private var selectedPosition = RecyclerView.NO_POSITION

    fun setData(sizeList: List<String>) {
        submitList(sizeList)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SizeViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = SizesItemBinding.inflate(inflater, parent, false)
        return SizeViewHolder(binding)
    }

    @SuppressLint("ResourceAsColor")
    override fun onBindViewHolder(holder: SizeViewHolder, position: Int) {
        val size = getItem(position)
        holder.binding.sizeName.text = size

        if (position == selectedPosition) {
            holder.binding.root.setBackgroundResource(R.color.black)
            holder.binding.sizeName.setTextColor(ContextCompat.getColor(holder.itemView.context, R.color.white))
        } else {
            holder.binding.root.setBackgroundResource(R.drawable.product_sizes_bg)
            holder.binding.sizeName.setTextColor(ContextCompat.getColor(holder.itemView.context, R.color.color_grey))
        }

        holder.itemView.setOnClickListener {
            notifyItemChanged(selectedPosition)
            selectedPosition = holder.adapterPosition
            notifyItemChanged(selectedPosition)
        }
    }

    class SizeViewHolder(val binding: SizesItemBinding) : RecyclerView.ViewHolder(binding.root)

    class DiffUtilCallBack : DiffUtil.ItemCallback<String>() {
        override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem == newItem
        }
    }
}
