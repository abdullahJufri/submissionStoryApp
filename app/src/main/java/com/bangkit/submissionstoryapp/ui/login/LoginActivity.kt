package com.bangkit.submissionstoryapp.ui.login

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import com.bangkit.submissionstoryapp.R
import com.bangkit.submissionstoryapp.databinding.ActivityLoginBinding
import com.bangkit.submissionstoryapp.data.local.UserPreference
import com.bangkit.submissionstoryapp.ui.ViewModelFactory
import com.bangkit.submissionstoryapp.ui.main.MainActivity
import com.bangkit.submissionstoryapp.ui.register.RegisterActivity
import com.bangkit.submissionstoryapp.utils.ApiCallbackString
import com.bangkit.submissionstoryapp.utils.showLoading
import com.bangkit.submissionstoryapp.utils.showToast


private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var viewmodel: LoginViewmodels

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)

        setContentView(binding.root)

        setupViewModel()


        viewmodel.isLoading.observe(this) {
            showLoading(it, binding.progressBar)
        }
        buttonListener()

        binding.btnRegister.setOnClickListener {
            startActivity(Intent(this@LoginActivity, RegisterActivity::class.java))
            finish()
        }
    }

    private fun setupViewModel() {
        viewmodel = ViewModelProvider(
            this,
            ViewModelFactory(UserPreference.getInstance(dataStore))
        )[LoginViewmodels::class.java]
    }

    private fun buttonListener() {
        binding.btnLogin.setOnClickListener {
            val email = binding.myEmail.text.toString()
            val password = binding.myPassword.text.toString()
            if (email.isEmpty() || password.isEmpty()) {
                val msg = getString(R.string.error_field)
                Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
            } else {
                viewmodel.login(email, password, object : ApiCallbackString {
                    override fun onResponse(success: Boolean, message: String) {
                        showAlertToast(success, message)
                    }
                })
            }
        }
    }

    private fun showAlertToast(param: Boolean, message: String) = if (param) {
        showToast(this@LoginActivity, ", $message")
        val intent = Intent(this@LoginActivity, MainActivity::class.java)
        intent.flags =
            Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent)
        finish()
    } else {
        showToast(this@LoginActivity, message)
        binding.progressBar.visibility = View.GONE
    }
}
