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
        messages.add(ChatMessage(false){ Text(currentQuestion.content) })
        messages.add(ChatMessage(false, getAlternativesForCurrentQuestion()))
        _uiState.update { currentState ->
            currentState.copy(
                messageList = messages,
                currentQuestionIndex = currentQuestionIndex,
                currentQuestionsAlternatives = currentQuestion.getAlternatives()
            )
        }
    }

    fun restart(){
        currentQuestion = quiz.questions.first()
        answers.clear()
        messages.clear()
        currentQuestionIndex = 0
        _uiState.value = ChatUiState()
        addCurrentQuestionToMessages()
    }

    fun goToNext(){
        if(currentQuestionIndex == quiz.questions.size - 1){
            _uiState.update { currentState ->
                currentState.copy(endOfChat = true)
            }
        } else {
            currentQuestionIndex++
            currentQuestion = quiz.questions[currentQuestionIndex]
            addCurrentQuestionToMessages()
        }
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

    fun submitAnswer(content: String){
        answers.add(Answer(content, currentQuestion))
        val answerMessage = "Response $content Submitted at question ${currentQuestionIndex + 1}"
        messages.add(ChatMessage(true){ Text(answerMessage) })
        _uiState.update { currentState ->
            currentState.copy(
                messageList = messages
            )
        }
        goToNext()
    }

}