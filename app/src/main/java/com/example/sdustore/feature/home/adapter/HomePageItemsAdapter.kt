package com.example.sdustore.feature.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.sdustore.databinding.MainListItemBinding
import com.example.sdustore.feature.home.data.ItemModel

class HomePageItemsAdapter(private val itemsList:ArrayList<ItemModel>) :
    RecyclerView.Adapter<HomePageItemsAdapter.ItemsViewHolder>() {

    inner class ItemsViewHolder(val binding : MainListItemBinding) : RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemsViewHolder {
        return ItemsViewHolder(MainListItemBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: ItemsViewHolder, position: Int) {
        holder.binding.mainImage.setImageResource(itemsList[position].image)
        holder.binding.textName.text = itemsList[position].nameItem
        holder.binding.textPrice.text = itemsList[position].price.toString()

    }

    override fun getItemCount(): Int {
        return itemsList.size
    }

}
