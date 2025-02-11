package com.example.chatapp.model

import java.util.Date
import java.util.UUID


class Report(
    val quizId : UUID,
    val score : Int,
    val date: Date = Date()
){
    val id : UUID = UUID.randomUUID() //implement id-shock workaround

}