package com.example.habittracker

import android.app.Activity
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.content.Intent
import com.example.habittracker.DataBase.DBHelper
import com.example.habittracker.DataBase.Task

import kotlinx.android.synthetic.main.activity_add_card.*
import kotlinx.android.synthetic.main.content_add_card.*

class AddCardActivity : AppCompatActivity() {

    internal lateinit var db: DBHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_card)
        setSupportActionBar(toolbar)

        db = DBHelper(this)

        addCard_save.setOnClickListener{
            val task = Task(addCard_name.text.toString().hashCode(),addCard_name.text.toString(),addCard_description.text.toString(),Integer.parseInt(addCard_period.text.toString()))
            db.addUser(task)
            var intent = Intent()
            setResult(Activity.RESULT_OK,intent)
            finish()

        }
    }
}
