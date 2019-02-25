package com.akhilesh.todo

interface IToDoDataOperations {
    fun addData(headline : String, descriprion:String, deadline : String)
    fun removeData(id : Int)
    fun getAllData()
}