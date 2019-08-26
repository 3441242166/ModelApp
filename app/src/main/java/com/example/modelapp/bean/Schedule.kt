package com.example.modelapp.bean

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class Schedule(@PrimaryKey val id: Int,
               uid: Int, // 所属用户
               calendarId: Int, // 所属日历
               title: String,
               startTime: String,
               endTime: String,
               participants: List<Int> = listOf(), // 参与者 暂不使用
               color: String, // 日程标示颜色
               visibility: String, // 公开范围
               state: Int, // 状态 开始前 进行中 已结束
               isBusy: Boolean,
               remindTime: String,
               location: Location,
               repeatFrequency: String,
               describe: String)