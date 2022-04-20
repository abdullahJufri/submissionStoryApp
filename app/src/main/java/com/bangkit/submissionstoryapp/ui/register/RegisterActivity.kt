package com.bangkit.submissionstoryapp.ui.register

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.bangkit.submissionstoryapp.R
import com.bangkit.submissionstoryapp.databinding.ActivityRegisterBinding
import com.bangkit.submissionstoryapp.ui.login.LoginActivity
import com.bangkit.submissionstoryapp.utils.ApiCallbackString
import com.bangkit.submissionstoryapp.utils.showLoading
import com.bangkit.submissionstoryapp.utils.showToast

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding
    private val viewmodel by viewModels<RegisterViewmodels>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewmodel.isLoading.observe(this) {
            showLoading(it, binding.progressBar)
        }
        buttonListener()


    }


    private fun buttonListener() {
        binding.btnRegister.setOnClickListener {
            val name = binding.editTextName.text.toString()
            val email = binding.myEditTextEmail.text.toString()
            val password = binding.myEditTextPassword.text.toString()
            if (email.isEmpty() || password.isEmpty() || name.isEmpty()) {
                val msg = getString(R.string.error_field)
                Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
            } else {
                viewmodel.register(name, email, password, object : ApiCallbackString {
                    override fun onResponse(success: Boolean, message: String) {
                        showAlertToast(success, message)
                    }
                })
            }
        }
    }

    private fun showAlertToast(param: Boolean, message: String) {
        if (param) {
            showToast(this@RegisterActivity, message)
            val intent = Intent(this@RegisterActivity, LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
            finish()


        } else {
            showToast(this@RegisterActivity, message)
            binding.progressBar.visibility = View.GONE

        }
    }
}