package com.sheldon.bujofe.calendar

import com.sheldon.bujofe.R
import com.sheldon.bujofe.`object`.ClassMute
import com.sheldon.bujofe.`object`.ClassName
import org.threeten.bp.YearMonth


fun generateFlights(): List<ClassMute> {

    val list = mutableListOf<ClassMute>()
    val currentMonth = YearMonth.now()

    val currentMonth17 = currentMonth.atDay(17)
    list.add(
        ClassMute(
            currentMonth17.atTime(14, 0),
            ClassName("1-3 微積分","五樓三室", "25/30","超凡數學"),
            R.color.brown_700
        )
    )



    list.add(
        ClassMute(
            currentMonth17.atTime(21, 30),
            ClassName("1-2 副詞子句","三樓三室", "49/50","芙丸英文"),
            R.color.blue_grey_700
        )
    )

    val currentMonth22 = currentMonth.atDay(22)
    list.add(
        ClassMute(
            currentMonth22.atTime(13, 20),
            ClassName("3-1 近代歐洲","五樓二室", "50/60","添財歷史"),
            R.color.blue_800
        )
    )
    list.add(
        ClassMute(
            currentMonth22.atTime(17, 40),
            ClassName("2-1 高地氣候","五樓二室", "30/30","飛翔地理"),

            R.color.red_800
        )
    )






    list.add(
        ClassMute(
            currentMonth.atDay(3).atTime(20, 0),
            ClassName("2-1 簡協運動","四樓二室","40/45","天兵物理"),
            R.color.teal_700
        )
    )

    list.add(
        ClassMute(
            currentMonth.atDay(12).atTime(18, 15),
            ClassName("2-2 單擺運動","五樓二室", "40/50","天兵物理"),
            R.color.cyan_700
        )
    )

    val nextMonth13 = currentMonth.plusMonths(1).atDay(13)
    list.add(
        ClassMute(
            nextMonth13.atTime(7, 30),
            ClassName("2-2 文章寫作","三樓三室", "36/37","芙丸英文"),
            R.color.pink_700
        )
    )
    list.add(
        ClassMute(
            nextMonth13.atTime(10, 50),
            ClassName("2-3 雙擺運動","四樓二室", "37/38","天兵物理"),
            R.color.green_700
        )
    )



    list.add(
        ClassMute(
            currentMonth.minusMonths(1).atDay(9).atTime(20, 15),
            ClassName("2-3 緩衝溶液","三樓四室", "29/30","吳用化學"),
            R.color.orange_800
        )
    )
    return list
}