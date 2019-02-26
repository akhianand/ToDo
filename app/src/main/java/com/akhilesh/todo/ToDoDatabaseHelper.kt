package com.akhilesh.todo

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.akhilesh.todo.ToDoReaderContract.ToDoEntry.TABLE_NAME


class ToDoDatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) , IToDoDataOperations {

    /**
     * @param id ID
     * @param headline Headline of Note
     * @param descriprion Description
     * @param deadline Deadline of Note
     * This Function allows us to add Data to the SQLite Database
     * */

    override fun addData(id: Int, headline: String, descriprion: String, deadline: String) : Boolean {

            val db = this.writableDatabase
            val values = ContentValues().apply {
                put(ToDoReaderContract.ToDoEntry.COLUMN_NAME_ID, id)
                put(ToDoReaderContract.ToDoEntry.COLUMN_NAME_HEADLINE, headline)
                put(ToDoReaderContract.ToDoEntry.COLUMN_NAME_DESCRIPTION, descriprion)
                put(ToDoReaderContract.ToDoEntry.COLUMN_NAME_DEADLINE, deadline)
            }

        println(id)
            val _success = db.insert(TABLE_NAME, null, values)
            db.close()
            return (Integer.parseInt("$_success") != -1)
    }



    /**
     * @param id ID
     * @param headline Headline of Note
     * @param descriprion Description
     * @param deadline Deadline of Note
     * This Function allows us to edit Data to the SQLite Database
     * */
    override fun editData(id: Int, headline: String, descriprion: String, deadline: String) : Boolean {
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put(ToDoReaderContract.ToDoEntry.COLUMN_NAME_HEADLINE, headline)
            put(ToDoReaderContract.ToDoEntry.COLUMN_NAME_DESCRIPTION, descriprion)
            put(ToDoReaderContract.ToDoEntry.COLUMN_NAME_DEADLINE, deadline)
        }
        val selection = "${ToDoReaderContract.ToDoEntry.COLUMN_NAME_ID} LIKE ?"
        val selectionArgs = arrayOf(""+id)
        val _success = db.update(
                ToDoReaderContract.ToDoEntry.TABLE_NAME,
                values,
                selection,
                selectionArgs)
        db.close()
        return (Integer.parseInt("$_success") != -1)



    }


    override fun removeData(id: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getAllData() : MutableList<ToDoDataEntry> {


        val db = readableDatabase
        val selectALLQuery = "SELECT * FROM $TABLE_NAME"
        val cursor = db.rawQuery(selectALLQuery, null)


        val items = mutableListOf<ToDoDataEntry>()
        with(cursor) {
            while (moveToNext()) {
                val itemId = getInt(getColumnIndexOrThrow(ToDoReaderContract.ToDoEntry.COLUMN_NAME_ID))
                val headline = getString(getColumnIndexOrThrow(ToDoReaderContract.ToDoEntry.COLUMN_NAME_HEADLINE))
                val description = getString(getColumnIndexOrThrow(ToDoReaderContract.ToDoEntry.COLUMN_NAME_DESCRIPTION))
                val deadline = getString(getColumnIndexOrThrow(ToDoReaderContract.ToDoEntry.COLUMN_NAME_DEADLINE))
                items.add(ToDoDataEntry(itemId,headline,description, deadline))
            }
        }


        return  items


    }

    val SQL_CREATE_ENTRIES =
            "CREATE TABLE ${ToDoReaderContract.ToDoEntry.TABLE_NAME} (" +
                    "${ToDoReaderContract.ToDoEntry.COLUMN_NAME_ID} INTEGER PRIMARY KEY," +
                    "${ToDoReaderContract.ToDoEntry.COLUMN_NAME_HEADLINE} TEXT," +
                    "${ToDoReaderContract.ToDoEntry.COLUMN_NAME_DESCRIPTION} TEXT," +
                    "${ToDoReaderContract.ToDoEntry.COLUMN_NAME_DEADLINE} TEXT)"

      val SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS ${ToDoReaderContract.ToDoEntry.TABLE_NAME}"



    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(SQL_CREATE_ENTRIES)
    }
    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL(SQL_DELETE_ENTRIES)
        onCreate(db)
    }
    override fun onDowngrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        onUpgrade(db, oldVersion, newVersion)
    }
    companion object {
        const val DATABASE_VERSION = 3
        const val DATABASE_NAME = "ToDoReader.db"
    }






}

