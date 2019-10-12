package com.example.habittracker

import android.app.Activity
import android.os.Bundle
import android.view.View
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import android.content.Intent
import android.widget.EditText
import androidx.annotation.IntegerRes
import com.example.habittracker.DataBase.DBHelper
import com.example.habittracker.DataBase.ListUserAdapter
import com.example.habittracker.DataBase.User

import kotlinx.android.synthetic.main.activity_add_card.*
import kotlinx.android.synthetic.main.content_add_card.*
import kotlinx.android.synthetic.main.content_main.*

class AddCardActivity : AppCompatActivity() {

    internal lateinit var db: DBHelper
    internal var users:List<User> = ArrayList<User>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_card)
        setSupportActionBar(toolbar)

        db = DBHelper(this)

        addCard_save.setOnClickListener{
            val user = User(addCard_name.text.toString().hashCode(),addCard_name.text.toString(),addCard_description.text.toString(),Integer.parseInt(addCard_period.text.toString()))
            db.addUser(user)
            var intent = Intent()
            setResult(Activity.RESULT_OK,intent)
            finish()

        }
    }
}
