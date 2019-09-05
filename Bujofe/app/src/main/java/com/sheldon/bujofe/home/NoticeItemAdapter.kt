package com.sheldon.bujofe.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.sheldon.bujofe.`object`.Notice
import com.sheldon.bujofe.databinding.ItemHomeAnnounceBinding

class NoticeItemAdapter : ListAdapter<Notice, NoticeItemAdapter.ItemViewHolder>(DiffCallback) {

    companion object DiffCallback : DiffUtil.ItemCallback<Notice>() {
        override fun areItemsTheSame(oldItem: Notice, newItem: Notice): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: Notice, newItem: Notice): Boolean {
            return oldItem.title == newItem.title
        }
    }


    class ItemViewHolder(private var binding: ItemHomeAnnounceBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(notice: Notice) {
            binding.notice = notice
            binding.executePendingBindings()
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return ItemViewHolder(ItemHomeAnnounceBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val notice = getItem(position)
        holder.bind(notice)
    }
}