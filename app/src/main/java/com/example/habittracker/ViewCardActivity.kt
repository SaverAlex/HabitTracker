package com.example.habittracker

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.habittracker.DataBase.DBHelper
import com.example.habittracker.DataBase.Task

import kotlinx.android.synthetic.main.activity_view_card.*
import kotlinx.android.synthetic.main.content_view_card.*
import kotlin.collections.ArrayList
import androidx.recyclerview.widget.RecyclerView
import com.jakewharton.threetenabp.AndroidThreeTen
import com.kizitonwose.calendarview.model.*
import com.kizitonwose.calendarview.ui.DayBinder
import com.kizitonwose.calendarview.ui.ViewContainer
import kotlinx.android.synthetic.main.calendar_day_layout.view.*
import org.threeten.bp.DayOfWeek
import org.threeten.bp.LocalDate
import org.threeten.bp.YearMonth

class ViewCardActivity : AppCompatActivity() {

    companion object {
        const val positionNumber = ""
    }

    internal lateinit var db:DBHelper
    internal var listTasks:List<Task> = ArrayList()
    internal var position = -1

    private var completedDays: ArrayList<LocalDate> = ArrayList() // Необходимо хранить в БД
    private lateinit var today:LocalDate


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_card)
        setSupportActionBar(toolbar)

        db = DBHelper(this)
        showCard()
        completedDays = DateArray.deserialize(listTasks[position].completedDays)
        assemblyCalendar()

        delete.setOnClickListener{
            val task = Task(listTasks[position].id,listTasks[position].name,listTasks[position].description,listTasks[position].period,null)
            db.deleteTask(task)
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

    private fun assemblyCalendar (){

        AndroidThreeTen.init(this)
        today = LocalDate.now()
        class DayViewContainer(view: View) : ViewContainer(view) {
            val textView = view.calendarDayText
        }

        calendarView.inDateStyle = InDateStyle.ALL_MONTHS
        calendarView.outDateStyle = OutDateStyle.END_OF_ROW
        calendarView.scrollMode = ScrollMode.PAGED
        calendarView.orientation = RecyclerView.HORIZONTAL

        weekMode.setOnCheckedChangeListener { _ , isChecked ->
            if (isChecked) {
                calendarView.maxRowCount =  1
                calendarView.hasBoundaries = false
            } else {
                calendarView.maxRowCount =  6
                calendarView.hasBoundaries =  true
            }
        }
        calendarView.setup(YearMonth.now(), YearMonth.now().plusMonths(0),DayOfWeek.MONDAY)
        calendarView.dayBinder = object : DayBinder<DayViewContainer> {
            override fun create(view: View) = DayViewContainer(view)
            override fun bind(container: DayViewContainer, day: CalendarDay) {
                container.textView.text = day.date.dayOfMonth.toString()
                if (day.owner == DayOwner.THIS_MONTH) {
                    container.textView.setTextColor(Color.BLACK)
                } else {
                    container.textView.setTextColor(Color.LTGRAY)
                }
                when{
                    completedDays.contains(day.date) -> {
                        container.textView.setBackgroundResource(DateArray.drawableRes(completedDays,day,listTasks[position].period))
                        if (today.dayOfYear >= completedDays[0].dayOfYear + listTasks[position].period) {
                            //flag.visibility = View.GONE
                            flag.text = "Период завершён"
                            flag.isEnabled = false
                        }
                        if (today == day.date){
                            flag.text = "Выполнено"
                            flag.setBackgroundColor(getColor(R.color.colorToolbar))
                        }
                    }
                    today == day.date -> {
                        flag.setOnClickListener {
                            flag.text = "Выполнено"
                            flag.setBackgroundColor(getColor(R.color.colorToolbar))

                            completedDays.add(day.date)
                            container.textView.setBackgroundResource(DateArray.drawableRes(completedDays,day,listTasks[position].period))
                            val task = Task(listTasks[position].id,listTasks[position].name,listTasks[position].description,listTasks[position].period,
                                DateArray.serialization(completedDays))
                            db.updateTask(task)
                        }
                    }
                    day.date.dayOfYear < today.dayOfYear -> {
                        if (completedDays.isNotEmpty() && (completedDays[0].dayOfYear < day.date.dayOfYear) && flag.isEnabled){
                            container.textView.setBackgroundColor(Color.RED)
                        }
                    }

                }

            }
        }

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



