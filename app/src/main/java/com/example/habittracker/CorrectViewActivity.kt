package com.example.habittracker

import android.app.Activity
import android.content.Intent
import android.os.Build
import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import com.example.habittracker.DataBase.DBHelper
import com.example.habittracker.DataBase.User

import kotlinx.android.synthetic.main.activity_correct_view.*
import kotlinx.android.synthetic.main.activity_correct_view.toolbar
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_correct_view.*
import kotlinx.android.synthetic.main.content_view_card.*

class CorrectViewActivity : AppCompatActivity() {

    companion object {
        const val positionNumber = ""
    }
    internal lateinit var db: DBHelper
    internal var users:List<User> = ArrayList<User>()
    internal var position = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_correct_view)
        setSupportActionBar(toolbar)

        db = DBHelper(this)
        showCard()

        save.setOnClickListener{
            val user = User(users[position].id,correctView_name.getText().toString(),
                correctView_description.getText().toString(),
                users[position].period)
            db.updateUser(user)
            var intent = Intent()
            setResult(Activity.RESULT_OK,intent)
            finish()
        }
    }

    private fun showCard() {
        users = db.allUser
        position = intent.getIntExtra(Build.ID,0)
        correctView_name.setText(users[position].name)
        correctView_description.setText(users[position].description)
        correctView_period.text = getString(R.string.view_card_period,users[position].period)
    }

}
