package com.chatappfrontend.messages.ui

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.chatappfrontend.domain.model.Message
import com.chatappfrontend.messages.viewmodel.ChatViewModel
import com.example.common_android.R

private const val MAX_TEXT_FIELD_LINES = 12

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatScreen(
    modifier: Modifier = Modifier,
    chatId: String?,
    userId: String?,
    username: String,
    onBackPressed: () -> Unit,
    viewModel: ChatViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.initializeData(chatId = chatId)
    }

    Content(
        modifier = modifier.imePadding(),
        chatId = chatId,
        userId = userId,
        username = username,
        messages = uiState.messages,
        textInput = uiState.textFieldInput,
        setMessage = viewModel::setMessage,
        sendMessage = { chatId, userId ->
            viewModel.sendMessage(chatId = chatId, recipientId = userId)
        },
        onBackPressed = onBackPressed
    )

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun Content(
    modifier: Modifier = Modifier,
    chatId: String?,
    userId: String?,
    username: String,
    messages: List<Message>,
    textInput: String,
    setMessage: (String) -> Unit,
    sendMessage: (String, String) -> Unit,
    onBackPressed: () -> Unit
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text(text = username) },
                navigationIcon = {
                    IconButton(onClick = {
                        Log.d("ChatScreen", "Disconnecting WebSocket and going back")
                        onBackPressed()
                    }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            MessageList(
                modifier = Modifier
                    .fillMaxSize()
                    .weight(1f),
                messages = messages,
                userId = userId
            )
            MessageInput(
                textInput = textInput,
                setMessage = setMessage,
                sendMessage = {
                    sendMessage(chatId ?: "", userId ?: "")
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .padding(
                        start = 16.dp,
                        top = 8.dp,
                        end = 16.dp,
                        bottom = 8.dp
                    ),
            )
        }
    }
}

@Composable
private fun MessageList(
    modifier: Modifier = Modifier,
    messages: List<Message>,
    userId: String?
) {
    val listState = rememberLazyListState()
    val showTimeMap = remember { (mutableStateMapOf<String, Boolean>()) }

    Box(
        modifier
    ) {
        LazyColumn(
            state = listState,
            modifier = Modifier
                .fillMaxSize(),
            contentPadding = PaddingValues(
                start = 16.dp,
                end = 16.dp,
                top = 8.dp,
                bottom = 8.dp
            ),
            reverseLayout = true
        ) {
            items(messages) { message ->
                val showTime = showTimeMap[message.id] ?: false

                Message(
                    message = message,
                    userId = userId,
                    showTime = showTimeMap[message.id] ?: false,
                    toggleShowTime = {
                        showTimeMap[message.id] = !showTime
                    }
                )
            }
        }
    }
}

@Composable
private fun Message(
    modifier: Modifier = Modifier,
    message: Message,
    userId: String?,
    showTime: Boolean,
    toggleShowTime: () -> Unit

) {
    val messageAlignment = if (message.senderId == userId) {
        Alignment.Start
    } else {
        Alignment.End
    }
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = if (message.senderId == userId) {
            Arrangement.Start
        } else {
            Arrangement.End
        },
        verticalAlignment = Alignment.CenterVertically
    ) {

        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = messageAlignment,
            verticalArrangement = Arrangement.Center
        ) {
            Card(
                modifier = modifier
                    .padding(top = 8.dp, bottom = 8.dp),
                shape = CircleShape,
                onClick = toggleShowTime
            ) {
                Text(
                    text = message.content,
                    modifier = Modifier
                        .padding(16.dp)
                        .background(
                            color = MaterialTheme.colorScheme.primaryContainer,
                            shape = RectangleShape
                        )
                )
            }
            if (showTime) {
                Text(
                    text = message.createdAt,
                    style = MaterialTheme.typography.bodySmall,
                )
            }
            if (message.status == Message.Status.FAILED) {
                toggleShowTime()
                Text(
                    text = stringResource(R.string.message_transmission_failure),
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodySmall
                )
            }
//            else if (message.status == Message.Status.SENDING) {
//                showTime = false
//                Text(
//                    text = stringResource(R.string.message_sending),
//                    style = MaterialTheme.typography.bodySmall
//                )
//            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun MessageInput(
    modifier: Modifier,
    textInput: String,
    setMessage: (String) -> Unit,
    sendMessage: () -> Unit
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        OutlinedTextField(
            value = textInput,
            onValueChange = setMessage,
            maxLines = MAX_TEXT_FIELD_LINES,
            modifier = Modifier
                .weight(1f)
        )
        Spacer(modifier = Modifier.width(8.dp))
        SendButton(
            sendMessage = sendMessage,
            modifier = Modifier
        )
    }
}

@Composable
private fun SendButton(
    modifier: Modifier,
    sendMessage: () -> Unit
) {
    Button(
        onClick = sendMessage,
        modifier = modifier,
    ) {
        Icon(
            imageVector = Icons.AutoMirrored.Filled.Send,
            contentDescription = "Send",
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun ChatScreenPreview() {
    Content(
        chatId = "123",
        userId = "456",
        messages = listOf(
            Message(
                id = "1",
                content = "Hello!",
                senderId = "456",
                recipientId = "123",
                createdAt = "2023-10-01T12:00:00Z",
                isRead = true,
                chatId = "TODO()",
            ),
            Message(
                id = "2",
                content = "Hi there!",
                senderId = "123",
                recipientId = "456",
                createdAt = "2023-10-01T12:01:00Z",
                isRead = false,
                chatId = "TODO()",
            )
        ),
        textInput = "",
        setMessage = {},
        sendMessage = { _, _ -> },
        onBackPressed = {},
        username = "alex",
    )
}