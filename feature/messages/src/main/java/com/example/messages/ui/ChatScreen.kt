package com.example.messages.ui

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.chatappfrontend.domain.model.Message
import com.example.common_android.R
import com.example.messages.viewmodel.ChatViewModel

private const val MAX_TEXT_FIELD_LINES = 12

// recipient username
// recipient userId
// a way to differentiate between messages sent by the user and received messages
// show time each message was sent within that day
// if sent within a week, label the day of the week for the group of messages sent that day
// if sent more than a week, include weekday and day of month


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatScreen(
    modifier: Modifier = Modifier,
    chatId: String?,
    userId: String?,
    username: String?,
    onBackPressed: () -> Unit,
    viewModel: ChatViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.initializeData(chatId = chatId)
    }
    Content(
        modifier = modifier,
        chatId = chatId,
        userId = userId,
        username = username,
        messages = uiState.messages,
        textInput = uiState.textFieldInput,
        isLoading = uiState.isLoading,
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
    username: String?,
    messages: List<Message>,
    textInput: String,
    isLoading: Boolean,
    setMessage: (String) -> Unit,
    sendMessage: (String, String) -> Unit,
    onBackPressed: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = username ?: "") },
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
                },
                modifier = Modifier
            )
        },
        modifier = modifier
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
        ) {
            if (messages.isEmpty()) {
                EmptyMessageListState(modifier = Modifier.weight(1f))
            } else {
                MessageList(
                    messages = messages,
                    userId = userId
                )
            }
            MessageInput(
                textInput = textInput,
                setMessage = setMessage,
                sendMessage = {
                    sendMessage(chatId ?: "", userId ?: "")
                },
                modifier = modifier
                    .padding(
                        start = 16.dp,
                        end = 16.dp
                    )
            )
        }
    }
}

@Composable
private fun EmptyMessageListState(
    modifier: Modifier = Modifier
) {
    Text(
        text = "No messages yet",
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp),
        textAlign = TextAlign.Center
    )
}

@Composable
private fun ColumnScope.MessageList(
    modifier: Modifier = Modifier,
    messages: List<Message>,
    userId: String?
) {
    val listState = rememberLazyListState()

    Box(
        modifier.weight(1f)
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
                Log.d("ChatScreen", "Displaying message: ${message.content}")
                Message(
                    message = message,
                    userId = userId
                )
            }
        }
    }
}

@Composable
private fun Message(
    modifier: Modifier = Modifier,
    message: Message,
    userId: String?
) {
    Row(
        modifier = modifier
            .fillMaxWidth(),
        horizontalArrangement = if (message.senderId == userId) {
            Arrangement.Start
        } else {
            Arrangement.End
        },
        verticalAlignment = Alignment.CenterVertically
    ) {
        var showTime by remember { mutableStateOf(false) }

        Column(
            horizontalAlignment = if (message.senderId == userId) {
                Alignment.Start
            } else {
                Alignment.End
            },
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth()
        ) {
            Card(
                modifier = modifier
                    .padding(top = 8.dp, bottom = 8.dp),
                shape = CircleShape,
                onClick = {
                    showTime = !showTime
                }
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
//                val startPadding = if (message.senderId == userId) 8.dp else 0.dp
//                val endPadding = if (message.senderId == userId) 0.dp else 8.dp
                Text(
                    text = message.createdAt,
                    style = MaterialTheme.typography.bodySmall,
                )
            }
            if (message.status == Message.Status.FAILED) {
                showTime = false
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
        modifier = modifier
            .padding(bottom = 8.dp),
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
        isLoading = false
    )
}