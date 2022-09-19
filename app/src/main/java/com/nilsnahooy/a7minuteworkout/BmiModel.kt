package com.nilsnahooy.a7minuteworkout

class BmiModel(
    private val score: Float,
    private val result: BmiResult
) {
    fun getScore(): Float {
        return score
    }
    fun getClass(): String{
        return result.classification
    }
    fun getAdvice(): String{
        return result.advice
    }
}