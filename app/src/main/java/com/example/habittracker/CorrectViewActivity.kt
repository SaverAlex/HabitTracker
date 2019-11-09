package com.example.habittracker

import android.app.Activity
import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.habittracker.DataBase.DBHelper
import com.example.habittracker.DataBase.Task

import kotlinx.android.synthetic.main.activity_correct_view.toolbar
import kotlinx.android.synthetic.main.content_correct_view.*

class CorrectViewActivity : AppCompatActivity() {

    companion object {
        const val positionNumber = ""
    }
    internal lateinit var db: DBHelper
    internal var listTasks:List<Task> = ArrayList<Task>()
    internal var position = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_correct_view)
        setSupportActionBar(toolbar)

        db = DBHelper(this)
        showCard()

        save.setOnClickListener{
            val task = Task(listTasks[position].id,correctView_name.getText().toString(),
                correctView_description.getText().toString(),
                listTasks[position].period,null)
            db.updateTask(task)
            var intent = Intent()
            setResult(Activity.RESULT_OK,intent)
            finish()
        }
    }

    private fun showCard() {
        listTasks = db.allTask
        position = intent.getIntExtra(Build.ID,0)
        correctView_name.setText(listTasks[position].name)
        correctView_description.setText(listTasks[position].description)
        correctView_period.text = getString(R.string.view_card_period,listTasks[position].period)
    }

}
