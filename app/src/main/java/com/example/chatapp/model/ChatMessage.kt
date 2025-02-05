package com.example.chatapp.model

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.snapshots.SnapshotStateList

enum class ChatMessageType{
    QUESTION, ALTERNATIVE, ANSWER
}

open class ChatMessage(
    val messageType: ChatMessageType,
    val isFromMe: Boolean,
    val message: @Composable () -> Unit,
    val question: Question? = null
)


class QuestionMessage(question: Question) : ChatMessage(
    messageType = ChatMessageType.QUESTION,
    isFromMe = false,
    message = { Text(question.content) },
    question = question
){}

class AlternativeMessage(message : @Composable () -> Unit) : ChatMessage(
    messageType = ChatMessageType.ALTERNATIVE,
    isFromMe = false,
    message = message
)

class AnswerMessage(answer: Answer) : ChatMessage(
    messageType = ChatMessageType.ANSWER,
    isFromMe = true,
    message = @Composable { Text(answer.content) }
){}

fun MutableList<ChatMessage>.addQuestion(questionMessage: QuestionMessage){
    this.add(questionMessage)
    val lastMessage = this.last()
    if(lastMessage.messageType == ChatMessageType.QUESTION &&
        lastMessage.question != null) {
        val altMessage = @Composable {
            Column {
                lastMessage.question.getAnswers().forEach {
                    Text(it)
                }
            }
        }
        this.add(AlternativeMessage(altMessage))
    }
}
