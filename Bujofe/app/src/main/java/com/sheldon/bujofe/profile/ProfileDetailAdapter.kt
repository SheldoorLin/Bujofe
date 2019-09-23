package com.sheldon.bujofe.profile

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.sheldon.bujofe.`object`.ClassInformation
import com.sheldon.bujofe.databinding.ItemProfileDetailBinding

class ProfileDetailAdapter : ListAdapter<ClassInformation, ProfileDetailAdapter.ItemViewHolder>(DiffCallback) {

    companion object DiffCallback : DiffUtil.ItemCallback<ClassInformation>() {
        override fun areItemsTheSame(oldItem: ClassInformation, newItem: ClassInformation): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: ClassInformation, newItem: ClassInformation): Boolean {
            return oldItem == newItem
        }
    }


    class ItemViewHolder(private var binding: ItemProfileDetailBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(classInformation: ClassInformation) {
            binding.classInformation = classInformation
            binding.executePendingBindings()
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return ItemViewHolder(ItemProfileDetailBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val notice = getItem(position)
        holder.bind(notice)
    }
}