package com.sheldon.bujofe.profile

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.sheldon.bujofe.`object`.ClassList
import com.sheldon.bujofe.databinding.ItemProfileDetailBinding

class ProfileDetailAdapter : ListAdapter<ClassList, ProfileDetailAdapter.ItemViewHolder>(DiffCallback) {

    companion object DiffCallback : DiffUtil.ItemCallback<ClassList>() {
        override fun areItemsTheSame(oldItem: ClassList, newItem: ClassList): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: ClassList, newItem: ClassList): Boolean {
            return oldItem == newItem
        }
    }


    class ItemViewHolder(private var binding: ItemProfileDetailBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(classList: ClassList) {
            binding.classList = classList
            binding.executePendingBindings()
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return ItemViewHolder(ItemProfileDetailBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val classList = getItem(position)
        holder.bind(classList)
    }
}