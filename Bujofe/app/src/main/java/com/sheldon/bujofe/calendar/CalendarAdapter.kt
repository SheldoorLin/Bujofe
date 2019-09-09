package com.sheldon.bujofe.calendar

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sheldon.bujofe.R
import com.sheldon.bujofe.`object`.ClassMute
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_calendar_event_view.*
import org.threeten.bp.format.DateTimeFormatter


class CalendarAdapter : RecyclerView.Adapter<CalendarAdapter.EventItemViewHolder>() {

    val flights = mutableListOf<ClassMute>()


    private val formatter = DateTimeFormatter.ofPattern("EEE'\n'dd MMM'\n'HH:mm")



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventItemViewHolder {
        return EventItemViewHolder(parent.inflate(R.layout.item_calendar_event_view))
    }


    override fun onBindViewHolder(viewHolder: EventItemViewHolder, position: Int) {
        viewHolder.bind(flights[position])
    }

    override fun getItemCount(): Int = flights.size

    inner class EventItemViewHolder(override val containerView: View) :
        RecyclerView.ViewHolder(containerView), LayoutContainer {

        fun bind(classMute: ClassMute) {
            itemFlightDateText.text = formatter.format(classMute.time)
            itemFlightDateText.setBackgroundColor(itemView.context.getColorCompat(classMute.color))
            itemDepartureAirportCodeText.text = classMute.departure.teacherName
            itemDepartureAirportCityText.text = classMute.departure.type
//            itemDestinationAirportCodeText.text = classMute.destination.code
//            itemDestinationAirportCityText.text = classMute.destination.city
        }
    }
}