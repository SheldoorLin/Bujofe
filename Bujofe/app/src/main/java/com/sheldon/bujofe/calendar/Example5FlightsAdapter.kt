package com.sheldon.bujofe.calendar

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sheldon.bujofe.R
import com.sheldon.bujofe.`object`.Flight
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.example_5_event_item_view.*
import org.threeten.bp.format.DateTimeFormatter


class Example5FlightsAdapter : RecyclerView.Adapter<Example5FlightsAdapter.Example5FlightsViewHolder>() {

    val flights = mutableListOf<Flight>()


    private val formatter = DateTimeFormatter.ofPattern("EEE'\n'dd MMM'\n'HH:mm")



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Example5FlightsViewHolder {
        return Example5FlightsViewHolder(parent.inflate(R.layout.example_5_event_item_view))
    }


    override fun onBindViewHolder(viewHolder: Example5FlightsViewHolder, position: Int) {
        viewHolder.bind(flights[position])
    }

    override fun getItemCount(): Int = flights.size

    inner class Example5FlightsViewHolder(override val containerView: View) :
        RecyclerView.ViewHolder(containerView), LayoutContainer {

        fun bind(flight: Flight) {
            itemFlightDateText.text = formatter.format(flight.time)
            itemFlightDateText.setBackgroundColor(itemView.context.getColorCompat(flight.color))
            itemDepartureAirportCodeText.text = flight.departure.code
            itemDepartureAirportCityText.text = flight.departure.city
            itemDestinationAirportCodeText.text = flight.destination.code
            itemDestinationAirportCityText.text = flight.destination.city
        }
    }
}