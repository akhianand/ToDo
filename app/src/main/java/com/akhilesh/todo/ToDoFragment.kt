package com.akhilesh.todo

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.app.Dialog
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import kotlinx.android.synthetic.main.add_dialog.*
import kotlinx.android.synthetic.main.todo_fragment.*
import java.util.*
import android.content.Intent
import android.view.MenuItem
import android.support.v4.app.NavUtils




class ToDoFragment : AppCompatActivity() {

    private val dbHelper = ToDoDatabaseHelper(this)
    val numbers = arrayOf("01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12")


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.todo_fragment)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right)


        val headline =  intent.getStringExtra("headline")
        val deadline =  intent.getStringExtra("deadline")
        val description =  intent.getStringExtra("description")
        val id =  intent.getIntExtra("id", -1)


        txtFragHeadline.setText(headline)
        txtFragContent.setText(description)
        txtFragDate.setText(deadline)


        imageDelete.setOnClickListener{ _ ->
            AlertDialog.Builder(this)
                    .setTitle("Delete entry")
                    .setMessage("Are you sure you want to delete this ToDo?")
                    .setPositiveButton("Yes") { dialog, which ->
                        dbHelper.removeData(id)
                        val intent = Intent(this, MainActivity::class.java)
                        startActivity(intent)
                    }
                    .setNegativeButton(android.R.string.no, null)
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show()

        }


        imageEdit.setOnClickListener{ _ ->

            val dialog = Dialog(this, R.style.MyAlertDialogStyle)
            dialog.setContentView(R.layout.add_dialog)
            dialog.setCancelable(true)


            dialog.editHeadline.setText(headline)
            dialog.editDescription.setText(description)
            dialog.editDeadline.setText(deadline)

            dialog.calendarButton.setOnClickListener{ _ ->
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
                val headLined  =  dialog.editHeadline.text.toString()
                val descriptiod  =  dialog.editDescription.text.toString()
                val deadlined  =  dialog.editDeadline.text.toString()
                txtFragHeadline.setText(headLined)
                txtFragContent.setText(descriptiod)
                txtFragDate.setText(deadlined)

                if(validate(headLined, descriptiod, deadlined)) {
                    dbHelper.editData(id, headLined, descriptiod, deadlined)
                    Toast.makeText(this, "Note Edited Successfully", Toast.LENGTH_SHORT).show()
                    dialog.dismiss()
                }else{
                    Toast.makeText(this, "Fields cant be left Blank", Toast.LENGTH_SHORT).show()
                }
            }
            dialog.show()
        }
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        onBackPressed()
        return true
    }

    fun validate(headline :String , description: String, deadline:String) : Boolean{
        if (headline == "")  return false
        if (description == "")  return false
        if (deadline == "")  return false
        return true
    }
}