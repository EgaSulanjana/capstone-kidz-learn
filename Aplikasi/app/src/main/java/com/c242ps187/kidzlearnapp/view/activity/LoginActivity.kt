package com.c242ps187.kidzlearnapp.view.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.c242ps187.kidzlearnapp.R
import com.c242ps187.kidzlearnapp.data.result.Result
import com.c242ps187.kidzlearnapp.databinding.ActivityLoginBinding
import com.c242ps187.kidzlearnapp.utils.Utils.showLoading
import com.c242ps187.kidzlearnapp.view.viewmodel.MainViewModel
import com.c242ps187.kidzlearnapp.view.viewmodel.ViewModelFactory

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private val viewModel by viewModels<MainViewModel> {
        ViewModelFactory.getInstance(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.pbLogin.showLoading(false)

        binding.toRegister.setOnClickListener{
            startActivity(Intent(this, RegisterActivity::class.java))
            finish()
        }

        binding.loginButton.setOnClickListener{
            val email = binding.edEmail.text.toString()
            val password = binding.edPassword.text.toString()

            viewModel.login(email, password).observe(this){ result ->
                when(result){
                    is Result.Loading -> binding.pbLogin.showLoading(true)
                    is Result.Success -> {
                        binding.pbLogin.showLoading(false)
                        viewModel.saveSession(result.data.token)
                        AlertDialog.Builder(this).apply {
                            setTitle("Yeah!")
                            setMessage(result.data.message)
                            setPositiveButton(getString(R.string.continu)) { _, _ ->
                                val intent = Intent(this@LoginActivity, NavActivity::class.java)
                                intent.flags =
                                    Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                                startActivity(intent)
                                finish()
                            }
                            create()
                            show()
                        }
                    }
                    is Result.Error -> {
                        binding.pbLogin.showLoading(false)
                        Toast.makeText(this, result.error, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }
}