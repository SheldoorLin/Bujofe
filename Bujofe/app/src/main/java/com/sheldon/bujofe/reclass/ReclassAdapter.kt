package com.sheldon.bujofe.reclass

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.sheldon.bujofe.R
import com.sheldon.bujofe.`object`.ReClass
import com.sheldon.bujofe.databinding.ItemReclassRecodeBinding

class ReclassAdapter : ListAdapter<ReClass, ReclassAdapter.ItemViewHolder>(DiffCallback) {

    companion object DiffCallback : DiffUtil.ItemCallback<ReClass>() {
        override fun areItemsTheSame(oldItem: ReClass, newItem: ReClass): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: ReClass, newItem: ReClass): Boolean {
            return oldItem.className == newItem.className
        }
    }


    class ItemViewHolder(private var binding: ItemReclassRecodeBinding) :
        RecyclerView.ViewHolder(binding.root) {


        @SuppressLint("ResourceAsColor")
        fun bind(reClass: ReClass) {
            binding.reClass = reClass

            when (reClass.Status) {
                "Approved" -> binding.status.setBackgroundResource(R.drawable.rounded_reclass_text_approve)
                "Rejected" -> binding.status.setBackgroundResource(R.drawable.rounded_reclass_text_reject)
                "Waiting" -> binding.status.setBackgroundResource(R.drawable.rounded_reclass_text_waiting)
            }
            binding.executePendingBindings()
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return ItemViewHolder(ItemReclassRecodeBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val notice = getItem(position)
        holder.bind(notice)
    }
}