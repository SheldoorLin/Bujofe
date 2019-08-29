package com.sheldon.bujofe.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.sheldon.bujofe.`object`.NoticeItem
import com.sheldon.bujofe.databinding.ItemHomeAnnounceBinding

class NoticeItemAdapter : ListAdapter<NoticeItem, NoticeItemAdapter.ItemViewHolder>(DiffCallback) {

    companion object DiffCallback : DiffUtil.ItemCallback<NoticeItem>() {
        override fun areItemsTheSame(oldItem: NoticeItem, newItem: NoticeItem): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: NoticeItem, newItem: NoticeItem): Boolean {
            return oldItem == newItem
        }
    }


    class ItemViewHolder(private var binding: ItemHomeAnnounceBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(noticeItem: NoticeItem) {
            binding.noticeItem = noticeItem
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