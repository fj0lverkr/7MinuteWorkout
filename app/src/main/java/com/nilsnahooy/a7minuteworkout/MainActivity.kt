package com.nilsnahooy.a7minuteworkout

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.FrameLayout

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val flStartButton: FrameLayout = findViewById(R.id.fl_button_wrapper)
        flStartButton.setOnClickListener{

        }
    }
}