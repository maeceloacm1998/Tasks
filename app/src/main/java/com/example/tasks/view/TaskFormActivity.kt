package com.example.tasks.view

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.DatePicker
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.tasks.R
import com.example.tasks.viewmodel.TaskFormViewModel
import kotlinx.android.synthetic.main.activity_register.button_save
import kotlinx.android.synthetic.main.activity_task_form.*
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.time.DayOfWeek
import java.util.*

class TaskFormActivity : AppCompatActivity(), View.OnClickListener,DatePickerDialog.OnDateSetListener {

    private lateinit var mViewModel: TaskFormViewModel
    private var formatDate = SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_task_form)

        mViewModel = ViewModelProvider(this).get(TaskFormViewModel::class.java)
        mViewModel.getPriority()

        // Inicializa eventos
        listeners()
        observe()
    }

    override fun onClick(v: View) {
        val id = v.id
        if (id == R.id.button_save) {
            subbmitTask()
        }

        if (id == R.id.button_date) {
            showDatePicker()
        }
    }

    private fun listeners() {
        button_save.setOnClickListener(this)
        button_date.setOnClickListener(this)
    }

    private fun observe() {
        mViewModel.createTask.observe(this, Observer {
            if (it) {
                Intent(applicationContext, MainActivity::class.java)
            }
        })

        mViewModel.priorityList.observe(this, Observer {
            val list: MutableList<String> = arrayListOf()
            for (item in it) {
                list.add(item.description)
            }

            setPrioritysInSpinner(list)
        })
    }

    private fun showDatePicker(){
        val date = Calendar.getInstance()
        val year = date.get(Calendar.YEAR)
        val month = date.get(Calendar.MONTH)
        val dayOfMonth = date.get(Calendar.DAY_OF_MONTH)

        DatePickerDialog(this,this,year,month, dayOfMonth).show()
    }

    override fun onDateSet(p0: DatePicker?, year: Int, month: Int, dayOfWeek: Int) {
        val caledar = Calendar.getInstance()
        caledar.set(year,month,dayOfWeek)

        val date = formatDate.format(caledar.time)
        button_date.text = date
    }

    private fun subbmitTask(){
        val description = edit_description.text.toString()
        val completeTask = check_complete.isChecked
        val dueDate = button_date.text.toString()
        val priorityId = spinner_priority.selectedItemPosition

        mViewModel.createTask(priorityId,description,dueDate,completeTask)
    }

    private fun setPrioritysInSpinner(list: MutableList<String>) {
        val adapter = ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item, list)
        spinner_priority.adapter = adapter
    }
}
