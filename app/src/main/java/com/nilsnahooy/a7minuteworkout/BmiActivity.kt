package com.nilsnahooy.a7minuteworkout

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.view.children
import com.nilsnahooy.a7minuteworkout.databinding.ActivityBmiBinding
import java.math.BigDecimal
import java.math.RoundingMode

class BmiActivity : AppCompatActivity() {
    private var b : ActivityBmiBinding? = null
    private var isMetric = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        b = ActivityBmiBinding.inflate(layoutInflater)
        setContentView(b?.root)

        setSupportActionBar(b?.tbBmi)
        if(supportActionBar != null) {
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }

        b?.tbBmi?.setNavigationOnClickListener {
            finish()
        }

        b?.swMetricImperial?.isChecked = isMetric
        setMetricOrImperial()

        b?.swMetricImperial?.setOnClickListener {
           setMetricOrImperial()
        }

        resetTextViewsVisibility(false)

        b?.btnCalculate?.setOnClickListener {
            val bmiResult = calculateBmi()
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

    private fun setMetricOrImperial(){
        isMetric = b?.swMetricImperial?.isChecked == true
        b?.tvLblMetricImperial?.text =
            if(isMetric){
                getString(R.string.label_metric)
            }else{
                getString(R.string.label_imperial)
            }
        b?.tilInputHeight?.hint =
            if(isMetric){
                getString(R.string.hint_height_in_cm)
            }else{
                getString(R.string.hint_height_in_in)
            }
        b?.tilInputWeight?.hint =
            if (isMetric){
                getString(R.string.hint_weight_in_kg)
            }else{
                getString(R.string.hint_weight_in_lb)
            }
        b?.tilInputWeight?.startIconDrawable =
            if (isMetric){
                AppCompatResources.getDrawable(this, R.drawable.ic_weight_kilogram)
            }else{
                AppCompatResources.getDrawable(this, R.drawable.ic_weight_pound)
            }
    }

    private fun calculateBmi():BmiModel{
        val wString = b?.tiWeight?.text.toString()
        val hString = b?.tiHeight?.text.toString()
        if (wString.isNotEmpty() && hString.isNotEmpty()) {
            val weight: Float = wString.toFloat()
            val height: Float =
                if (isMetric) {
                    hString.toFloat() / 100 //cm to meter
                } else {
                    hString.toFloat() //plain old inches
                }
            var score: Float =
                if (isMetric) {
                    weight / (height * height)
                } else {
                    (weight / (height * height)) * 703
                }
            score = BigDecimal(score.toDouble()).setScale(2, RoundingMode.HALF_EVEN).toFloat()
            return when (score) {
                in 0F..18.49F -> BmiModel(score, BmiResult.UNDERWEIGHT)
                in 18.5F..24.9F -> BmiModel(score, BmiResult.NORMAL)
                in 25F..29.99F -> BmiModel(score, BmiResult.OVERWEIGHT)
                in 30F..34.99F -> BmiModel(score, BmiResult.OBESE_I)
                in 35F..39.99F -> BmiModel(score, BmiResult.OBESE_II)
                in 40F..9999F -> BmiModel(score, BmiResult.OBESE_III)
                else -> BmiModel(score, BmiResult.UNDETERMINED)
            }
        } else {
            return BmiModel(-1F, BmiResult.UNDETERMINED)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        b = null
    }
}