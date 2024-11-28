package com.c242ps187.kidzlearnapp.view.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.c242ps187.kidzlearnapp.R
import com.c242ps187.kidzlearnapp.data.result.Result
import com.c242ps187.kidzlearnapp.databinding.ActivityRegisterBinding
import com.c242ps187.kidzlearnapp.utils.Utils.showLoading
import com.c242ps187.kidzlearnapp.view.viewmodel.MainViewModel
import com.c242ps187.kidzlearnapp.view.viewmodel.ViewModelFactory

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding
    private val viewModel by viewModels<MainViewModel> {
        ViewModelFactory.getInstance(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.pbRegister.showLoading(false)

        binding.toLogin.setOnClickListener{
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }

        binding.btnRegister.setOnClickListener{
            val username = binding.edUsername.text.toString()
            val email = binding.edEmail.text.toString()
            val password = binding.edPassword.text.toString()

            viewModel.register(username, email, password).observe(this){ result ->
                when(result){
                    is Result.Loading -> binding.pbRegister.showLoading(true)
                    is Result.Success -> {
                        binding.pbRegister.showLoading(false)
                        viewModel.saveSession(result.data.token)
                        AlertDialog.Builder(this).apply {
                            setTitle("Yeah!")
                            setMessage(result.data.message)
                            setPositiveButton(getString(R.string.continu)) { _, _ ->
                                val intent = Intent(this@RegisterActivity, LoginActivity::class.java)
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
                        binding.pbRegister.showLoading(false)
                        Toast.makeText(this, result.error, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }
}