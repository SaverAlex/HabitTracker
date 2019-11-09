package com.example.habittracker

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
            if (!it.equals("")) {
                var date = it.split("-").toTypedArray()
                var day = LocalDate.of(date[0].toInt(), date[1].toInt(), date[2].toInt())
                result.add(day)
            }
        }
        return result
    }

}