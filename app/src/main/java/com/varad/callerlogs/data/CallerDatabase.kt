package com.varad.callerlogs.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import kotlin.concurrent.Volatile

@Database(entities = [IncomingCall::class], version = 1, exportSchema = false)
abstract class CallerDatabase : RoomDatabase() {

    companion object {
        @Volatile
        private var INSTANCE: CallerDatabase? = null
        fun getDatabase(context: Context): CallerDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    CallerDatabase::class.java,
                    "caller_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }

    abstract fun callDao(): CallDao
}