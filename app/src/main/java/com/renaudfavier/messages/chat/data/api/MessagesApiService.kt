package com.renaudfavier.messages.chat.data.api

import com.renaudfavier.messages.chat.data.api.model.ChatDto
import com.renaudfavier.messages.chat.data.api.model.MessageDto
import com.renaudfavier.messages.chat.data.api.model.SendMessageRequest
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path

interface MessagesApiService {

    @GET("/chats")
    suspend fun getChats(): List<ChatDto>

    @GET("/chats/{chat_id}/messages")
    suspend fun getMessages(
        @Path("chat_id") chatId: String
    ): List<MessageDto>

    @POST("/chats/{chat_id}/messages")
    suspend fun sendMessage(
        @Path("chat_id") chatId: String,
        @Header("Idempotency-Key") idempotencyKey: String,
        @Body request: SendMessageRequest
    ): MessageDto
}
