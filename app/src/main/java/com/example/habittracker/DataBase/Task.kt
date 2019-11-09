package com.example.habittracker.DataBase

class Task {
    var id:Int = 0
    var name:String? = null
    var description:String? = null
    var period:Int = 0
    var completedDays:String? = null

    constructor(id:Int,name:String?,description:String?,period:Int, completedDays: String?){
        this.id = id
        this.name = name
        this.description = description
        this.period = period
        this.completedDays = completedDays
    }
    constructor(){}
}
