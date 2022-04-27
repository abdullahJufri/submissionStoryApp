package com.bangkit.submissionstoryapp.ui.home

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bangkit.submissionstoryapp.R
import com.bangkit.submissionstoryapp.data.remote.model.Authentication
import com.bangkit.submissionstoryapp.databinding.ActivityHomeBinding
import com.bangkit.submissionstoryapp.data.local.UserPreference
import com.bangkit.submissionstoryapp.ui.ViewModelFactory
import com.bangkit.submissionstoryapp.ui.addstory.AddStoryActivity
import com.bangkit.submissionstoryapp.ui.login.LoginActivity
import com.bangkit.submissionstoryapp.ui.main.MainViewmodels
import com.bangkit.submissionstoryapp.utils.showLoading


private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class HomeActivity : AppCompatActivity() {


    private lateinit var binding: ActivityHomeBinding
    private val viewmodel by viewModels<HomeViewmodels>()
    private lateinit var mainViewmodels: MainViewmodels


    private lateinit var authentication: Authentication
    private lateinit var adapter: HomeAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        authentication = intent.getParcelableExtra(EXTRA_USER)!!
        setupViewModel()


        viewmodel.isLoading.observe(this) {
            showLoading(it, binding.progressBar)
        }



        setListStory()
        adapter = HomeAdapter()
        addStoryAction()

        binding.rvStories.layoutManager = LinearLayoutManager(this)

        binding.rvStories.adapter = adapter.withLoadStateFooter(
            footer = LoadingStateAdapter {
                adapter.retry()
            }
        )
        binding.rvStories.setHasFixedSize(true)


//        binding.rvStories.adapter = adapter

    }

    private fun setupViewModel() {
        mainViewmodels = ViewModelProvider(
            this,
            ViewModelFactory(UserPreference.getInstance(dataStore),this)
        )[MainViewmodels::class.java]

    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.option_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_language -> {
                val intent = Intent(Settings.ACTION_LOCALE_SETTINGS)
                startActivity(intent)
                return true
            }
            R.id.menu_logout -> {
                mainViewmodels.logout()
                AlertDialog.Builder(this).apply {
                    setTitle(getString(R.string.information_title))
                    setMessage(getString(R.string.log_out_success))
                    setPositiveButton(getString(R.string.btn_continue)) { _, _ ->
                        startActivity(Intent(this@HomeActivity, LoginActivity::class.java))
                        finish()
                    }
                    create()
                    show()
                }
                return true
            }
            else -> super.onOptionsItemSelected(item)
        }
        return true
    }

    private fun setListStory() {
//        viewmodel.showListStory(authentication.token)
//        viewmodel.itemStory.observe(this) {
//            adapter.setListStory(it)
//        }
        mainViewmodels.getStories(authentication.token).observe(this) {
            adapter.submitData(lifecycle, it)
        }
    }

    override fun onResume() {
        super.onResume()
        setListStory()
    }

    private fun addStoryAction() {
        binding.fabAdd.setOnClickListener {
            val moveToAddStoryActivity = Intent(this, AddStoryActivity::class.java)
            moveToAddStoryActivity.putExtra(AddStoryActivity.EXTRA_USER, authentication)
            startActivity(moveToAddStoryActivity)
        }
    }

    companion object {
        const val EXTRA_USER = "user"
    }
}