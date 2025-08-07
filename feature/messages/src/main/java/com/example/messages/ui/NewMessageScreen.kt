package com.example.messages.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.messages.viewmodel.NewMessageViewModel

// load users with chatid?
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewMessageScreen(
    modifier: Modifier = Modifier,
    viewModel: NewMessageViewModel = hiltViewModel(),
    navigateToChat: (String?, String?, String?) -> Unit,
    onBackPressed: () -> Unit
) {
     LaunchedEffect(Unit) {
        viewModel.getUsers()
     }

    val uiState by viewModel.uiState.collectAsState()

    var searchQuery by remember { mutableStateOf("") }
    val userList = uiState.users.filter {
        it.username.contains(searchQuery) ||
        it.email.contains(searchQuery)
    }


    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text("New Message")
                },
                navigationIcon = {
                    IconButton(onClick = onBackPressed) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back Pressed")
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = modifier
                .padding(paddingValues)
                .fillMaxSize()
        ) {
            OutlinedTextField(
                modifier = modifier.fillMaxWidth(),
                value = searchQuery,
                onValueChange = { searchQuery = it },
                placeholder = { Text("Type username or email") },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "Search Icon"
                    )
                }
            )
            LazyColumn(
                modifier.fillMaxWidth()
            ) {
                items(userList) { user ->
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                            .clickable {
                                navigateToChat(user.chatId, user.id, user.username)
                            },
                        contentAlignment = Alignment.CenterStart
                    ) {
                        Text(
                            text = user.username,
                            style = MaterialTheme.typography.bodyLarge
                        )
                    }
                }
            }
        }

    }
}