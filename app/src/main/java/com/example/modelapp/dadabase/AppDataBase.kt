package com.example.modelapp.dadabase

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.modelapp.bean.Schedule
import com.example.mvvm.config.Config.getApplicationContext
import androidx.room.Room
import com.example.modelapp.dao.ScheduleDao


@Database(entities = [Schedule::class], version = 1)
abstract class AppDataBase : RoomDatabase() {

    companion object {
        private var appDataBase: AppDataBase? = null

        fun getInstance(): AppDataBase {
            appDataBase?.run {
                return this
            }
            synchronized(this) {
                appDataBase?.run {
                    return this
                }
                appDataBase = Room.databaseBuilder(getApplicationContext(),
                        AppDataBase::class.java, "database-name").build()
            }
            return appDataBase!!
        }
    }

    abstract fun scheduleDao(): ScheduleDao

}