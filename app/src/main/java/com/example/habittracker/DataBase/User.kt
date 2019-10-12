package com.example.habittracker.DataBase

class User {
    var id:Int = 0
    var name:String? = null
    var description:String? = null
    var period:Int = 0

    constructor(id:Int,name:String?,description:String?,period:Int){
        this.id = id
        this.name = name
        this.description = description
        this.period = period
    }
    constructor(){}
}
