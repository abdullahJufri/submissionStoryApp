package com.bangkit.submissionstoryapp.ui.home

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.recyclerview.widget.LinearLayoutManager
import com.bangkit.submissionstoryapp.data.remote.model.Authentication
import com.bangkit.submissionstoryapp.databinding.ActivityHomeBinding


private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")
class HomeActivity : AppCompatActivity() {
//
//    private lateinit var storyViewModel: ShareUserViewmodels

    private var _binding: ActivityHomeBinding? = null
    private val binding get() = _binding
    private val viewModel by viewModels<HomeViewmodels>()

    private lateinit var authentication: Authentication
    private lateinit var adapter: HomeAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding?.root)
//
//        setupToolbar()
//        addStoryAction()

        authentication = intent.getParcelableExtra(EXTRA_USER)!!

        setListStory()
        adapter = HomeAdapter()

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

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
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

//    private fun addStoryAction(){
//        binding?.ivAddStory?.setOnClickListener {
//            val moveToAddStoryActivity = Intent(this, AddStoryActivity::class.java)
//            moveToAddStoryActivity.putExtra(AddStoryActivity.EXTRA_USER, user)
//            startActivity(moveToAddStoryActivity)
//        }
//    }

    companion object {
        const val EXTRA_USER = "user"
    }
}