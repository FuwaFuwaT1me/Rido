package com.example.feature_manga_library.di

import com.example.feature_manga_library.mvi.MangaLibraryState
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers

@InstallIn(SingletonComponent::class)
@Module
interface MyLibraryModule {

    companion object {

        @Provides
        fun provideDefaultState(): MangaLibraryState {
            return MangaLibraryState("")
        }

        @Provides
        fun provideDefaultCoroutineScope(): CoroutineScope {
            return CoroutineScope(Dispatchers.Default)
        }
    }
}
