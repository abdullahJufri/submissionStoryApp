package com.bangkit.submissionstoryapp.ui.activity

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.bangkit.submissionstoryapp.MainActivity
import com.bangkit.submissionstoryapp.R
import com.bangkit.submissionstoryapp.data.remote.model.LoginResult
import com.bangkit.submissionstoryapp.databinding.ActivityLoginBinding
import com.bangkit.submissionstoryapp.ui.UserPreference
import com.bangkit.submissionstoryapp.ui.ViewModelFactory
import com.bangkit.submissionstoryapp.ui.viewmodels.LoginViewmodels

import com.bangkit.submissionstoryapp.utils

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")
class LoginActivity : AppCompatActivity() {
    //
//    private lateinit var mainViewModel: SharedViewModel
    private lateinit var binding: ActivityLoginBinding
//    private val viewModel by viewModels <LoginViewmodels>()
    private lateinit var viewModel: LoginViewmodels

    private lateinit var user: LoginResult
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)

        setContentView(binding.root)

        setupViewModel()
//        buttonListener()

        binding.btnLogin.setOnClickListener {
            val email = binding.myEmail.text.toString()
            val password = binding.myPassword.text.toString()
            if (email.isEmpty() || password.isEmpty()) {
                val msg = getString(R.string.fill_field)
                Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
            } else {
                viewModel.login(email, password, object : utils.ApiCallbackString {
                    override fun onResponse(success: Boolean,message: String) {
                        val intent = Intent(this@LoginActivity, MainActivity::class.java)
                        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                        startActivity(intent)
                        finish()
                    }
                })
            }


        }
//        setupViewModel()\
//        viewModel = ViewModelProvider(this,ViewModelFactory)

//        binding.btnLogin.setOnClickListener {
//            val email = binding.myEmail.text.toString()
//            val password = binding.myPassword.text.toString()
//
//
//            if (email.isEmpty() || password.isEmpty()) {
//                val msg = getString(R.string.fill_field)
////                Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
//            }
//            else {
//                viewModel.login(email, password)
//            }

//            val intent = Intent(this, MainActivity::class.java)
//            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
//            startActivity(intent)
//            finish()

//            viewModel.login(email, password, object : utils.ApiCallbackString {
//                override fun onResponse(success: Boolean,message: String) {
//                    showAlertDialog(success, message)
//                }
//            })
        }


//    private fun buttonListener() {
//        binding.btnLogin.setOnClickListener {
//            val email = binding.myEmail.text.toString()
//            val password = binding.myPassword.text.toString()
//
//            viewModel.login(email, password, object : utils.ApiCallbackString {
//                override fun onResponse(success: Boolean,message: String) {
//                    showAlertDialog(success, message)
//                }
//            })
//        }
//    }

    private fun setupViewModel() {
        viewModel = ViewModelProvider(
            this,
            ViewModelFactory(UserPreference.getInstance(dataStore))
        )[LoginViewmodels::class.java]
    }

//    private fun showAlertDialog(param: Boolean, message: String) {
//        if (param) {
//            AlertDialog.Builder(this).apply {
//                setTitle(getString(R.string.information))
//                setMessage(getString(R.string.sign_in_success))
//                setPositiveButton(getString(R.string.continue_)) { _, _ ->
//                    val intent = Intent(context, MainActivity::class.java)
//                    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
//                    startActivity(intent)
//                    finish()
//                }
//                create()
//                show()
//            }
//        } else {
//            AlertDialog.Builder(this).apply {
//                setTitle(getString(R.string.information))
//                setMessage(getString(R.string.sign_in_failed) +", $message")
////                setPositiveButton(getString(R.string.continue_)) { _, _ ->
////                    binding.progressBar.visibility = View.GONE
////                }
//                create()
//                show()
//
//            }
//        }
//    }

//    private fun setupViewModel() {
//        viewModelShared = ViewModelProvider(
//            this,
//            ViewModelFactory(UserPreference.getInstance(dataStore))
//        )[ShareUserViewmodels::class.java]
//
//        viewModelShared.getUser().observe(this) { user ->
//            if(user.isLogin) {
//                val intent = Intent(this, HomeActivity::class.java)
//                startActivity(intent)
//                finish()
//            }
//        }
//    }
}