package com.c242ps187.kidzlearnapp.view

import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.c242ps187.kidzlearnapp.databinding.ActivitySplashScreenBinding

class SplashScreen : AppCompatActivity() {
    private lateinit var binding: ActivitySplashScreenBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val progress = 1000
        val duration: Long = 3000
        binding.pbSplashScreen.max = progress

        ObjectAnimator.ofInt(binding.pbSplashScreen, "progress", progress)
            .setDuration(duration)
            .start()

        Handler().postDelayed({
            val intent = Intent(this@SplashScreen, ExerciseActivity::class.java)
            startActivity(intent)
        }, duration)
    }
}