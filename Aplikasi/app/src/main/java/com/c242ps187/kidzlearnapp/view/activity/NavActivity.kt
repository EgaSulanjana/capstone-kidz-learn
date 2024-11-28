package com.c242ps187.kidzlearnapp.view.activity

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.c242ps187.kidzlearnapp.R
import com.c242ps187.kidzlearnapp.databinding.ActivityNavBinding
import com.c242ps187.kidzlearnapp.view.viewmodel.MainViewModel
import com.c242ps187.kidzlearnapp.view.viewmodel.ViewModelFactory
import com.google.android.material.bottomnavigation.BottomNavigationView

class NavActivity : AppCompatActivity() {
    private lateinit var binding: ActivityNavBinding
    private val viewModel by viewModels<MainViewModel> {
        ViewModelFactory.getInstance(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        checkToken()
    }

    private fun checkToken(){
        viewModel.getSession().observe(this){ token ->
            if(token.isEmpty()){
                startActivity(Intent(this, LoginActivity::class.java))
                finish()
            }else{
                setupView()
            }
        }
    }

    private fun setupView() {
        binding = ActivityNavBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navView: BottomNavigationView = binding.navView

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment_activity_nav) as NavHostFragment
        val navController = navHostFragment.navController
        navView.setupWithNavController(navController)
    }
}