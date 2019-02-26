package com.akhilesh.todo

interface IToDoDataOperations {
    fun addData(id: Int, headline : String, descriprion:String, deadline : String) : Boolean
    fun editData(id:Int, headline : String, descriprion:String, deadline : String) : Boolean
    fun removeData(id : Int) :Boolean
    fun getAllData() : MutableList<ToDoDataEntry>
}