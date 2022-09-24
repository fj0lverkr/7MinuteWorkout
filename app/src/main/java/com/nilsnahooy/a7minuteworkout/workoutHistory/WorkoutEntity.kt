package com.nilsnahooy.a7minuteworkout.workoutHistory

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tblWorkouts")
data class WorkoutEntity(
    @PrimaryKey(autoGenerate = true)
    val id:Int = 0,
    val skipped: Int = 0,
    val startDateTime: String = "",
    val endDateTime: String = "",
    val isCompleted: Boolean = false
)
