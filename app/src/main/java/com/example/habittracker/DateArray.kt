package com.example.habittracker

import com.kizitonwose.calendarview.model.CalendarDay
import org.threeten.bp.LocalDate


object DateArray {
    fun serialization (completedDays: ArrayList<LocalDate>): String{
        var allDate: String = ""
        completedDays.forEach{
            allDate = "$allDate$it;"
        }
        return allDate
    }

    fun deserialize (days: String?): ArrayList<LocalDate>{
        var result: ArrayList<LocalDate> = ArrayList()
        var tempArray = days?.split(";")?.toTypedArray()
        tempArray?.forEach {
            if (it != "") {
                var date = it.split("-").toTypedArray()
                var day = LocalDate.of(date[0].toInt(), date[1].toInt(), date[2].toInt())
                result.add(day)
            }
        }
        return result
    }

    fun drawableRes (completedDays: ArrayList<LocalDate>, day : CalendarDay, period: Int): Int {
        var i = 0
        completedDays.forEach {
            if (it == day.date){
                if (period == 1) return R.drawable.single_selected_bg
                if (i == 0) return R.drawable.continuous_selected_bg_start
                if (completedDays[0].dayOfYear + period == day.date.dayOfYear + 1) return R.drawable.continuous_selected_bg_end
                if (completedDays[i].dayOfMonth == day.date.dayOfMonth - 1){
                    return R.drawable.continuous_selected_bg_middle
                }
            }
            i++
        }
        return R.drawable.continuous_selected_bg_middle
    }

}