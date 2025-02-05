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
}

class MCQuestion(content: String)  : Question(
    content = content,
    type = QuestionType.MULTIPLE_CHOICE
){
    override fun getAnswers(): List<String> {
        return listOf(
        "1. Statement a",
        "2. Statement b",
        "3. Statement c",
        "4. Statement d"
        )
    }
}

class MAQuestion(content: String)  : Question(
    content = content,
    type = QuestionType.MULTIPLE_ANSWERS
){
    override fun getAnswers(): List<String> {
        return listOf(
            "1. Statement a",
            "2. Statement b",
            "3. Statement c",
            "4. Statement d"
        )
    }
}

val exampleQuestions = mutableListOf(
    TFQuestion("True Or False Question Example"),
    MCQuestion("Multiple Question Question Example"),
    MAQuestion("Multiple Answers Question Example")
)