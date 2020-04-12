package com.rockthevote.grommet.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

/**
 * Created by Mechanical Man on 3/24/20.
 */

@Database(entities = [Registration::class], version = 1)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    //TODO abstract fun registrationDao(): RegistrationDao

    companion object {
        @Volatile
        private var instance: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return instance
                    ?: synchronized(this) {
                instance ?: Room.databaseBuilder(
                        context,
                        AppDatabase::class.java,
                        "grommet_db"
                ).build()
            }
        }
    }
}
