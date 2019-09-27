package com.sheldon.bujofe.studyroom

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.sheldon.bujofe.BujofeApplication
import com.sheldon.bujofe.R
import com.sheldon.bujofe.`object`.OrderedTimes
import com.sheldon.bujofe.calendar.setTextColorRes
import com.sheldon.bujofe.databinding.ItemStudyroomSeatOrderedBinding

class OrderedAdapter(val viewModel: StudyRoomViewModel) :
    ListAdapter<OrderedTimes, OrderedAdapter.ItemViewHolder>(DiffCallback) {

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

        fun bind(orderedTimes: OrderedTimes, viewModel: StudyRoomViewModel) {

            binding.orderedTimes = orderedTimes

            binding.viewModel = viewModel

            if (orderedTimes.firstTimeSlot != "") {
                binding.btnFirstSlot.text = "已預定"
                binding.btnFirstSlot.setTextColorRes(R.color.Color_White_ffffff)
                binding.btnFirstSlot.setBackgroundResource(R.drawable.rounded_calendar_button_clicked)
            } else {
                binding.btnFirstSlot.text = "可預訂"
                binding.btnFirstSlot.isEnabled
                binding.btnFirstSlot.setTextColorRes(R.color.black)
                binding.btnFirstSlot.setBackgroundResource(R.drawable.rounded_reclass_item)
            }
            if (orderedTimes.secTimeSlot != "") {
                binding.btnSecSlot.text = "已預定"
                binding.btnSecSlot.setTextColorRes(R.color.Color_White_ffffff)
                binding.btnSecSlot.setBackgroundResource(R.drawable.rounded_calendar_button_clicked)
            } else {
                binding.btnSecSlot.text = "可預訂"
                binding.btnSecSlot.isEnabled
                binding.btnSecSlot.setTextColorRes(R.color.black)
                binding.btnSecSlot.setBackgroundResource(R.drawable.rounded_reclass_item)
            }
            if (orderedTimes.thirdTimeSlot != "") {
                binding.btnThirdSlot.text = "已預定"
                binding.btnThirdSlot.setTextColorRes(R.color.Color_White_ffffff)
                binding.btnThirdSlot.setBackgroundResource(R.drawable.rounded_calendar_button_clicked)
            } else {
                binding.btnThirdSlot.text = "可預訂"
                binding.btnThirdSlot.isEnabled
                binding.btnThirdSlot.setTextColorRes(R.color.black)
                binding.btnThirdSlot.setBackgroundResource(R.drawable.rounded_reclass_item)
            }


            binding.btnFirstSlot.setOnClickListener {
                viewModel.seatStatus.value = 1
            }
            binding.btnSecSlot.setOnClickListener {
                viewModel.seatStatus.value = 2
            }
            binding.btnThirdSlot.setOnClickListener {
                viewModel.seatStatus.value = 3
            }


            binding.executePendingBindings()
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return ItemViewHolder(ItemStudyroomSeatOrderedBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val notice = getItem(position)
        holder.bind(notice,viewModel)



    }
}