package com.nilsnahooy.a7minuteworkout

import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.speech.tts.TextToSpeech
import android.util.Log
import androidx.appcompat.content.res.AppCompatResources
import com.nilsnahooy.a7minuteworkout.databinding.ActivityExerciseBinding
import java.util.*

class ExerciseActivity : AppCompatActivity(), TextToSpeech.OnInitListener {

    private var b:ActivityExerciseBinding? = null
    private var activityTimer: CountDownTimer? = null
    private var activityProgress = -1
    private var tts:TextToSpeech? = null
    private var player: MediaPlayer? = null

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
        tts = TextToSpeech(this, this)
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

        val slogan = if(isRest){
            getString(R.string.label_rest_name, activityName)
        } else {
            getString(R.string.label_exercise_name, activityName)
        }

        b?.tvSlogan?.text = slogan
        b?.ivExerciseIllustration?.setImageDrawable(AppCompatResources.getDrawable(this,
            activityImage))

        speakOut(slogan)

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

    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS){
            val result = tts?.setLanguage(Locale.UK)
            if (result == TextToSpeech.LANG_MISSING_DATA ||
                result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Log.e("TTS", "Unsupported language.")
            }
        }else{
            Log.e("TTS", "Error initializing TTS.")
        }
        setupRestView()
    }

    private fun speakOut(text: String) {
        if(tts != null){
            tts?.speak(text, TextToSpeech.QUEUE_FLUSH, null, "")
        }
    }

    override fun onDestroy() {
        super.onDestroy()

        if(activityTimer != null) {
            activityTimer?.cancel()
            activityProgress = -1
        }

        if(tts != null){
            tts?.stop()
            tts?.shutdown()
        }

        b = null
    }
}