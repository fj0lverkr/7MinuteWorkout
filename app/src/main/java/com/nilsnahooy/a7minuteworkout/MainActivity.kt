package com.nilsnahooy.a7minuteworkout

/* NOTE ON USING VIEWBINDING
        - Faster, more efficient during compilation, safer, more concise in code.
        - becomes available after setting
                buildFeatures{
                    viewBinding true
                }
          in the android section of build.gradle
        - for usage, see code below (var b?)
        - make sure to set the binding back to null in onDestroy()
     */

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.FrameLayout
import com.nilsnahooy.a7minuteworkout.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private var b:ActivityMainBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        b = ActivityMainBinding.inflate(layoutInflater)
        setContentView(b?.root)

        b?.flButtonWrapper?.setOnClickListener {

        }
    }

    override fun onDestroy() {
        super.onDestroy()
        b = null
    }
}