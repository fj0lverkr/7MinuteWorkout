package com.nilsnahooy.a7minuteworkout

import android.app.Application
import android.content.res.Resources
import com.nilsnahooy.a7minuteworkout.workoutHistory.WorkoutDatabase

class WorkoutApp: Application() {
    val db by lazy {
        WorkoutDatabase.getInstance(this)
    }

    companion object{
        lateinit var res: Resources private set
    }

    override fun onCreate() {
        super.onCreate()
        res = resources
    }
}