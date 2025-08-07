
package com.example.messages.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil3.compose.AsyncImage
import com.chatappfrontend.common.UiEvent
import com.example.messages.R
import com.example.messages.viewmodel.ChatListViewModel


// should i use the user id for the selected chat to check if chat exists and then return that chat id? and
// if it doesn't exist, create a new chat with the user id and return that chat id? or should i include the chat id
// with the user when i first load this screen?
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatListScreen(
    modifier: Modifier = Modifier,
    viewModel: ChatListViewModel = hiltViewModel(),
    onNewMessageClick: () -> Unit,
    onChatClick: (String, String, String) -> Unit,
    onLogout: (String) -> Unit,
) {

    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.getChats()

        viewModel.uiEvent.collect { event ->
            when (event) {
                is UiEvent.Navigate -> {
                    onLogout(event.route)
                }

                is UiEvent.ShowSnackbar -> {}
            }
        }
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text(text = uiState.username) }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = onNewMessageClick
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "New Message"
                )
            }
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = modifier
                .fillMaxSize()
                .padding(paddingValues),
        ) {
            item {
                TextButton(
                    onClick = viewModel::logout,
                ) {
                    Text("Logout")
                }
            }
            items(uiState.chatInfos) { chat ->

                ChatItem(
                    modifier = Modifier
                        .clickable {
                            onChatClick(chat.id, chat.userId, chat.username)
                        },
                    profileImage = painterResource(R.drawable.round_account_circle_24),
                    contactName = chat.username,
                    lastMessage = chat.lastMessage,
                    date = chat.lastMessageTimeStamp
                )
            }
        }

    }
}

@Composable
fun ChatItem(
    profileImage: Painter,
    modifier: Modifier = Modifier,
    contactName: String,
    lastMessage: String,
    date: String
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(12.dp),
    ) {
        AsyncImage(
            model = "https://e7.pngegg.com/pngimages/81/570/png-clipart-profile-logo-computer-icons-user-user-blue-heroes-thumbnail.png",
            contentDescription = "Profile Image Thumbnail",
            placeholder = painterResource(R.drawable.round_account_circle_24),
            error = painterResource(R.drawable.round_account_circle_24),
            fallback = painterResource(R.drawable.round_account_circle_24),
            modifier = Modifier
                .size(48.dp)
                .clip(CircleShape)
        )

        Spacer(modifier = Modifier.width(12.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Column(
                modifier = Modifier
                    .weight(1f),
            ) {
                Text(
                    modifier = Modifier,
                    text = contactName,
                    style = MaterialTheme.typography.bodyLarge
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = lastMessage,
                    style = MaterialTheme.typography.bodyMedium,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            Text(
                modifier = Modifier
                    .align(Alignment.Top),
                text = date,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
        }

    }

}