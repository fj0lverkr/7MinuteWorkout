package com.nilsnahooy.a7minuteworkout.workoutHistory

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface WorkoutDao {
    @Insert
    suspend fun insert(workoutEntity: WorkoutEntity)

    @Delete
    suspend fun delete(workoutEntity: WorkoutEntity)

    @Query("SELECT * FROM 'tblWorkouts'")
    fun selectAllWorkouts():Flow<List<WorkoutEntity>>

}
