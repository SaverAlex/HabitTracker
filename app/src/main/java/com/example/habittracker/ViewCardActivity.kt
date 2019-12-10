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

    private var id: Int = -1
    private var name: String? = null
    private var description: String? = null
    private var period: Int = -1

    private var completedDays: ArrayList<LocalDate> = ArrayList() // Необходимо хранить в БД
    private lateinit var today:LocalDate
    private var daysPassed = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_card)
        setSupportActionBar(toolbar)

        db = DBHelper(this)
        showCard()
        completedDays = DateArray.deserialize(listTasks[position].completedDays)
        assemblyCalendar()

        delete.setOnClickListener{
            val task = Task(id,name,description,period,null)
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

        id = listTasks[position].id
        name = listTasks[position].name
        description = listTasks[position].description
        period = listTasks[position].period

        viewCard_name.text = listTasks[position].name
        viewCard_description.text = getString(R.string.view_card_description,description)
        viewCard_period.text = getString(R.string.view_card_period,period)
    }

    private fun assemblyCalendar (){

        AndroidThreeTen.init(this)
        today = LocalDate.now()
        if (completedDays.isNotEmpty()) daysPassed = today.dayOfYear - completedDays[0].dayOfYear
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
                resultView.visibility = View.VISIBLE
                if (completedDays.isNotEmpty()) {
                    daysPassed = if (completedDays[0].dayOfYear + period <= today.dayOfYear)
                        completedDays[completedDays.size - 1].dayOfYear - completedDays[0].dayOfYear + 1
                    else
                        today.dayOfYear - completedDays[0].dayOfYear + 1
                    resultView.text = AnalyzeProgress.start(
                        period,
                        completedDays.size,
                        daysPassed
                    )
                }
                else resultView.text = "У тебя пока никаких результатов нет"
            } else {
                calendarView.maxRowCount =  6
                calendarView.hasBoundaries =  true
                resultView.visibility = View.INVISIBLE
            }
        }
        var numberOfMonths = 0
        if (completedDays.isNotEmpty()) numberOfMonths = (completedDays[0].dayOfMonth + period) / 30
        calendarView.setup(YearMonth.now(), YearMonth.now().plusMonths(numberOfMonths.toLong()),DayOfWeek.MONDAY)
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
                        container.textView.setBackgroundResource(DateArray.drawableRes(completedDays,day,period))
                        if (today == day.date){
                            flag.text = "Выполнено"
                            flag.isEnabled = false
                        }
                        if ((today.dayOfYear >= completedDays[0].dayOfYear + period) || (daysPassed >= period)) {
                            // В день окончания плашка всё равно отображает Выполнено
                            flag.text = "Период завершён"
                            flag.isEnabled = false
                        }
                    }
                    today == day.date -> {
                        flag.setOnClickListener {
                            daysPassed++
                            if (daysPassed < period) flag.text = "Выполнено"
                            else flag.text = "Период завершён"
                            flag.isEnabled = false

                            completedDays.add(day.date)
                            container.textView.setBackgroundResource(DateArray.drawableRes(completedDays,day,period))
                            val task = Task(id,name,description,period,
                                DateArray.serialization(completedDays))
                            db.updateTask(task)
                        }
                    }
                    day.date.dayOfYear < today.dayOfYear -> {
                        // Не происходит окрашивание в красный цвет после периода окончания
                        if (completedDays.isNotEmpty() && (completedDays[0].dayOfYear < day.date.dayOfYear) && daysPassed < period){
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



