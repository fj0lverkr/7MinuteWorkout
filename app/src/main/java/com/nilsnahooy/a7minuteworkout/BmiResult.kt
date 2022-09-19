package com.nilsnahooy.a7minuteworkout

enum class BmiResult(val classification: String, val advice: String) {
    UNDERWEIGHT("underweight", ""),
    NORMAL("normal weight", ""),
    OVERWEIGHT("overweight", ""),
    OBESE_I("obese class I", ""),
    OBESE_II("obese class II", ""),
    OBESE_III("obese class III", ""),
    UNDETERMINED("invalid input", "Please correct your input.")
}