package com.nilsnahooy.a7minuteworkout

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.widget.Toast
import com.nilsnahooy.a7minuteworkout.databinding.ActivityExerciseBinding

class ExerciseActivity : AppCompatActivity() {

    private val timeToRest = 10000L
    private val timeToRestSeconds = timeToRest/1000
    private var b:ActivityExerciseBinding? = null
    private var restTimer: CountDownTimer? = null
    private var restProgress = -1

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
        if(restTimer != null) {
            restTimer?.cancel()
            restProgress = -1
        }
        setRestProgressBar()
    }

    private fun setRestProgressBar() {
        b?.pbTimer?.progress = restProgress
        restTimer = object : CountDownTimer(timeToRest, 1000){
            override fun onTick(p0: Long) {
                restProgress ++
                b?.pbTimer?.progress = timeToRestSeconds.toInt()-restProgress
                b?.tvTimer?.text = "${timeToRestSeconds-restProgress}"
            }

            override fun onFinish() {
                restProgress ++
                b?.pbTimer?.progress = timeToRestSeconds.toInt()-restProgress
                b?.tvTimer?.text = "${timeToRestSeconds-restProgress}"
                Toast.makeText(this@ExerciseActivity, "Now we start the exercise!",
                    Toast.LENGTH_LONG).show()
                restProgress = -1
                restTimer = null
            }
        }.start()
    }

    override fun onDestroy() {
        super.onDestroy()
        if(restTimer != null) {
            restTimer?.cancel()
            restProgress = -1
        }
        b = null
    }
}