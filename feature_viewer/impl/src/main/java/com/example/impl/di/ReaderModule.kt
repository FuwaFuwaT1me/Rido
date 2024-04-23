package com.example.impl.di

import com.example.impl.mvi.ReaderState
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
interface ReaderModule {

    companion object {

        @Singleton
        @Provides
        fun provideDefaultState(): ReaderState {
            return ReaderState(
                isLoading = false
            )
        }
    }
}
