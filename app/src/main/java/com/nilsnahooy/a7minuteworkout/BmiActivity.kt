package com.nilsnahooy.a7minuteworkout

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.core.view.children
import com.nilsnahooy.a7minuteworkout.databinding.ActivityBmiBinding

class BmiActivity : AppCompatActivity() {
    private var b : ActivityBmiBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        b = ActivityBmiBinding.inflate(layoutInflater)
        setContentView(b?.root)

        setSupportActionBar(b?.tbBmi)
        if(supportActionBar != null) {
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }

        b?.tbBmi?.setNavigationOnClickListener {
            finish()
        }

        resetTextViewsVisibility(false)

        b?.btnCalculate?.setOnClickListener {
            val bmiResult = calculateBmi(true)
            b?.tvResultNumber?.text = bmiResult.getScore().toString()
            b?.tvResultClassification?.text = bmiResult.getClass()
            b?.tvResultAdvice?.text = bmiResult.getAdvice()
            resetTextViewsVisibility(true)
        }
    }

    private fun resetTextViewsVisibility(visible: Boolean){
        for(c in b?.llTvWrapper?.children!!){
            c.visibility = if(visible){
                View.VISIBLE
            }else{
                View.INVISIBLE
            }
        }
    }

    private fun calculateBmi(isMetric:Boolean):BmiModel{
        val weight: Float = b?.tiWeight?.text.toString().toFloat()
        val height: Float = b?.tiHeight?.text.toString().toFloat()/100
        val score: Float =
            if (isMetric){
                weight/(height*height)
            }else{
                (weight/(height*height))*703
            }
        return when(score){
            in 0F..18.5F -> BmiModel(score, "underweight",
                "Eat more or something")
            else -> BmiModel(score, "invalid score", "Invalid score")
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        b = null
    }
}