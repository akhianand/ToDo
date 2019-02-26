package com.akhilesh.todo

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.app.Dialog
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.add_dialog.*
import kotlinx.android.synthetic.main.content_main.*
import kotlinx.android.synthetic.main.todo_fragment.*
import java.util.*
import android.content.DialogInterface
import android.content.Intent
import android.view.MenuItem


class ToDoFragment : AppCompatActivity() {

    private val dbHelper = ToDoDatabaseHelper(this)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.todo_fragment)

        val actionBar = actionBar
        actionBar.setDisplayHomeAsUpEnabled(true)

        val headline =  intent.getStringExtra("headline")
        val deadline =  intent.getStringExtra("deadline")
        val description =  intent.getStringExtra("description")
        val id =  intent.getIntExtra("id", -1)

        Toast.makeText(applicationContext,""+id, Toast.LENGTH_SHORT).show()

        txtFragHeadline.setText(headline)
        txtFragContent.setText(description)
        txtFragDate.setText(deadline)


        imageDelete.setOnClickListener{ _ ->

            AlertDialog.Builder(this)
                    .setTitle("Delete entry")
                    .setMessage("Are you sure you want to delete this ToDo?")
                    .setPositiveButton(android.R.string.yes) { dialog, which ->
                        val intent = Intent(this, MainActivity::class.java)
                        startActivity(intent)
                    }

                    // A null listener allows the button to dismiss the dialog and take no further action.
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
                    dialog.editDeadline.setText("" + monthOfYear + "/" + dayOfMonth + "/" + year)
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


    override  fun onOptionsItemSelected(item: MenuItem): Boolean {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        return true
    }



    fun validate(headline :String , description: String, deadline:String) : Boolean{

        if (headline == "")  return false

        if (description == "")  return false

        if (deadline == "")  return false

        return true
    }
}