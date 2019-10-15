package com.sheldon.bujofe

import android.content.Context
import com.google.firebase.Timestamp
import com.sheldon.bujofe.data.ClassEvent
import com.sheldon.bujofe.data.ClassName
import com.sheldon.bujofe.data.DateList
import com.sheldon.bujofe.data.TeachList
import com.sheldon.bujofe.unittest.getTeacherList
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.runners.MockitoJUnitRunner
import org.threeten.bp.DateTimeUtils


@RunWith(MockitoJUnitRunner::class)
class DataFilterTest {

    @Mock
    private lateinit var mockContext: Context
    val testTeath: ArrayList<TeachList> = ArrayList()
    val testDate: ArrayList<DateList> = ArrayList()
    val testStudentList: ArrayList<String> = ArrayList()
    val testRollNameList: ArrayList<String> = ArrayList()
    val testclassEvent: ArrayList<ClassEvent> = ArrayList()

    @Test
    fun filterFunctionTest() {
        testRollNameList.addAll(listOf("李曉明", "王大明", "林曉明", "王大力"))
        testStudentList.addAll(listOf("林大目", "林曉明", "王大明", "李曉明", "岳不群"))
        testDate.addAll(listOf(DateList(Timestamp.now(), "1-1 代數導論", testRollNameList)))
        testTeath.add(
            TeachList(
                "001",
                3,
                "賀折數學",
                "賀折",
                "八樓三室",
                "Math",
                testDate,
                testStudentList
            )
        )


        val testTimeNow = java.sql.Timestamp(System.currentTimeMillis())
        testclassEvent.addAll(
            listOf(
                ClassEvent(
                    DateTimeUtils.toLocalDateTime(testTimeNow),
                    DateTimeUtils.toLocalDateTime(testTimeNow).plusHours(3),
                    ClassName(
                        "1-1 代數導論",
                        "八樓三室",
                        "${testRollNameList.size}/${testStudentList.size}",
                        "haha"
                    )
                )
            )
        )

        val testResult = getTeacherList(testTeath)


        assertThat(testResult.size, `is`(testclassEvent.toList().size))
    }
}