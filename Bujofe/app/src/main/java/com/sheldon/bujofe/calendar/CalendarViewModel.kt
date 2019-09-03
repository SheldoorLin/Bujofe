package com.sheldon.bujofe.calendar

import android.util.Log
import android.widget.TextView
import androidx.lifecycle.ViewModel;
import com.sheldon.bujofe.BujofeApplication
import com.sheldon.bujofe.R
import com.sheldon.bujofe.timelayout.Event
import com.sheldon.bujofe.timelayout.EventView

class CalendarViewModel : ViewModel() {



    val myEventView = EventView(
        BujofeApplication.instance.applicationContext,
        Event().apply {
            startTime = 7F
            endTime = 9F
            title = "1"
//              Log.d("AAmyEvent1","${myEvent1.toString()}")
        },//加入開始結束時間
        itemsMargin = 1, //與旁邊方塊的間隔(可加可不加)
        layoutResourceId = R.layout.item_event_three,//使用甚麼顏色的背景(不加就默認第一種)
        setupView = { myView ->
            //SETUP VIEW
            myView.findViewById<TextView>(R.id.tvTitle).text = "芙丸英文"
            Log.d("Sheldon", "setupView1,I'am here")
        },
        onItemClick = { _ ->
            //CLICK EVENT
            Log.d("Sheldon", "I'am here,onItemClick")
        }
    )

    val myEventView2 = EventView(
        BujofeApplication.instance.applicationContext,
        Event().apply {
            startTime = 9F
            endTime = 12F
            title = "2"

        },//加入開始結束時間
        itemsMargin = 1, //與旁邊方塊的間隔(可加可不加)
        layoutResourceId = R.layout.item_event, //使用甚麼顏色的背景(不加就默認第一種)
        setupView = { myView ->
            //SETUP VIEW
            myView.findViewById<TextView>(R.id.tvTitle).text = "吳用化學"
            Log.d("Sheldon", "setupView2,I'am here")
        },
        onItemClick = { _ ->
            //CLICK EVENT
            Log.d("Sheldon", "I'am here,onItemClick")
        }
    )

    val myEventView3 = EventView(
        BujofeApplication.instance.applicationContext,
        Event().apply {
            startTime = 12F
            endTime = 13F
            title = "2"

        },//加入開始結束時間
        itemsMargin = 1,//與旁邊方塊的間隔(可加可不加)
        layoutResourceId = R.layout.item_event_two, //使用甚麼顏色的背景(不加就默認第一種)
        setupView = { myView ->
            //SETUP VIEW
            myView.findViewById<TextView>(R.id.tvTitle).text = "吃飯時間"
            Log.d("Sheldon", "setupView2,I'am here")
        },
        onItemClick = { _ ->
            //CLICK EVENT
            Log.d("Sheldon", "I'am here,onItemClick")
        }
    )



}
