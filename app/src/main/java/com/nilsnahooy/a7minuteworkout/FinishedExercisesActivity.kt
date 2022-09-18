package com.nilsnahooy.a7minuteworkout

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.nilsnahooy.a7minuteworkout.databinding.ActivityFinishedBinding

class FinishedExercisesActivity : AppCompatActivity() {
    private var b: ActivityFinishedBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        b = ActivityFinishedBinding.inflate(layoutInflater)
        setContentView(b?.root)

        setSupportActionBar(b?.tbExercise)

        b?.btnFinish?.setOnClickListener {
            val intent =  Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        b = null
    }
}