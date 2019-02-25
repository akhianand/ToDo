package com.akhilesh.todo

import android.provider.BaseColumns

object ToDoReaderContract {
    object ToDoEntry : BaseColumns {
        const val TABLE_NAME = "entry"
        const val COLUMN_NAME_HEADLINE = "headline"
        const val COLUMN_NAME_DESCRIPTION = "description"
        const val COLUMN_NAME_DEADLINE = "deadline"

    }
}