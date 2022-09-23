package com.nilsnahooy.a7minuteworkout.data

import com.nilsnahooy.a7minuteworkout.R
import com.nilsnahooy.a7minuteworkout.WorkoutApp

enum class BmiResult(val classification: String, val advice: String) {
    UNDERWEIGHT("underweight", WorkoutApp.res.getString(R.string.adv_underweight)),
    NORMAL("normal weight", WorkoutApp.res.getString(R.string.adv_normal)),
    OVERWEIGHT("overweight", WorkoutApp.res.getString(R.string.adv_overweight)),
    OBESE_I("obese class I", WorkoutApp.res.getString(R.string.adv_obese_1)),
    OBESE_II("obese class II", WorkoutApp.res.getString(R.string.adv_obese_2)),
    OBESE_III("obese class III", WorkoutApp.res.getString(R.string.adv_obese_3)),
    UNDETERMINED("invalid input", WorkoutApp.res.getString(R.string.adv_undetermined))
}