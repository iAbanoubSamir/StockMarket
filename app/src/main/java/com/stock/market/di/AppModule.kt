package com.stock.market.di

import android.app.Application
import androidx.room.Room
import com.stock.market.data.local.StockDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideStockDatabase(application: Application): StockDatabase {
        return Room.databaseBuilder(
            application,
            StockDatabase::class.java,
            "StockDatabase.db"
        ).build()
    }
}