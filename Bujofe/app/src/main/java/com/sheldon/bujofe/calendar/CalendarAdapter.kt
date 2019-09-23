package com.sheldon.bujofe.calendar

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.sheldon.bujofe.R
import com.sheldon.bujofe.`object`.ClassMute
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_calendar_event_view.*
import org.threeten.bp.format.DateTimeFormatter


class CalendarAdapter : RecyclerView.Adapter<CalendarAdapter.EventItemViewHolder>() {


    val classMute = mutableListOf<ClassMute>()


    private val formatter = DateTimeFormatter.ofPattern("MM'月' dd'號' HH:mm")
    private val formatter_start_time = DateTimeFormatter.ofPattern("HH:mm")


    companion object DiffCallback : DiffUtil.ItemCallback<ClassMute>() {
        override fun areItemsTheSame(oldItem: ClassMute, newItem: ClassMute): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: ClassMute, newItem: ClassMute): Boolean {
            return oldItem == newItem
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventItemViewHolder {
        return EventItemViewHolder(parent.inflate(R.layout.item_calendar_event_view))
    }


    override fun onBindViewHolder(viewHolder: EventItemViewHolder, position: Int) {
        viewHolder.bind(classMute[position])
    }

    override fun getItemCount(): Int = classMute.size


    inner class EventItemViewHolder(override val containerView: View) :
        RecyclerView.ViewHolder(containerView), LayoutContainer {

        fun bind(classMute: ClassMute) {
            itemFlightDateText.text = formatter.format(classMute.time)
            itemDepartureAirportCityText.text = classMute.departure.type
            textView2.text = classMute.departure.teach_class
            textView.text =  classMute.departure.order_people
            itemDepartureAirportCodeText.text = classMute.departure.class_context
        }
    }
}