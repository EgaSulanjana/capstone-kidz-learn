package com.c242ps187.kidzlearnapp.view.activity

import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.c242ps187.kidzlearnapp.databinding.ActivitySplashScreenBinding

@SuppressLint("CustomSplashScreen")
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

        Handler(Looper.getMainLooper()).postDelayed({
            val intent = Intent(this@SplashScreen, NavActivity::class.java)
            startActivity(intent)
            finish()
        }, duration)


    }
}