package com.example.feature_my_library.di

import android.content.Context
import com.example.feature_my_library.mvi.MyLibraryState
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
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

        @Singleton
        @Provides
        fun provideDefaultCoroutineScope(): CoroutineScope {
            return CoroutineScope(Dispatchers.Default)
        }

        @Singleton
        @Provides
        fun provideApplicationContext(@ApplicationContext context: Context): Context {
            return context
        }
    }
}
