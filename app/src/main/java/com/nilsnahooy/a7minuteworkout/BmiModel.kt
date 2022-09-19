package com.nilsnahooy.a7minuteworkout

class BmiModel(
    private val score: Float,
    private val classification: String,
    private val advice: String
) {
    fun getScore(): Float {
        return score
    }
    fun getClass(): String{
        return classification
    }
    fun getAdvice(): String{
        return advice
    }
}