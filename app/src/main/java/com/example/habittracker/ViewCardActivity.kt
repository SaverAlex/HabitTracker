package com.example.habittracker

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.habittracker.DataBase.DBHelper
import com.example.habittracker.DataBase.Task

import kotlinx.android.synthetic.main.activity_view_card.*
import kotlinx.android.synthetic.main.content_view_card.*

class ViewCardActivity : AppCompatActivity() {

    companion object {
        const val positionNumber = ""
    }

    internal lateinit var db:DBHelper
    internal var listTasks:List<Task> = ArrayList<Task>()
    internal var position = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_card)
        setSupportActionBar(toolbar)

        db = DBHelper(this)
        showCard()

        delete.setOnClickListener{
            val user = Task(listTasks[position].id,listTasks[position].name,listTasks[position].description,listTasks[position].period)
            db.deleteUser(user)
            var intent = Intent()
            setResult(Activity.RESULT_OK,intent)
            finish()
        }
        correct.setOnClickListener {
            val intent = Intent(this,CorrectViewActivity::class.java)
            intent.putExtra(CorrectViewActivity.positionNumber, position)
            startActivityForResult(intent,0)
        }
    }

    private fun showCard() {
        listTasks = db.allTask
        position = intent.getIntExtra(positionNumber,0)
        viewCard_name.text = listTasks[position].name
        viewCard_description.text = getString(R.string.view_card_description,listTasks[position].description)
        viewCard_period.text = getString(R.string.view_card_period,listTasks[position].period)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (data == null){
            return
        }
        showCard()
        var intent = Intent()
        setResult(Activity.RESULT_OK,intent)
    }

}
