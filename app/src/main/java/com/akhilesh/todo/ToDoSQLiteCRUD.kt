package com.akhilesh.todo

import android.content.ContentValues

class ToDoSQLiteCRUD(private val dbHelper: ToDoDatabaseHelper) : IToDoDataOperations {


    override fun addData(headline: String, descriprion: String, deadline: String) {
        /**
         * @param headline Headline of Note
         * @param descriprion Description
         * @param deadline Deadline of Note
         * This Function allows us to add Data to the SQLite Database
         * */
        fun addData(headline : String, descriprion:String, deadline : String) {
            val db = dbHelper.writableDatabase
            val values = ContentValues().apply {
                put(ToDoReaderContract.ToDoEntry.COLUMN_NAME_HEADLINE, headline)
                put(ToDoReaderContract.ToDoEntry.COLUMN_NAME_DESCRIPTION, descriprion)
                put(ToDoReaderContract.ToDoEntry.COLUMN_NAME_DEADLINE, deadline)
            }
            val newRowId = db?.insert(ToDoReaderContract.ToDoEntry.TABLE_NAME, null, values)
        }

    }

    override fun removeData(id: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getAllData() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}