package com.nilsnahooy.a7minuteworkout

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import androidx.appcompat.content.res.AppCompatResources
import com.nilsnahooy.a7minuteworkout.databinding.ActivityExerciseBinding

class ExerciseActivity : AppCompatActivity() {

    private var b:ActivityExerciseBinding? = null
    private var activityTimer: CountDownTimer? = null
    private var activityProgress = -1

    private val exerciseList = Constants.defaultExerciseList()

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
        startExercise(0, true)
    }


    private fun startExercise(activityIndex: Int, isRest: Boolean) {
        val activity = exerciseList[activityIndex]
        val activityName = activity.getName()
        val activityImage = activity.getImageRes()
        val timeMs = if (isRest) {
            10000
        }else {
            activity.getDuration()
        }

        b?.tvSlogan?.text = if(isRest){
            getString(R.string.label_rest_name, activityName)
        } else {
            getString(R.string.label_exercise_name, activityName)
        }
        b?.ivExerciseIllustration?.setImageDrawable(AppCompatResources.getDrawable(this,
            activityImage))
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

                if (isRest) {
                    startExercise(activityIndex, false)
                } else {
                    if(activityIndex < exerciseList.size-1) {
                        startExercise(activityIndex + 1, true)
                    } else {
                        b?.ivExerciseIllustration?.setImageDrawable(AppCompatResources.getDrawable(
                            this@ExerciseActivity, R.drawable.ic_done))
                        b?.tvSlogan?.text = getString(R.string.ex_done)
                        b?.tvTimer?.text = ""
                    }
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