package com.sheldon.bujofe.studyroom

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.sheldon.bujofe.R
import com.sheldon.bujofe.`object`.OrderedTimes
import com.sheldon.bujofe.calendar.setTextColorRes
import com.sheldon.bujofe.databinding.ItemStudyroomSeatOrderedBinding

class OrderedAdapter : ListAdapter<OrderedTimes, OrderedAdapter.ItemViewHolder>(DiffCallback) {

    companion object DiffCallback : DiffUtil.ItemCallback<OrderedTimes>() {
        override fun areItemsTheSame(oldItem: OrderedTimes, newItem: OrderedTimes): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: OrderedTimes, newItem: OrderedTimes): Boolean {
            return oldItem == newItem
        }
    }


    class ItemViewHolder(private var binding: ItemStudyroomSeatOrderedBinding) :
        RecyclerView.ViewHolder(binding.root) {


        fun bind(orderedTimes: OrderedTimes) {
            binding.orderedTimes = orderedTimes

            binding.btnFirstSlot.isEnabled = orderedTimes.firstTimeSlot == ""
            if (orderedTimes.firstTimeSlot!=""){
                binding.btnFirstSlot.text = "已預定"
                binding.btnFirstSlot.setTextColorRes(R.color.Color_White_ffffff)
                binding.btnFirstSlot.setBackgroundResource(R.drawable.rounded_calendar_button_clicked)
            }


            binding.btnSecSlot.isEnabled = orderedTimes.secTimeSlot == ""
            if (orderedTimes.secTimeSlot!=""){
                binding.btnSecSlot.text = "已預定"
                binding.btnSecSlot.setTextColorRes(R.color.Color_White_ffffff)
                binding.btnSecSlot.setBackgroundResource(R.drawable.rounded_calendar_button_clicked)
            }

            binding.btnThirdSlot.isEnabled = orderedTimes.thirdTimeSlot == ""
            if (orderedTimes.thirdTimeSlot!=""){
                binding.btnThirdSlot.text = "已預定"
                binding.btnThirdSlot.setTextColorRes(R.color.Color_White_ffffff)
                binding.btnThirdSlot.setBackgroundResource(R.drawable.rounded_calendar_button_clicked)
            }


            binding.executePendingBindings()
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return ItemViewHolder(ItemStudyroomSeatOrderedBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val notice = getItem(position)
        holder.bind(notice)
    }
}