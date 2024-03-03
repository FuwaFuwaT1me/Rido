package com.example.rido.di

import android.content.Context
import androidx.room.Room
import com.example.core_data.database.RidoDatabase
import com.example.core_data.database.dao.TitleFileDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
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
            ).build()
        }

        @Singleton
        @Provides
        fun provideTitleFileDao(database: RidoDatabase): TitleFileDao {
            return database.getTitleFileDao()
        }
    }
}
