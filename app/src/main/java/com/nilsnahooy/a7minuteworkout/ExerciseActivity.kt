package com.nilsnahooy.a7minuteworkout

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.widget.Toast
import com.nilsnahooy.a7minuteworkout.databinding.ActivityExerciseBinding

class ExerciseActivity : AppCompatActivity() {

    private var b:ActivityExerciseBinding? = null
    private var activityTimer: CountDownTimer? = null
    private var activityProgress = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        b = ActivityExerciseBinding.inflate(layoutInflater)
        setContentView(b?.root)

        setSupportActionBar(b?.tbExercise)
        if(supportActionBar != null) {
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }
        b?.tbExercise?.setNavigationOnClickListener {
            onBackPressed()
        }

        setupRestView()
    }

    private fun setupRestView() {
        if(activityTimer != null) {
            activityTimer?.cancel()
            activityProgress = -1
        }
        setProgressBar("demo", 10000, true)
    }


    private fun setProgressBar(activityName: String, timeMs: Long, isRest: Boolean) {
        b?.tvSlogan?.text = if(isRest){
            getString(R.string.label_rest_name, activityName)
        } else {
            getString(R.string.label_exercise_name, activityName)
        }
        var timeToExercise: Int = (timeMs/1000).toInt()
        b?.pbTimer?.max = timeToExercise
        b?.pbTimer?.progress = activityProgress
        activityTimer = object : CountDownTimer(timeMs, 1000){
            override fun onTick(p0: Long) {
                activityProgress ++
                b?.pbTimer?.progress = timeToExercise-activityProgress
                b?.tvTimer?.text = "${timeToExercise-activityProgress}"
            }

            override fun onFinish(){
                timeToExercise = (timeMs/1000).toInt()
                activityProgress ++
                b?.pbTimer?.progress = timeToExercise-activityProgress
                b?.tvTimer?.text = "${timeToExercise-activityProgress}"
                activityProgress = -1
                activityTimer = null
                if(isRest){
                    setProgressBar("demo", 30000, false)
                } else {
                    Toast.makeText(this@ExerciseActivity, "Done",
                        Toast.LENGTH_LONG).show()
                }
            }
        }.start()
    }

    override fun onDestroy() {
        super.onDestroy()
        if(activityTimer != null) {
            activityTimer?.cancel()
            activityProgress = -1
        }
        b = null
    }
}