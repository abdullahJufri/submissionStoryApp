package com.bangkit.submissionstoryapp.ui.main

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import com.bangkit.submissionstoryapp.R
import com.bangkit.submissionstoryapp.data.remote.model.Authentication
import com.bangkit.submissionstoryapp.databinding.ActivityMainBinding
import com.bangkit.submissionstoryapp.data.local.UserPreference
import com.bangkit.submissionstoryapp.ui.ViewModelFactory
import com.bangkit.submissionstoryapp.ui.home.HomeActivity

val Context.dataStore: DataStore<Preferences> by preferencesDataStore("settings")

class MainActivity : AppCompatActivity() {
    private lateinit var authentication: Authentication
    private lateinit var mainViewModel: MainViewmodels
    private lateinit var binding: ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupViewModel()
        buttonListener()
        
    }

    private fun setupViewModel() {
        mainViewModel = ViewModelProvider(
            this,
            ViewModelFactory(UserPreference.getInstance(dataStore))
        )[MainViewmodels::class.java]

        mainViewModel.getUser().observe(this) {
            authentication = Authentication(
                it.name,
                it.email,
                it.password,
                it.userId,
                it.token,
                true
            )
            binding.nameTextView.text = getString(R.string.greeting, authentication.name)
        }
    }

    private fun buttonListener() {
        binding.btnLisStory.setOnClickListener {
            val moveToListStoryActivity = Intent(this@MainActivity, HomeActivity::class.java)
            moveToListStoryActivity.putExtra(HomeActivity.EXTRA_USER, authentication)
            startActivity(moveToListStoryActivity)
            finish()
        }

    }
}