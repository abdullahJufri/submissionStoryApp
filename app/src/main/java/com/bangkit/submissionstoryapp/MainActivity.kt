package com.bangkit.submissionstoryapp

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import androidx.appcompat.app.AlertDialog
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import com.bangkit.submissionstoryapp.data.remote.model.User
import com.bangkit.submissionstoryapp.databinding.ActivityMainBinding
import com.bangkit.submissionstoryapp.ui.UserPreference
import com.bangkit.submissionstoryapp.ui.ViewModelFactory
import com.bangkit.submissionstoryapp.ui.activity.HomeActivity
import com.bangkit.submissionstoryapp.ui.viewmodels.MainViewmodels

val Context.dataStore: DataStore<Preferences> by preferencesDataStore("settings")
class MainActivity : AppCompatActivity() {
    private lateinit var user: User
    private lateinit var mainViewModel: MainViewmodels
    private lateinit var binding: ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupViewModel()
//        playAnimation()
        buttonListener()
    }

    private fun setupViewModel() {
        mainViewModel = ViewModelProvider(
            this,
            ViewModelFactory(UserPreference.getInstance(dataStore))
        )[MainViewmodels::class.java]

        mainViewModel.getUser().observe(this) {
            user = User(
                it.name,
                it.email,
                it.password,
                it.userId,
                it.token,
                true
            )
            binding.nameTextView.text = getString(R.string.greeting, user.name)
        }
    }

    private fun buttonListener() {
        binding.btnLisStory.setOnClickListener {
            val moveToListStoryActivity = Intent(this@MainActivity, HomeActivity::class.java)
            moveToListStoryActivity.putExtra(HomeActivity.EXTRA_USER, user)
            startActivity(moveToListStoryActivity)
        }
//        binding.ivSetting?.setOnClickListener {
//            startActivity(Intent(Settings.ACTION_LOCALE_SETTINGS))
//        }
//        binding.btnLogOut.setOnClickListener {
//            mainViewModel.logout()
//            AlertDialog.Builder(this).apply {
//                setTitle(getString(R.string.information))
//                setMessage(getString(R.string.log_out_success))
//                setPositiveButton(getString(R.string.continue_)) { _, _ ->
//                    startActivity(Intent(this@MainActivity, SignInActivity::class.java))
//                    finish()
//                }
//                create()
//                show()
//            }
//        }
    }
}