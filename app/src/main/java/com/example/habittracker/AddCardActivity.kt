package com.example.habittracker

import android.os.Bundle
import android.view.View
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import android.content.Intent

import kotlinx.android.synthetic.main.activity_add_card.*
import kotlinx.android.synthetic.main.content_add_card.*

class AddCardActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_card)
        setSupportActionBar(toolbar)
    }

    fun saveMe (view: View) {
    }

}
