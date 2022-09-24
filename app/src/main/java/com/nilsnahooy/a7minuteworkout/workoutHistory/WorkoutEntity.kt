package com.nilsnahooy.a7minuteworkout.workoutHistory

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tblWorkouts")
data class WorkoutEntity(
    @PrimaryKey(autoGenerate = true)
    val id:Int = 0,
    var skipped: Int = 0,
    var startDateTime: String = "",
    var endDateTime: String = "",
    var isCompleted: Boolean = false
)
