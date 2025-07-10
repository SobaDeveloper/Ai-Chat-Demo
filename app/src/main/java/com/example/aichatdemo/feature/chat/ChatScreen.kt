package com.example.aichatdemo.feature.chat

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.example.aichatdemo.R
import com.example.aichatdemo.feature.chat.components.AiModelDialog
import com.example.aichatdemo.feature.chat.components.SystemMessageView
import com.example.aichatdemo.feature.chat.components.UserInputField
import com.example.aichatdemo.feature.chat.components.UserMessageView
import com.example.aichatdemo.ui.components.DotsLoader
import com.example.aichatdemo.ui.theme.LocalSpacing
import com.example.core.utils.formatTimestamp
import com.example.data.service.gemini.dto.Role
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatScreen(
    viewModel: ChatViewModel = koinViewModel()
) {
    val inputText by viewModel.inputText.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val conversations by viewModel.conversations.collectAsState()
    val selectedSessionId by viewModel.selectedSessionId.collectAsState()
    val messages by viewModel.messages.collectAsState()

    var showModelDialog by remember { mutableStateOf(false) }
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val listState = rememberLazyListState()
    val scope = rememberCoroutineScope()
    val spacing = LocalSpacing.current

    LaunchedEffect(drawerState.isOpen) {
        if (drawerState.isOpen) {
            viewModel.getConversations()
        }
    }

    LaunchedEffect(selectedSessionId) {
        selectedSessionId?.let {
            snapshotFlow { messages.size }
                .filter { it > 0 }
                .first()
            scope.launch {
                listState.scrollToItem(messages.lastIndex)
            }
        }
    }

    if (showModelDialog) {
        AiModelDialog(
            viewModel = viewModel,
            onDismissRequest = { showModelDialog = false }
        )
    }

    ModalNavigationDrawer(
        drawerContent = {
            ModalDrawerSheet {
                Text(stringResource(R.string.chat_history), modifier = Modifier.padding(spacing.md))
                HorizontalDivider()
                Column {
                    conversations.forEach {
                        NavigationDrawerItem(
                            label = { Text(text = it.id.toLong().formatTimestamp()) },
                            selected = it.id == selectedSessionId,
                            onClick = {
                                viewModel.getChatMessages(it.id)
                                scope.launch { drawerState.close() }
                            }
                        )
                    }
                }
            }
        },
        drawerState = drawerState
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text(stringResource(R.string.app_name)) },
                    navigationIcon = {
                        IconButton(onClick = {
                            scope.launch { drawerState.open() }
                        }) {
                            Icon(
                                Icons.Default.Menu, contentDescription = stringResource(R.string.menu)
                            )
                        }
                    },
                    actions = {
                        IconButton(
                            onClick = { showModelDialog = true }
                        ) {
                            Icon(
                                imageVector = Icons.Filled.Settings,
                                contentDescription = stringResource(R.string.settings)
                            )
                        }
                    }
                )
            },
            bottomBar = {
                Surface(
                    color = MaterialTheme.colorScheme.background,
                    modifier = Modifier
                        .fillMaxWidth()
                        .imePadding()
                ) {
                    UserInputField(
                        value = inputText,
                        onValueChange = viewModel::onInputChange,
                        onSend = viewModel::getModelResponse
                    )
                }
            },
        )
        { contentPadding ->
            LaunchedEffect(messages.size) {
                if (messages.isNotEmpty()) {
                    scope.launch {
                        val targetIndex = if (isLoading) messages.size else messages.size - 1
                        listState.animateScrollToItem(targetIndex)
                    }
                }
            }
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(spacing.sm),
                contentPadding = contentPadding,
                modifier = Modifier.fillMaxSize(),
                state = listState
            ) {
                items(messages) { message ->
                    if (message.role == Role.MODEL) {
                        SystemMessageView(message.text)
                    } else {
                        UserMessageView(message.text)
                    }
                }
                if (isLoading && messages.isNotEmpty()) {
                    item {
                        DotsLoader(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = spacing.md, vertical = spacing.sm)
                        )
                    }
                }
            }
        }
    }
}

