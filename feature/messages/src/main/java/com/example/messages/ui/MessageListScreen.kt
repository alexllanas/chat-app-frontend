package com.example.messages.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import com.example.messages.viewmodel.MessageListViewModel

@Composable
fun MessageListScreen(
    modifier: Modifier = Modifier,
    viewModel: MessageListViewModel = hiltViewModel(),
    onLogout: (String) -> Unit,
) {

    val list = listOf(
        "alex",
        "james",
        "sarah",
        "john",
        "stephanie",
        "michael",
        "linda",
        "david",
        "emma",
        "oliver",
        "alex",
        "mags",
        "olive",
        "james",
        "sarah",
        "john",
        "stephanie",
        "michael",
        "linda",
        "david",
        "emma",
        "oliver",
        "alex",
        "mags",
        "olive",
        "james",
        "sarah",
        "john",
        "stephanie",
        "michael",
        "linda",
        "david",
        "emma",
        "oliver",
        "alex",
        "mags",
        "olive",
        "james",
        "sarah",
        "john",
        "stephanie",
        "michael",
        "linda",
        "david",
        "emma",
        "oliver",
        "alex",
        "mags",
        "olive",
        "james",
        "sarah",
        "john",
        "stephanie",
        "michael",
        "linda",
        "david",
        "emma",
        "oliver",
        "alex",
        "mags",
        "olive",
        "james",
        "sarah",
        "john",
        "stephanie",
        "michael",
        "linda",
        "david",
        "emma",
        "oliver"
    )

    LaunchedEffect(Unit) {
        viewModel.uiEvent.collect { event ->
            when (event) {
                is UiEvent.Navigate -> {
                    onLogout(event.route)
                }

                is UiEvent.ShowSnackbar -> {}
            }
        }
    }
    LazyColumn(
        modifier = modifier
            .fillMaxSize(),
        contentPadding = PaddingValues(16.dp)
    ) {
        item {
            TextButton(
                onClick = viewModel::logout,
            ) {
                Text("Logout")
            }
        }
        items(list) { item ->

            ConversationItem(
                profileImage = painterResource(R.drawable.round_account_circle_24),
                contactName = item,
                lastMessage = "Last message from $item Last message from $item",
                date = "Today"
            )
        }
    }
}

@Composable
fun ConversationItem(
    profileImage: Painter,
    modifier: Modifier = Modifier,
    contactName: String,
    lastMessage: String,
    date: String,
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