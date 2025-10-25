package com.renaudfavier.messages.chat.di

import com.renaudfavier.messages.chat.data.FakeMessageRepository
import com.renaudfavier.messages.chat.domain.MessageRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface ChatModule {

    @Singleton
    @Binds
    fun bindMessageRepository(fake: FakeMessageRepository): MessageRepository
}
