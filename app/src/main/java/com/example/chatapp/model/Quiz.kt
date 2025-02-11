package com.example.chatapp.model

import java.util.UUID

class Quiz(
    val name : String,
    val questions : List<Question>,
    val typicalDuration: Int = 0,
    val description : String = ""
) {
    val id : UUID = UUID.randomUUID()
    val size : Int = questions.size
}