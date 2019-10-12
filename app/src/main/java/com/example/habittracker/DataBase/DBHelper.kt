package com.example.habittracker.DataBase

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DBHelper(contex: Context):SQLiteOpenHelper(contex,DATABASE_NAME,null,DATABASE_VER){

    companion object{
        private val DATABASE_VER = 1
        private val DATABASE_NAME = "userCards.db"

        // Table

        private val TABLE_NAME="UserCard"
        private val COL_ID = "ID"
        private val COL_NAME="Name"
        private val COL_DESCRIPTION="Description"
        private val COL_PERIOD="Period"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val CREATE_TABLE_QUERY:String = ("CREATE TABLE $TABLE_NAME ($COL_ID INTEGER PRIMARY KEY, $COL_NAME TEXT, $COL_DESCRIPTION TEXT, $COL_PERIOD INTEGER)")
        db!!.execSQL(CREATE_TABLE_QUERY)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db!!.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db!!)
    }

    val allUser:List<User>
        get() {
            val users =  ArrayList<User>()
            val selectQuery = "SELECT * FROM $TABLE_NAME"
            val db = this.writableDatabase
            val cursor = db.rawQuery(selectQuery,null)
            if (cursor.moveToFirst()){
                do {
                    val user = User()
                    user.id = cursor.getInt(cursor.getColumnIndex(COL_ID))
                    user.name = cursor.getString(cursor.getColumnIndex(COL_NAME))
                    user.description = cursor.getString(cursor.getColumnIndex(COL_DESCRIPTION))
                    user.period = cursor.getInt(cursor.getColumnIndex(COL_PERIOD))

                    users.add(user)
                } while (cursor.moveToNext())
            }
            db.close()
            return users
        }

    fun addUser(user: User){
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(COL_ID, user.id)
        values.put(COL_NAME, user.name)
        values.put(COL_DESCRIPTION, user.description)
        values.put(COL_PERIOD, user.period)
        db.insert(TABLE_NAME,null,values)
        db.close()
    }

    fun updateUser(user: User): Int{
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(COL_ID, user.id)
        values.put(COL_NAME, user.name)
        values.put(COL_DESCRIPTION, user.description)
        values.put(COL_PERIOD, user.period)
        return db.update(TABLE_NAME,values,"$COL_ID=?", arrayOf(user.id.toString()))
    }

    fun deleteUser(user: User){
        val db = this.writableDatabase
        db.delete(TABLE_NAME,"$COL_ID=?", arrayOf(user.id.toString()))
        db.close()
    }
}