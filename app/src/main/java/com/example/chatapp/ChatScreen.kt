package com.example.chatapp

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.example.chatapp.model.ChatMessage
import com.example.chatapp.model.MCQuestion
import com.example.chatapp.model.QuestionMessage
import com.example.chatapp.model.TFQuestion
import com.example.chatapp.model.addQuestion
import com.example.chatapp.ui.theme.Pink80
import com.example.chatapp.ui.theme.Purple80


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
                .background(if (message.isFromMe) Purple80 else Pink80)
                .padding(8.dp)
        ) {
            message.message()
        }
    }
}

@Composable
fun ChatScreen(
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp)
){
    //val scope = rememberCoroutineScope()
    val listState = rememberLazyListState()
    val messageList = remember { mutableStateListOf<ChatMessage>() }

    if(messageList.isEmpty()){
//        messageList.add(QuestionMessage(TFQuestion("True Or False Question Example")))
        messageList.addQuestion(QuestionMessage(MCQuestion("Multiple Choice Question Example")))
//        messageList.add(QuestionMessage(MAQuestion("Multiple Answers Question Example")))
    }
    LaunchedEffect(messageList.size) {
        if (messageList.isNotEmpty()) {
            // Scroll to the last item with animation
            listState.animateScrollToItem(messageList.size - 1)
        }
    }
    ConstraintLayout (modifier = modifier.fillMaxSize()) {
        val (messages, addMsgButton) = createRefs()

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
            items(messageList) {
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

        Button(
            onClick = { //TO DO!!
                messageList.add(QuestionMessage(TFQuestion("Additional Question"))) },
            modifier = Modifier
                .constrainAs(addMsgButton) {
                    bottom.linkTo(parent.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
        ){
            Text("Add New Message")
        }

    }

}

@Preview
@Composable
fun ChatPreview(){
    ChatScreen()
}