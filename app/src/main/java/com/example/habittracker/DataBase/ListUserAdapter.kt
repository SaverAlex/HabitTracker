package com.example.habittracker.DataBase

import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.EditText
import com.example.habittracker.MainActivity
import com.example.habittracker.R
import kotlinx.android.synthetic.main.row_layout.view.*

class ListUserAdapter (internal var activity: Activity,
                         internal var users:List<User>) : BaseAdapter() {

    internal var inflater: LayoutInflater

    init {
        inflater = activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val rowView: View
        rowView = inflater.inflate(R.layout.row_layout,null)
        rowView.txt_row_name.text = users[position].name.toString()

        rowView.setOnClickListener{
            //edt_name.setText(rowView.txt_row_name.text.toString())
        }

        return rowView

    }

    override fun getItem(position: Int): Any {
        return users[position]
    }

    override fun getItemId(position: Int): Long {
        return users[position].id.toLong()
    }

    override fun getCount(): Int {
        return users.size
    }

}