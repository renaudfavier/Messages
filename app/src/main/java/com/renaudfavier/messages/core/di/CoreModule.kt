package com.renaudfavier.messages.core.di

import com.renaudfavier.messages.core.data.FakeContactRepository
import com.renaudfavier.messages.core.domain.ContactRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface CoreModule {

    @Singleton
    @Binds
    fun bindContactRepository(fake: FakeContactRepository): ContactRepository
}
