package com.example.modelapp.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.modelapp.bean.Schedule

@Dao
interface ScheduleDao {

    @Insert
    fun insert(schedule: Schedule)

    @Delete
    fun delete(schedule: Schedule)

    @Query("SELECT * FROM schedule")
    fun queryAll();
}