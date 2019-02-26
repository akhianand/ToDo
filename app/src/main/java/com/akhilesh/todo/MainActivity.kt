package com.akhilesh.todo


import android.app.DatePickerDialog
import android.app.Dialog
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.add_dialog.*
import kotlinx.android.synthetic.main.content_main.*
import java.util.*


class MainActivity : AppCompatActivity() {

    private val dbHelper = ToDoDatabaseHelper(this)
    private val numbers = arrayOf("01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right)

        fab.setOnClickListener { _ ->
            val dialog = Dialog(this, R.style.MyAlertDialogStyle)
            dialog.setContentView(R.layout.add_dialog)
            dialog.setCancelable(true)
            dialog.calendarButton.setOnClickListener { _ ->
                val c = Calendar.getInstance()
                val year = c.get(Calendar.YEAR)
                val month = c.get(Calendar.MONTH)
                val day = c.get(Calendar.DAY_OF_MONTH)
                val dpd = DatePickerDialog(this, DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                    val builder = StringBuilder(numbers[monthOfYear])
                    builder.append("/")
                    builder.append(dayOfMonth)
                    builder.append("/")
                    builder.append(year)
                    dialog.editDeadline.setText(builder.toString())
                }, year, month, day)

                dpd.show()
            }


            dialog.submitButton.setOnClickListener { _ ->
                val headLine = dialog.editHeadline.text.toString()
                val description = dialog.editDescription.text.toString()
                val deadline = dialog.editDeadline.text.toString()
                val data = ArrayList(dbHelper.getAllData())
                if (validate(headLine, description, deadline)) {
                    dbHelper.addData(data.size, headLine, description, deadline)
                    Toast.makeText(this, "Note Added Successfully", Toast.LENGTH_SHORT).show()
                    refreshList()
                    dialog.dismiss()
                } else {
                    Toast.makeText(this, "Fields cant be left Blank", Toast.LENGTH_SHORT).show()
                }
            }
            dialog.show()
        }
    }

    fun refreshList() {
        val data = ArrayList(dbHelper.getAllData())
        if (data.size > 0) txtNoToDo.visibility = View.INVISIBLE
        todorecycler.layoutManager = LinearLayoutManager(this)
        todorecycler.adapter = ToDoAdapter(data, this)
    }


    fun validate(headline: String, description: String, deadline: String): Boolean {
        if (headline == "") return false
        if (description == "") return false
        if (deadline == "") return false
        return true
    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_settings -> {
                val transaction = supportFragmentManager.beginTransaction()
                transaction.add(R.id.container, Settings())
                return (transaction.commit() != -1)
            }
            else -> super.onOptionsItemSelected(item)
        }
    }




    override fun onResume() {
        super.onResume()
        refreshList()
    }
}
