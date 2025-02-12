package com.example.chatapp

import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.chatapp.model.Answer
import com.example.chatapp.model.ChatMessage
import com.example.chatapp.model.ChatViewModel
import com.example.chatapp.model.Quiz
import com.example.chatapp.model.Report
import com.example.chatapp.model.User


/**
 * enum values that represent the screens in the app
 */
enum class QuizScreen(@StringRes val title: Int) {
    Start(title = R.string.app_name),
    Quiz01(title = R.string.quiz_01),
}

@Composable
fun QuizApp(
    navController: NavHostController = rememberNavController()
){
    // Get current back stack entry
    val backStackEntry by navController.currentBackStackEntryAsState()
    // Get the name of the current screen
    val currentScreen = QuizScreen.valueOf(
        backStackEntry?.destination?.route ?: QuizScreen.Start.name
    )
    val chatViewModel = ChatViewModel(exampleQuiz)

    Scaffold(
        topBar = {
            QuizTopBar(
                currentScreen = currentScreen,
                canNavigateBack = navController.previousBackStackEntry != null,
                navigateUp = { navController.navigateUp() }
            )
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = QuizScreen.Start.name,
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(innerPadding)
        ) {
            composable(route = QuizScreen.Start.name) {
                QuizSelection(
                    onQuizSelection = {
                        navController.navigate(QuizScreen.Quiz01.name)
                    },
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp)//dimensionResource(R.dimen.padding_medium))
                )
            }

            composable(route = QuizScreen.Quiz01.name) {
                ChatScreen2(chatViewModel)
            }


        }
    }

}

@Composable
fun QuizSelection(
    onQuizSelection : () -> Unit,
    modifier: Modifier = Modifier
){
    Surface(modifier = modifier){
        Box(){
            Button(onClick = onQuizSelection) {
                Text("Start Quiz 01")
            }
        }
    }

}

@Composable
fun ChatItem(message :  ChatMessage, modifier: Modifier = Modifier){
    Box( modifier = modifier) {
        Box(
            modifier = Modifier
                .align(if (message.isFromMe) Alignment.CenterEnd else Alignment.CenterStart)
                .clip(
                    RoundedCornerShape(
                        topStart = 48f,
                        topEnd = 48f,
                        bottomStart = if (message.isFromMe) 48f else 0f,
                        bottomEnd = if (message.isFromMe) 0f else 48f
                    )
                )
                .background(
                    if (message.isFromMe)
                        MaterialTheme.colorScheme.primaryContainer
                    else
                        MaterialTheme.colorScheme.tertiaryContainer
                )
                .padding(8.dp)
        ) {
            message.message()
        }
    }
}

@Preview
@Composable
fun QuizEndDialog(
    onDismissRequest: () -> Unit ={},
    onConfirmation: () -> Unit ={},
) {
    AlertDialog(
        icon = { Icons.Default.Check },
        title = { Text(text = "Nice!") },
        text = {
            Text(text = "You just finished your quiz. Would you like to give it another try?")
        },
        onDismissRequest = { onDismissRequest() },
        confirmButton = {
            TextButton(
                onClick = { onConfirmation() }
            ) {
                Text("Yes")
            }
        },
        dismissButton = {
            TextButton(
                onClick = { onDismissRequest() }
            ) {
                Text("No")
            }
        }
    )
}

@Preview
@Composable
fun ChatPreview(){
    ChatScreen2(ChatViewModel(exampleQuiz))
}

/**
 * Composable that displays the topBar and displays back button if back navigation is possible.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QuizTopBar(
    currentScreen: QuizScreen,
    canNavigateBack: Boolean,
    navigateUp: () -> Unit,
    modifier: Modifier = Modifier
) {
    androidx.compose.material3.TopAppBar(
        title = { Text(stringResource(currentScreen.title)) },
        colors = TopAppBarDefaults.mediumTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
        ),
        modifier = modifier,
        navigationIcon = {
            if (canNavigateBack) {
                IconButton(onClick = navigateUp) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Go Back"
                    )
                }
            }
        }
    )
}

@Composable
fun ChatScreen2(
    chatViewModel: ChatViewModel,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp)
){
    //Setting up Chat state variables
    val listState = rememberLazyListState()
    val chatUiState by chatViewModel.uiState.collectAsState()

    if(chatUiState.endOfChat){
        QuizEndDialog(
            onDismissRequest = { },
            onConfirmation = { chatViewModel.restart() }
        )
    }

    //Applying Auto-Scroll to last message queued
    LaunchedEffect(chatUiState.messageList.size) {
        if (chatUiState.messageList.isNotEmpty()) {
            // Scroll to the last item with animation
            listState.animateScrollToItem(chatUiState.messageList.size - 1)
        }
    }

    Surface(color = MaterialTheme.colorScheme.background) {
        //Setting relative layout
        ConstraintLayout (modifier = modifier.fillMaxSize()) {
            val (messages, addMsgButton) = createRefs()

            //Scrollable Chat
            LazyColumn(
                state = listState,
                contentPadding = contentPadding,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp)
                    .constrainAs(messages) {
                        top.linkTo(parent.top)
                        bottom.linkTo(addMsgButton.top)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        height = Dimension.fillToConstraints
                    }
            ) {
                items(chatUiState.messageList) {
                        message -> run {
                    ChatItem(
                        message = message,
                        modifier = Modifier
                            .padding(vertical = 4.dp)
                            .fillParentMaxWidth()
                    )
                }
                }
            }

            //Actions Buttons on Bottom of Screen
            Row(modifier = Modifier
                .constrainAs(addMsgButton) {
                    bottom.linkTo(parent.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
                .fillMaxWidth(0.9F)
            ) {
                chatUiState.currentQuestionsAlternatives.forEach {
                    Button(
                        onClick = { chatViewModel.submitAnswer(it) },
                        modifier = Modifier.padding(horizontal = 4.dp).weight(1f)
                    ) { Text(it) }
                }

            }
        }
    }
}