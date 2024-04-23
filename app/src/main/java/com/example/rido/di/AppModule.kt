package com.example.rido.di

import android.content.Context
import androidx.room.Room
import com.example.core_data.database.RidoDatabase
import com.example.core_data.database.dao.ImageFileDao
import com.example.core_data.database.dao.PdfFileDao
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
interface AppModule {

    companion object {

        @Singleton
        @Provides
        fun provideDatabase(@ApplicationContext applicationContext: Context): RidoDatabase {
            return Room.databaseBuilder(
                applicationContext,
                RidoDatabase::class.java,
                "rido_database"
            )
                .fallbackToDestructiveMigration()
                .build()
        }

        @Singleton
        @Provides
        fun providePdfFileDao(database: RidoDatabase): PdfFileDao {
            return database.getPdfFileDao()
        }

        @Singleton
        @Provides
        fun provideImageFileDao(database: RidoDatabase): ImageFileDao {
            return database.getImageFileDao()
        }

        @Singleton
        @Provides
        fun provideApplicationContext(@ApplicationContext context: Context): Context {
            return context
        }

        @Singleton
        @Provides
        fun provideDefaultCoroutineScope(): CoroutineScope {
            return CoroutineScope(Dispatchers.Default)
        }
    }
}
