package com.sheldon.bujofe.home

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
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

        private const val CLASS_EMERGENCY_CODE = 1

        private const val CLASS_NOTE_CODE = 2

        private const val CLASS_SCHOLARSHIP_CODE = 3
    }

    class ItemViewHolder(private var binding: ItemHomeNoticeCardBinding) :
        RecyclerView.ViewHolder(binding.root) {

        private val imgNoticeIcon: ImageView = binding.imgNoticeIcon

        fun bind(notice: Notice) {

            binding.notice = notice

            imgNoticeIcon.setColorFilter(
                ContextCompat.getColor(BujofeApplication.instance, R.color.color_orange_Dark),
                android.graphics.PorterDuff.Mode.SRC_IN
            )

            when (notice.type) {

                CLASS_EMERGENCY_CODE -> imgNoticeIcon.setImageResource(R.drawable.tornado)

                CLASS_NOTE_CODE -> imgNoticeIcon.setImageResource(R.drawable.ic_small_man_read)

                CLASS_SCHOLARSHIP_CODE -> imgNoticeIcon.setImageResource(R.drawable.crown_icon)
            }

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