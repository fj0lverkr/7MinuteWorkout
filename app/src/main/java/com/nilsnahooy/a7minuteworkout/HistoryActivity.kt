package com.nilsnahooy.a7minuteworkout

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate
import com.nilsnahooy.a7minuteworkout.databinding.ActivityHistoryBinding

class HistoryActivity : AppCompatActivity() {
    var b : ActivityHistoryBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        b = ActivityHistoryBinding.inflate(layoutInflater)
        setContentView(b?.root)

        setSupportActionBar(b?.tbHistory)
        if(supportActionBar != null) {
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }

        b?.tbHistory?.setNavigationOnClickListener {
            finish()
        }
    }
}