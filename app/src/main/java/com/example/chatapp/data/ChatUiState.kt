package com.example.chatapp.data

import com.example.chatapp.model.ChatMessage

data class ChatUiState(
    val messageList : MutableList<ChatMessage> = mutableListOf(),
    val currentQuestionIndex : Int = 0,
    val endOfChat : Boolean = false,
    val currentQuestionsAlternatives : List<String> = listOf(),
) {
}