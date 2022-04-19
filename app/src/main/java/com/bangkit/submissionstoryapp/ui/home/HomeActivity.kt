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
import com.bangkit.submissionstoryapp.ui.UserPreference
import com.bangkit.submissionstoryapp.ui.ViewModelFactory
import com.bangkit.submissionstoryapp.ui.addstory.AddStoryActivity
import com.bangkit.submissionstoryapp.ui.login.LoginActivity
import com.bangkit.submissionstoryapp.ui.main.MainViewmodels


private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class HomeActivity : AppCompatActivity() {


    private var _binding: ActivityHomeBinding? = null
    private val binding get() = _binding
    private val viewModel by viewModels<HomeViewmodels>()
    private lateinit var mainViewmodels: MainViewmodels


    private lateinit var authentication: Authentication
    private lateinit var adapter: HomeAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding?.root)
//
//        setupToolbar()
//        addStoryAction()
        setupViewModel()

        authentication = intent.getParcelableExtra(EXTRA_USER)!!

        setListStory()
        adapter = HomeAdapter()

        addStoryAction()

//        showSnackBar()

        binding?.rvStories?.layoutManager = LinearLayoutManager(this)
        binding?.rvStories?.setHasFixedSize(true)
        binding?.rvStories?.adapter = adapter

//        showLoading()
//        showHaveDataOrNot()
    }

//    private fun setupToolbar(){
//        setSupportActionBar(binding?.toolbar)
//        supportActionBar?.setDisplayHomeAsUpEnabled(true)
//        supportActionBar?.setDisplayShowHomeEnabled(true)
//    }

    private fun setupViewModel() {
        mainViewmodels = ViewModelProvider(
            this,
            ViewModelFactory(UserPreference.getInstance(dataStore))
        )[MainViewmodels::class.java]
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.option_menu, menu)

//        val addMenu = menu.findItem(R.id.menu_add)
//        val logoutMenu = menu.findItem(R.id.menu_logout)
//
//        addMenu.isVisible = false
//        logoutMenu.isVisible = false

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
                    setTitle(getString(R.string.information))
                    setMessage(getString(R.string.log_out_success))
                    setPositiveButton(getString(R.string.continue_)) { _, _ ->
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

//    private fun showSnackBar() {
//        viewModel.snackBarText.observe(this) {
//            it.getContentIfNotHandled()?.let { snackBarText ->
//                Snackbar.make(
//                    findViewById(R.id.rv_story),
//                    snackBarText,
//                    Snackbar.LENGTH_SHORT
//                ).show()
//            }
//        }
//    }

//    private fun showLoading() {
//        viewModel.isLoading.observe(this) {
//            binding?.apply {
//                if (it) {
//                    progressBar.visibility = View.VISIBLE
//                    rvStory.visibility = View.INVISIBLE
//                } else {
//                    progressBar.visibility = View.GONE
//                    rvStory.visibility = View.VISIBLE
//                }
//            }
//        }
//    }

//    private fun showHaveDataOrNot(){
//        viewModel.isHaveData.observe(this){
//            binding?.apply {
//                if (it) {
//                    tvInfo.visibility = View.GONE
//                } else {
//                    tvInfo.visibility = View.VISIBLE
//                }
//            }
//        }
//    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun setListStory() {
        viewModel.showListStory(authentication.token)
        viewModel.itemStory.observe(this) {
            adapter.setListStory(it)
        }
    }

    override fun onResume() {
        super.onResume()
        setListStory()
    }

    private fun addStoryAction() {
        binding?.fabAdd?.setOnClickListener {
            val moveToAddStoryActivity = Intent(this, AddStoryActivity::class.java)
            moveToAddStoryActivity.putExtra(AddStoryActivity.EXTRA_USER, authentication)
            startActivity(moveToAddStoryActivity)
        }
    }

    companion object {
        const val EXTRA_USER = "user"
    }
}