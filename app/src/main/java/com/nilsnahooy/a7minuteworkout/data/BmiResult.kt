package com.nilsnahooy.a7minuteworkout.data

import com.nilsnahooy.a7minuteworkout.R
import com.nilsnahooy.a7minuteworkout.utils.AppForContext

enum class BmiResult(val classification: String, val advice: String) {
    UNDERWEIGHT("underweight", AppForContext.res.getString(R.string.adv_underweight)),
    NORMAL("normal weight", AppForContext.res.getString(R.string.adv_normal)),
    OVERWEIGHT("overweight", AppForContext.res.getString(R.string.adv_overweight)),
    OBESE_I("obese class I", AppForContext.res.getString(R.string.adv_obese_1)),
    OBESE_II("obese class II", AppForContext.res.getString(R.string.adv_obese_2)),
    OBESE_III("obese class III", AppForContext.res.getString(R.string.adv_obese_3)),
    UNDETERMINED("invalid input", AppForContext.res.getString(R.string.adv_undetermined))
}