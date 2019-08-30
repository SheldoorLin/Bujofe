package com.sheldon.bujofe.study_room

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.sheldon.bujofe.databinding.ItemStudyroomSpinnerBinding

class WeekSpinnerAdapter(private val strings: Array<String>) :
    BaseAdapter() {
    val TAG: String = "Sheldon"

    @SuppressLint("ViewHolder")
    override fun getView(
        position: Int, convertView: View?, parent: ViewGroup?): View {
        val binding =
            ItemStudyroomSpinnerBinding.inflate(LayoutInflater.from(parent?.context), parent, false)
        binding.title = strings[position]
        return binding.root
    }


    override fun getItem(position: Int): Any {
        return strings[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int {
        return strings.size
    }
}