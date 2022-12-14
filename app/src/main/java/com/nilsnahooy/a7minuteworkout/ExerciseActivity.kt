package com.nilsnahooy.a7minuteworkout

import android.app.Dialog
import android.content.Intent
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.speech.tts.TextToSpeech
import android.util.Log
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.content.res.AppCompatResources
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.nilsnahooy.a7minuteworkout.data.Constants
import com.nilsnahooy.a7minuteworkout.databinding.ActivityExerciseBinding
import com.nilsnahooy.a7minuteworkout.databinding.DialogCustomBackConfirmationBinding
import com.nilsnahooy.a7minuteworkout.utils.ExerciseStatusAdapter
import com.nilsnahooy.a7minuteworkout.workoutHistory.WorkoutDao
import com.nilsnahooy.a7minuteworkout.workoutHistory.WorkoutEntity
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

class ExerciseActivity : AppCompatActivity(), TextToSpeech.OnInitListener {

    private var b:ActivityExerciseBinding? = null
    private var activityTimer: CountDownTimer? = null
    private var activityProgress = -1
    private var tts:TextToSpeech? = null
    private var startStopPlayer: MediaPlayer? = null
    private var tickPlayer: MediaPlayer? = null
    private var exerciseAdapter: ExerciseStatusAdapter? = null
    private var currentExercise: Int = 0

    private lateinit var workout: WorkoutEntity
    private lateinit var historyDao: WorkoutDao

    private val exerciseList = Constants.defaultExerciseList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        b = ActivityExerciseBinding.inflate(layoutInflater)
        historyDao = (application as WorkoutApp).db.workoutDao()
        setContentView(b?.root)

        setSupportActionBar(b?.tbExercise)
        if(supportActionBar != null) {
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }

        onBackPressedDispatcher.addCallback(this,
            object: OnBackPressedCallback(true){
                override fun handleOnBackPressed() {
                    confirmBackNavigation()
                }
        })
        b?.tbExercise?.setNavigationOnClickListener {
            confirmBackNavigation()
        }

        b?.ivSkipButton?.setOnClickListener {
            skipExercise(currentExercise)
        }

        val start = LocalDateTime.now().format(DateTimeFormatter
            .ofPattern("dd-MM-yyyy HH:mm"))
        workout = WorkoutEntity(startDateTime = start)

        //Setup MediaPlayers
        startStopPlayer = MediaPlayer.create(applicationContext, R.raw.ex_start)
        tickPlayer = MediaPlayer.create(applicationContext, R.raw.tick)
        startStopPlayer?.isLooping = false
        tickPlayer?.isLooping = false

        //Start TTS engine, this will also start the exercises
        tts = TextToSpeech(this, this)

        //Init  RecyclerView for tracking exercises
        setupExerciseStatus()
    }

    private fun setupExerciseStatus() {
        b?.rvProgress?.layoutManager = LinearLayoutManager(this,
            LinearLayoutManager.HORIZONTAL, false)
        exerciseAdapter = ExerciseStatusAdapter(exerciseList)
        b?.rvProgress?.adapter = exerciseAdapter
    }

    private fun setupRestView() {

        if(activityTimer != null) {
            activityTimer?.cancel()
            activityProgress = -1
        }
        startExercise(currentExercise, true)
    }

    private fun startExercise(activityIndex: Int, isRest: Boolean) {
        val activity = exerciseList[activityIndex]
        val activityName = activity.getName()
        val activityImage = activity.getImageRes()
        activityProgress = -1

        activity.setIsSelected(true)
        b?.rvProgress?.adapter?.notifyItemChanged(activityIndex)

        val slogan = if(isRest){
            getString(R.string.label_rest_name, activityName)
        } else {
            getString(R.string.label_exercise_name, activityName)
        }

        val timeMs = if (isRest) {
            10000
        }else {
            activity.getDuration()
        }

        b?.ivSkipButton?.visibility = if(isRest){
            View.GONE
        } else {
            View.VISIBLE
        }

        b?.tvSlogan?.text = slogan
        b?.ivExerciseIllustration?.setImageDrawable(AppCompatResources.getDrawable(this,
            activityImage))

        if(isRest){
            speakOut(slogan)
        }else{
            startStopPlayer?.start()
        }

        var timeToExercise: Int = (timeMs/1000).toInt()
        b?.pbTimer?.max = timeToExercise
        b?.pbTimer?.progress = activityProgress
        activityTimer = object : CountDownTimer(timeMs, 1000){
            override fun onTick(p0: Long) {
                activityProgress ++
                b?.pbTimer?.progress = timeToExercise-activityProgress
                b?.tvTimer?.text = "${timeToExercise-activityProgress}"
                if(!isRest) {
                    if (timeToExercise - activityProgress == timeToExercise / 2) {
                        speakOut("keep going!")
                    } else {
                        tickPlayer?.start()
                    }
                }
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
                    currentExercise = activityIndex + 1
                    if (startStopPlayer?.isPlaying == true) startStopPlayer?.stop()
                    startStopPlayer?.start()
                    if(activityIndex < exerciseList.size-1) {
                        startExercise(currentExercise, true)
                    } else {
                        workout.isCompleted = true
                        storeWorkoutToHistory()
                        speakOut(getString(R.string.tts_finished_workout))
                        val intent = Intent(this@ExerciseActivity,
                           FinishedExercisesActivity::class.java)
                        startActivity(intent)
                    }
                    activity.setIsSelected(false)
                    activity.setIsCompleted(true)
                    b?.rvProgress?.adapter?.notifyItemChanged(activityIndex)
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

    private fun confirmBackNavigation(){
        val confirmDialog = Dialog(this)
        val dB = DialogCustomBackConfirmationBinding.inflate(layoutInflater)
        confirmDialog.setContentView(dB.root)
        confirmDialog.setCanceledOnTouchOutside(false)
        dB.btnConfirmBackNo.setOnClickListener {
            confirmDialog.dismiss()
        }
        dB.btnConfirmBackYes.setOnClickListener {
            workout.isCompleted = false
            storeWorkoutToHistory()
            finish()
        }
        confirmDialog.show()
    }

    private fun storeWorkoutToHistory(){
        val end = LocalDateTime.now().format(DateTimeFormatter
            .ofPattern("dd-MM-yyyy HH:mm"))
        workout.endDateTime = end
        lifecycleScope.launch {
            historyDao.insert(workout)
        }
    }

    private fun skipExercise(activityIndex: Int){
        val activity = exerciseList[activityIndex]
        currentExercise += 1
        workout.skipped += 1

        activityTimer?.cancel()
        activity.setIsSelected(false)
        activity.setIsCompleted(true)
        b?.rvProgress?.adapter?.notifyItemChanged(activityIndex)
        if(currentExercise < exerciseList.size) {
            startExercise(currentExercise, true)
        } else {
            workout.isCompleted = true
            storeWorkoutToHistory()
            speakOut(getString(R.string.tts_finished_workout))
            val intent = Intent(this@ExerciseActivity,
                FinishedExercisesActivity::class.java)
            startActivity(intent)
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

        if(startStopPlayer != null) {
            if (startStopPlayer?.isPlaying == true) {
                startStopPlayer?.stop()
            }
            startStopPlayer = null
        }

        if(tickPlayer != null) {
            if (tickPlayer?.isPlaying == true) {
                tickPlayer?.stop()
            }
            tickPlayer = null
        }

        b = null
    }
}