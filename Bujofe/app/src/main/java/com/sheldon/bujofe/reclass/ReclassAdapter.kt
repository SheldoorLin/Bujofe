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
import com.sheldon.bujofe.`object`.Records
import com.sheldon.bujofe.databinding.ItemReclassRecodeBinding
import org.threeten.bp.format.DateTimeFormatter
import java.sql.Date
import java.util.*


class ReclassAdapter : ListAdapter<Records, ReclassAdapter.ItemViewHolder>(DiffCallback) {

    companion object DiffCallback : DiffUtil.ItemCallback<Records>() {
        override fun areItemsTheSame(oldItem: Records, newItem: Records): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: Records, newItem: Records): Boolean {
            return oldItem.className == newItem.className
        }
    }

    private val formatter = DateTimeFormatter.ofPattern("MM'月' dd'號' HH:mm")

    class ItemViewHolder(private var binding: ItemReclassRecodeBinding) :
        RecyclerView.ViewHolder(binding.root) {


        @SuppressLint("ResourceAsColor")
        fun bind(records: Records) {
            binding.records = records

            val sfd = SimpleDateFormat("yyyy-MM-dd")
            sfd.format(Date(records.date.seconds))
            Log.d("test", "sfd = ${sfd.format(Date(records.date.seconds*1000))}")
            binding.date.text = sfd.format(Date(records.date.seconds*1000))


            when (records.status) {
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
        val records = getItem(position)
        holder.bind(records)
    }
}