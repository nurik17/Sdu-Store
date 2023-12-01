package com.example.sdustore.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.sdustore.R
import com.example.sdustore.data.entity.MainRecyclerData
import com.example.sdustore.data.extensions.setSafeOnClickListener
import com.example.sdustore.databinding.MainHomeRecyclerItemBinding

class MainRecyclerAdapter(
    private val onClick :(MainRecyclerData) ->Unit
) : ListAdapter<MainRecyclerData, MainRecyclerAdapter.NewsViewHolder>(DiffUtilCallBack()) {

    fun setData(newList: List<MainRecyclerData>) {
        submitList(newList)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = MainHomeRecyclerItemBinding.inflate(inflater,parent,false)
        return NewsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item,onClick)
    }

    class NewsViewHolder(private val binding: MainHomeRecyclerItemBinding):RecyclerView.ViewHolder(binding.root){
        fun bind(item: MainRecyclerData, onClick: (MainRecyclerData) -> Unit){
            binding.apply {
                textTitle.text = item.textTitle
                textSubTitle.text = item.textSubTitle

                if(adapterPosition == 2){
                    textTitle.setTextColor(itemView.context.resources.getColor(R.color.white))
                    textSubTitle.setTextColor(itemView.context.resources.getColor(R.color.white))
                }else{
                    textTitle.setTextColor(itemView.context.resources.getColor(R.color.black))
                    textSubTitle.setTextColor(itemView.context.resources.getColor(R.color.black))
                }

                item.imageUrl?.let { imageUrl ->
                    Glide.with(imageBack)
                        .load(imageUrl)
                        .into(imageBack)
                }
                root.setSafeOnClickListener {
                    onClick.invoke(item)
                }
            }
        }
    }

    class DiffUtilCallBack: DiffUtil.ItemCallback<MainRecyclerData>(){
        override fun areItemsTheSame(oldItem: MainRecyclerData, newItem: MainRecyclerData): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: MainRecyclerData, newItem: MainRecyclerData): Boolean {
            return oldItem == newItem
        }
    }
}

