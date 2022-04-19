package com.bangkit.submissionstoryapp.ui.routing

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.bangkit.submissionstoryapp.R
import com.bangkit.submissionstoryapp.ui.UserPreference
import com.bangkit.submissionstoryapp.ui.ViewModelFactory
import com.bangkit.submissionstoryapp.ui.login.LoginActivity
import com.bangkit.submissionstoryapp.ui.main.MainActivity
import com.bangkit.submissionstoryapp.ui.main.MainViewmodels
import com.bangkit.submissionstoryapp.ui.main.dataStore

class RoutingActivity : AppCompatActivity() {

    private lateinit var mainViewModel: MainViewmodels

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_routing)

        setupViewModel()
    }



    private fun setupViewModel() {
        mainViewModel = ViewModelProvider(
            this,
            ViewModelFactory(UserPreference.getInstance(dataStore))
        )[MainViewmodels::class.java]

        mainViewModel.getUser().observe(this) {
            if (it.isLogin) {
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            }else{
                startActivity(Intent(this, LoginActivity::class.java))
                finish()
            }
        }
    }
}