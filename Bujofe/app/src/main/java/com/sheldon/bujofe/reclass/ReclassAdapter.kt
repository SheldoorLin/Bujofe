package com.sheldon.bujofe.reclass

import android.annotation.SuppressLint
import android.icu.text.SimpleDateFormat
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.sheldon.bujofe.R
import com.sheldon.bujofe.data.Records
import com.sheldon.bujofe.databinding.ItemReclassRecodeBinding
import org.threeten.bp.DateTimeUtils
import java.sql.Date


class ReclassAdapter : ListAdapter<Records, ReclassAdapter.ItemViewHolder>(DiffCallback) {

    companion object DiffCallback : DiffUtil.ItemCallback<Records>() {
        override fun areItemsTheSame(oldItem: Records, newItem: Records): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: Records, newItem: Records): Boolean {
            return oldItem.className == newItem.className
        }
    }

    class ItemViewHolder(private var binding: ItemReclassRecodeBinding) :
        RecyclerView.ViewHolder(binding.root) {


        @SuppressLint("ResourceAsColor")
        fun bind(records: Records) {

            binding.records = records

            val sfd = SimpleDateFormat("yyyy-MM-dd")
            sfd.format(Date(records.date.seconds * 1000))

            Log.d("test", "new function = ${DateTimeUtils.toLocalDate(Date(records.date.seconds * 1000))}")

            binding.date.text = sfd.format(Date(records.date.seconds * 1000))

            when (records.status) {
                "Approved" -> binding.status.setTextColor(R.color.green_700)
                "Rejected" -> binding.status.setTextColor(R.color.red_800)
                "Waiting" -> binding.status.setTextColor(R.color.Color_gray_aeaeae)
            }
            binding.executePendingBindings()
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return ItemViewHolder(ItemReclassRecodeBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val records = getItem(position)
        holder.bind(records)
    }
}