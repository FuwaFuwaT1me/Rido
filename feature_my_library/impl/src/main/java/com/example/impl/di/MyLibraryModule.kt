package com.example.impl.di

import com.example.impl.mvi.MyLibraryState
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
interface MyLibraryModule {

    companion object {

        @Singleton
        @Provides
        fun provideDefaultState(): MyLibraryState {
            return MyLibraryState(
                libraryItems = listOf(),
                showFilePicker = false
            )
        }
    }
}
