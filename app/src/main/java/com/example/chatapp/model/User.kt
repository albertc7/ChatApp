package com.example.chatapp.model

object User{
    val name : String = "User01"
    val age : Int = 25
    val gender : String = "Male"
    val occupation : String = "Firefighter"
    val reports : MutableList<Report> = mutableListOf<Report>()

    fun addReport(report: Report){
        reports.add(report)
    }
}