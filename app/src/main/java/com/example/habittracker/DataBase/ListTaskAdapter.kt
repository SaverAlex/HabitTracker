package com.example.habittracker.DataBase

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.example.habittracker.R
import com.example.habittracker.ViewCardActivity
import kotlinx.android.synthetic.main.row_layout.view.*

class ListTaskAdapter (internal var activity: Activity,
                       internal var listTasks:List<Task>) : BaseAdapter() {

    internal var inflater: LayoutInflater

    init {
        inflater = activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val rowView: View
        rowView = inflater.inflate(R.layout.row_layout,null)
        rowView.txt_row_name.text = listTasks[position].name.toString()

        rowView.setOnClickListener{
            val intent = Intent(activity,ViewCardActivity::class.java)
            intent.putExtra(ViewCardActivity.positionNumber, position)
            activity.startActivityForResult(intent,0)
            activity.recreate()
        }

        return rowView

    }

    override fun getItem(position: Int): Any {
        return listTasks[position]
    }

    override fun getItemId(position: Int): Long {
        return listTasks[position].id.toLong()
    }

    override fun getCount(): Int {
        return listTasks.size
    }

}