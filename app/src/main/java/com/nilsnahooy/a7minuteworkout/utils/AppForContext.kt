package com.nilsnahooy.a7minuteworkout.utils

import android.app.Application
import android.content.res.Resources

class AppForContext : Application() {

   companion object{
       lateinit var res: Resources private set
   }

    override fun onCreate() {
        super.onCreate()
        res = resources
    }

}