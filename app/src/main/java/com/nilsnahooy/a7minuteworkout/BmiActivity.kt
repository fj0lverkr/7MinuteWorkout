package com.nilsnahooy.a7minuteworkout

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.material.textfield.TextInputLayout
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
    }

    override fun onDestroy() {
        super.onDestroy()
        b = null
    }
}