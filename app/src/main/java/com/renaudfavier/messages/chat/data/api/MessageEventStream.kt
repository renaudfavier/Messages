package com.renaudfavier.messages.chat.data.api

import com.renaudfavier.messages.chat.data.api.mapper.ApiMapper
import com.renaudfavier.messages.chat.data.api.model.MessageDto
import com.renaudfavier.messages.chat.domain.Message
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.serialization.json.Json
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.sse.EventSource
import okhttp3.sse.EventSourceListener
import okhttp3.sse.EventSources
import javax.inject.Inject

class MessageEventStream @Inject constructor(
    private val okHttpClient: OkHttpClient,
    private val json: Json,
    private val apiMapper: ApiMapper,
    private val baseUrl: String
) {

    fun observeMessages(seenMessageIds: MutableSet<String>): Flow<Message> = callbackFlow {
        val request = Request.Builder()
            .url("$baseUrl/events?stream=messages")
            .build()

        val eventSourceListener = object : EventSourceListener() {
            override fun onOpen(eventSource: EventSource, response: Response) {
                // Connection opened successfully
            }

            override fun onEvent(
                eventSource: EventSource,
                id: String?,
                type: String?,
                data: String
            ) {
                try {
                    val messageDto = json.decodeFromString<MessageDto>(data)

                    // Skip if already seen
                    if (messageDto.id in seenMessageIds) return
                    else seenMessageIds.add(messageDto.id)

                    val message = apiMapper.mapMessage(messageDto)
                    trySend(message)
                } catch (e: Exception) {
                    // Ignore parsing errors for individual messages
                }
            }

            override fun onFailure(eventSource: EventSource, t: Throwable?, response: Response?) {
                // Connection failed - will be retried
                close(t)
            }

            override fun onClosed(eventSource: EventSource) {
                close()
            }
        }

        val eventSource = EventSources.createFactory(okHttpClient)
            .newEventSource(request, eventSourceListener)

        awaitClose {
            eventSource.cancel()
        }
    }
}
