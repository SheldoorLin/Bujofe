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

    private val timeFormatter = DateTimeFormatter.ofPattern("HH:mm")

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
            val classTimeStartToFinish =
                timeFormatter.format(classEvent.classStartTime) +
                    "\n|\n" + timeFormatter.format(classEvent.classFinishTime)

            date.text = classTimeStartToFinish

            tx_class_name.text = classEvent.className.type

            tx_teach_class_room.text = classEvent.className.teachClass

            tx_roll_name.text = classEvent.className.rollCallSituation

            tx_class_content.text = classEvent.className.courseContent
        }
    }
}