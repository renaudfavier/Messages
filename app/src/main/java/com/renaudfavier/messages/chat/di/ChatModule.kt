package com.renaudfavier.messages.chat.di

import com.renaudfavier.messages.chat.data.api.MessageEventStream
import com.renaudfavier.messages.chat.data.api.ServerMessageRepository
import com.renaudfavier.messages.chat.data.api.mapper.ApiMapper
import com.renaudfavier.messages.chat.domain.MessageRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.OkHttpClient
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface ChatModule {

    @Singleton
    @Binds
    fun bindMessageRepository(server: ServerMessageRepository): MessageRepository

    companion object {
        @Provides
        @Singleton
        fun provideMessageEventStream(
            okHttpClient: OkHttpClient,
            json: Json,
            apiMapper: ApiMapper,
            @Named("BaseUrl") baseUrl: String
        ): MessageEventStream {
            return MessageEventStream(okHttpClient, json, apiMapper, baseUrl)
        }
    }
}
