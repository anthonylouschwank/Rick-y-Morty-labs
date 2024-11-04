package com.example.lab10.di

import android.content.Context
import com.example.lab10.data.local.AppDatabase
import com.example.lab10.data.local.AppDatabaseFactory

object AppDependencies {
    private var database: AppDatabase? = null

    fun provideDatabase(context: Context): AppDatabase {
        return database ?: synchronized(this) {
            database ?: AppDatabaseFactory.create(context).also { database = it }
        }
    }
}