package com.chatappfrontend.data.websocket

import android.util.Log
import com.chatappfrontend.common.ResultWrapper
import com.chatappfrontend.data.BuildConfig
import com.chatappfrontend.data.MessagePayloadDTO
import com.chatappfrontend.data.mapper.toMessage
import com.chatappfrontend.domain.model.Message
import com.chatappfrontend.network.model.MessageDTO
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.WebSocket
import okhttp3.WebSocketListener
import javax.inject.Inject

class WebSocketManager @Inject constructor(
) {

    private val client = OkHttpClient()
    private lateinit var webSocket: WebSocket

    private val _incomingMessages = MutableSharedFlow<Message>()
    val incomingMessages: SharedFlow<Message> = _incomingMessages.asSharedFlow()

    private val baseUrl = if (BuildConfig.USE_REMOTE_WEB_SOCKET_URL) {
        BuildConfig.REMOTE_WEB_SOCKET_URL
    } else {
        BuildConfig.LOCAL_WEB_SOCKET_URL
    }

    @OptIn(DelicateCoroutinesApi::class)
    fun connect(userId: String) {
        Log.d("WebSocketManager", "Connecting to WebSocket at $baseUrl with userId: $userId")
        val request = Request.Builder()
            .url(baseUrl)
            .build()

        webSocket = client.newWebSocket(request, object : WebSocketListener() {
            override fun onOpen(webSocket: WebSocket, response: okhttp3.Response) {
                Log.d("WebSocketManager", "WebSocket opened: ${response.message}")
                val payload = Json.encodeToString(
                    RegisterWSConnectionDTO(userId = userId, type = "register")
                )
                webSocket.send(text = payload)
                Log.d("WebSocketManager", "Sent registration payload: $payload")
            }

            override fun onMessage(webSocket: WebSocket, text: String) {
                Log.d("WebSocketManager", "Received message: $text")
                val messageDTO = Json.decodeFromString<MessageDTO>(text)

                GlobalScope.launch {
                    _incomingMessages.emit(messageDTO.toMessage())
                }
                Log.d("WebSocketManager", "Message emitted: ${messageDTO.toMessage()}")
            }

            override fun onFailure(
                webSocket: WebSocket,
                t: Throwable,
                response: okhttp3.Response?
            ) {
                Log.d("WebSocketManager", "WebSocket failure: ${t.message}")
            }

            override fun onClosed(webSocket: WebSocket, code: Int, reason: String) {
                Log.d("WebSocketManager", "WebSocket closed: $code / $reason")
            }
        })
    }

    fun disconnect() {
        webSocket.close(1000, "Client closed the connection")
        Log.d("WebSocketManager", "WebSocket disconnected")
    }

    fun sendMessage(messagePayloadDTO: MessagePayloadDTO): ResultWrapper<Message.Status> {
        Log.d("WebSocketManager", "Sending message: $messagePayloadDTO")
        val payload = Json.encodeToString(messagePayloadDTO)
        val wasSent = webSocket.send(text = payload)
        if (wasSent) {
            Log.d("WebSocketManager", "Message sent successfully")
            return ResultWrapper.Success(Message.Status.FAILED)
        } else {
            Log.e("WebSocketManager", "Failed to send message")
            return ResultWrapper.Success(Message.Status.FAILED)
        }
    }
}