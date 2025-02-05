package com.example.chatapp.model

enum class QuestionType{
    MULTIPLE_CHOICE,
    TRUE_OR_FALSE,
    MULTIPLE_ANSWERS
}

open class Question(
    val content : String = "",
    val type : QuestionType = QuestionType.TRUE_OR_FALSE
) {

    open fun getAnswers() : List<String> {
        return listOf("")
    }

    open fun getAlternatives() : List<String> {
        return listOf("")
    }
}

class TFQuestion(content: String) : Question(
    content = content,
    type = QuestionType.TRUE_OR_FALSE
) {
    override fun getAnswers(): List<String> {
        return listOf(
            "1. True",
            "2. False"
        )
    }
    override fun getAlternatives(): List<String> {
        return listOf(
            "TRUE",
            "FALSE"
        )
    }
}

class MCQuestion(content: String)  : Question(
    content = content,
    type = QuestionType.MULTIPLE_CHOICE
){
    override fun getAnswers(): List<String> {
        return listOf(
        "A. Statement a",
        "B. Statement b",
        "C. Statement c",
        "D. Statement d"
        )
    }
    override fun getAlternatives(): List<String> {
        return listOf(
            "A",
            "B",
            "C",
            "D",
        )
    }
}

class MAQuestion(content: String)  : Question(
    content = content,
    type = QuestionType.MULTIPLE_ANSWERS
){
    override fun getAnswers(): List<String> {
        return listOf(
            "A. Statement a",
            "B. Statement b",
            "C. Statement c",
            "D. Statement d"
        )
    }

    override fun getAlternatives(): List<String> {
        return listOf(
            "A",
            "B",
            "C",
            "D",
        )
    }
}

val exampleQuestions = mutableListOf(
    TFQuestion("True Or False Question Example"),
    MCQuestion("Multiple Question Question Example"),
    MAQuestion("Multiple Answers Question Example")
)