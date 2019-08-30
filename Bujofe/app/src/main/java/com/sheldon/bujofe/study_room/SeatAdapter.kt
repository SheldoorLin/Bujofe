package com.sheldon.bujofe.study_room

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.sheldon.bujofe.`object`.Seat
import com.sheldon.bujofe.databinding.ItemStudyroomSeatBinding

class SeatAdapter : ListAdapter<Seat, SeatAdapter.ItemViewHolder>(DiffCallback) {

    companion object DiffCallback : DiffUtil.ItemCallback<Seat>() {
        override fun areItemsTheSame(oldItem: Seat, newItem: Seat): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: Seat, newItem: Seat): Boolean {
            return oldItem.id == newItem.id
        }
    }


    class ItemViewHolder(private var binding: ItemStudyroomSeatBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(seat: Seat) {
            binding.seat = seat
            binding.executePendingBindings()
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return ItemViewHolder(ItemStudyroomSeatBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val seat = getItem(position)
        holder.bind(seat)
    }
}