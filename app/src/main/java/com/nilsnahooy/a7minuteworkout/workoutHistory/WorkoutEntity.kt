package com.nilsnahooy.a7minuteworkout.workoutHistory

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.nilsnahooy.a7minuteworkout.utils.Converters
import java.util.*

@Entity(tableName = "tblWorkouts")
@TypeConverters(Converters::class)
data class WorkoutEntity(
    @PrimaryKey(autoGenerate = true)
    val id:Int = 0,
    val startDateTime: Date? = null,
    val endDateTime: Date? = null,
    val isCompleted: Boolean = false
)
