package com.example.chatapp.model

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.lifecycle.ViewModel
import com.example.chatapp.data.ChatUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class ChatViewModel(val quiz : Quiz): ViewModel() {

    private val _uiState = MutableStateFlow(ChatUiState())
    val uiState: StateFlow<ChatUiState> = _uiState.asStateFlow()


    private var currentQuestion : Question =  quiz.questions.first()
    private val answers = mutableListOf<Answer>()
    private val messages = mutableListOf<ChatMessage>()
    private var currentQuestionIndex = 0

    init{
        addCurrentQuestionToMessages()

    }

    private fun addCurrentQuestionToMessages(){
//            currentQuestion = quiz.questions[uiState.value.currentQuestionIndex]
        messages.add(ChatMessage(false){ Text(currentQuestion.content) })
        messages.add(ChatMessage(false, getAlternativesForCurrentQuestion()))
        _uiState.update { currentState ->
            currentState.copy(messageList = messages, currentQuestionIndex = currentQuestionIndex)
        }
    }

    fun goToNext(){
        currentQuestionIndex++
        currentQuestion = quiz.questions[currentQuestionIndex]
        addCurrentQuestionToMessages()
    }


    private fun getAlternativesForCurrentQuestion() : @Composable () -> Unit {
        val alternativesMessage = @Composable {
            Column {
                currentQuestion.getAnswers().forEach {
                    Text(it)
                }
            }
        }
        return alternativesMessage
    }
}