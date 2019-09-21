package com.sheldon.bujofe.home

import android.content.Context
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.sheldon.bujofe.BujofeApplication
import com.sheldon.bujofe.R
import com.sheldon.bujofe.`object`.Notice
import com.sheldon.bujofe.databinding.ItemHomeNoticeCardBinding

class NoticeItemAdapter : ListAdapter<Notice, NoticeItemAdapter.ItemViewHolder>(DiffCallback) {

    companion object DiffCallback : DiffUtil.ItemCallback<Notice>() {
        override fun areItemsTheSame(oldItem: Notice, newItem: Notice): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: Notice, newItem: Notice): Boolean {
            return oldItem.title == newItem.title
        }
    }


    class ItemViewHolder(private var binding: ItemHomeNoticeCardBinding) :
        RecyclerView.ViewHolder(binding.root) {

        val img = binding.imgNoticeIcon

        fun bind(notice: Notice) {
            binding.notice = notice
            binding.imgNoticeIcon

            img.setColorFilter(
                ContextCompat.getColor(BujofeApplication.instance, R.color.color_orange_text_gray),
                android.graphics.PorterDuff.Mode.SRC_IN
            )

            when (notice.type) {
                1 -> img.setImageResource(R.drawable.tornado)
                2 -> img.setImageResource(R.drawable.smallmanread)
                3 -> img.setImageResource(R.drawable.crown_icon)

            }
            Log.d("noticetype", notice.type.toString())
            binding.executePendingBindings()
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return ItemViewHolder(ItemHomeNoticeCardBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val notice = getItem(position)


        holder.bind(notice)
    }
}