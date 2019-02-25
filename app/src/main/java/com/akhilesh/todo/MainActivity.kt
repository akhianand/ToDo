package com.akhilesh.todo

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.ContentValues
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.app.FragmentTransaction
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast

import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.add_dialog.*

class MainActivity : AppCompatActivity() {

    private val dbHelper = ToDoDatabaseHelper(this)
    private val dbCRUD = ToDoSQLiteCRUD(dbHelper)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)



        fab.setOnClickListener { _ ->
            val dialog = Dialog(this, R.style.MyAlertDialogStyle)
            dialog.setContentView(R.layout.add_dialog)
            dialog.setCancelable(true)

            val headLine  =  dialog.editHeadline.text.toString()
            val description  =  dialog.editDescription.text.toString()
            val deadline  =  dialog.editDeadline.text.toString()


            dialog.submitButton.setOnClickListener { _ ->
                dbCRUD.addData(headLine, description, deadline)
                Toast.makeText(this, "Note Added Successfully", Toast.LENGTH_SHORT).show()
                dialog.dismiss()
            }
            dialog.show()

        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }




}
