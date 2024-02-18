package com.example.feature_manga_library.di

import com.example.feature_manga_library.mvi.MyLibraryState
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers

@MyLibraryScope
@InstallIn(SingletonComponent::class)
@Module
interface MyLibraryModule {

    companion object {

        @MyLibraryScope
        @Provides
        fun provideDefaultState(): MyLibraryState {
            return MyLibraryState(
                libraryItems = listOf()
            )
        }

        @MyLibraryScope
        @Provides
        fun provideDefaultCoroutineScope(): CoroutineScope {
            return CoroutineScope(Dispatchers.Default)
        }
    }
}
