package com.sheldon.bujofe.class_schedule

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.sheldon.bujofe.R
import com.sheldon.bujofe.data.ClassEvent
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_calendar_event_view.*
import org.threeten.bp.format.DateTimeFormatter


class ClassScheduleAdapter :
    ListAdapter<ClassEvent, ClassScheduleAdapter.EventItemViewHolder>(DiffCallback) {

    val classEvents = mutableListOf<ClassEvent>()

    private val formatter = DateTimeFormatter.ofPattern("MM'月' dd'號' HH:mm")

    companion object DiffCallback : DiffUtil.ItemCallback<ClassEvent>() {
        override fun areItemsTheSame(oldItem: ClassEvent, newItem: ClassEvent): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: ClassEvent, newItem: ClassEvent): Boolean {
            return oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventItemViewHolder {
        return EventItemViewHolder(parent.inflate(R.layout.item_calendar_event_view))
    }

    override fun onBindViewHolder(holder: EventItemViewHolder, position: Int) {
        holder.bind(classEvents[position])
    }

    override fun getItemCount(): Int = classEvents.size

    inner class EventItemViewHolder(override val containerView: View) :
        RecyclerView.ViewHolder(containerView), LayoutContainer {

        fun bind(classEvent: ClassEvent) {
            date.text = formatter.format(classEvent.time)
            tx_class_name.text = classEvent.className.type
            class_room.text = classEvent.className.teach_class
            order_people.text = classEvent.className.order_people
            class_context.text = classEvent.className.class_context
        }
    }
}