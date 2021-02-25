package com.macoev.aadstudyproject.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.macoev.aadstudyproject.BuildConfig
import com.macoev.aadstudyproject.data.dao.BaseDao
import com.macoev.aadstudyproject.data.dao.UserDao
import com.macoev.aadstudyproject.data.entity.User
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

@Database(entities = [User::class], version = 6)
internal abstract class AppDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao

    companion object {
        private var INSTANCE: AppDatabase? = null
        private var lock = Any()
        private val scope = CoroutineScope(Job() + Dispatchers.IO)

         fun getDatabase(context: Context): AppDatabase {
            if (INSTANCE != null) return INSTANCE!!
            synchronized(lock) {
                if (INSTANCE == null) {
                    INSTANCE =
                        Room.databaseBuilder(context, AppDatabase::class.java, "database-user")
                            .addCallback(roomCallback)
                            .fallbackToDestructiveMigration()
                            .build()
                }
            }
            return INSTANCE!!
        }

        private val roomCallback: Callback = object : Callback() {
            override fun onDestructiveMigration(db: SupportSQLiteDatabase) {
                scope.launch {
                    INSTANCE?.userDao()?.deleteAll()
                }
            }

            override fun onOpen(db: SupportSQLiteDatabase) {
                scope.launch {
                    INSTANCE?.userDao()?.run {
                        if (BuildConfig.DEBUG && isEmpty() == true) {
                            for (i in 0..10) {
                                insertOrUpdate(User.createRandom())
                            }
                        }
                    }
                }
            }
        }
    }
}