package com.example.chatapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.example.chatapp.model.ChatViewModel
import com.example.chatapp.model.MCQuestion
import com.example.chatapp.model.Quiz
import com.example.chatapp.model.TFQuestion
import com.example.chatapp.ui.theme.AppTheme

val exampleQuiz = Quiz(
    name = "Example Quiz",
    questions = listOf(
        TFQuestion("True or False 1"),
        MCQuestion("Multiple Choice 1"),
        TFQuestion("True or False 1"),
        MCQuestion("Multiple Choice 1"),
        TFQuestion("True or False 1"),
        MCQuestion("Multiple Choice 1"),
        TFQuestion("True or False 1"),
        MCQuestion("Multiple Choice 1"),
    )
)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AppTheme{
//                Scaffold(
//                    topBar = { TopAppBar() },
//                    modifier = Modifier
//                    //.statusBarsPadding()
//                    .navigationBarsPadding()
//                    .fillMaxSize()) { innerPadding ->
//                    ChatScreen2(
//                        ChatViewModel(exampleQuiz),
//                        contentPadding = innerPadding
//                    )
//                }
                QuizApp()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBar(modifier: Modifier = Modifier) {
    CenterAlignedTopAppBar(
        title = {
            Text(
                text = stringResource(R.string.app_name),
                fontSize = 26.sp
            )
        },
        modifier = modifier
    )
}


@Preview(showBackground = true)
@Composable
fun AppPreview() {
    AppTheme {
        Scaffold(
            topBar = { TopAppBar() },
            modifier = Modifier
                .statusBarsPadding()
                .fillMaxSize()) { innerPadding ->
            ChatScreen2(ChatViewModel(exampleQuiz),
                contentPadding = innerPadding
            )
        }
    }
}