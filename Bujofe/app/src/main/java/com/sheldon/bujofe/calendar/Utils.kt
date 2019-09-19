package com.sheldon.bujofe.calendar

import com.sheldon.bujofe.R
import com.sheldon.bujofe.`object`.ClassMute
import org.threeten.bp.YearMonth

private typealias classMute = ClassMute.ClassName

fun generateFlights(): List<ClassMute> {

    val list = mutableListOf<ClassMute>()
    val currentMonth = YearMonth.now()





    val currentMonth17 = currentMonth.atDay(17)
    list.add(
        ClassMute(
            currentMonth17.atTime(14, 0),
            classMute("超凡", "超凡數學"),
            R.color.brown_700
        )
    )



    list.add(
        ClassMute(
            currentMonth17.atTime(21, 30),
            classMute("芙丸", "芙丸英文"),
            R.color.blue_grey_700
        )
    )

    val currentMonth22 = currentMonth.atDay(22)
    list.add(
        ClassMute(
            currentMonth22.atTime(13, 20),
            classMute("添財", "添財歷史"),
            R.color.blue_800
        )
    )
    list.add(
        ClassMute(
            currentMonth22.atTime(17, 40),
            classMute("飛翔", "飛翔地理"),

            R.color.red_800
        )
    )






    list.add(
        ClassMute(
            currentMonth.atDay(3).atTime(20, 0),
            classMute("天兵", "天兵物理"),
            R.color.teal_700
        )
    )

    list.add(
        ClassMute(
            currentMonth.atDay(12).atTime(18, 15),
            classMute("笨拙", "笨拙家政"),
            R.color.cyan_700
        )
    )

    val nextMonth13 = currentMonth.plusMonths(1).atDay(13)
    list.add(
        ClassMute(
            nextMonth13.atTime(7, 30),
            classMute("小白", "芙丸英文"),
            R.color.pink_700
        )
    )
    list.add(
        ClassMute(
            nextMonth13.atTime(10, 50),
            classMute("大丸", "天兵物理"),
            R.color.green_700
        )
    )



    list.add(
        ClassMute(
            currentMonth.minusMonths(1).atDay(9).atTime(20, 15),
            classMute("曾強", "超凡數學"),
            R.color.orange_800
        )
    )

    return list
}