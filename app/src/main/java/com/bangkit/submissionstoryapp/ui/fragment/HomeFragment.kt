//package com.bangkit.submissionstoryapp.ui.fragment
//
//import android.os.Bundle
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import androidx.annotation.StringRes
//import androidx.fragment.app.Fragment
//import androidx.fragment.app.viewModels
//import com.bangkit.submissionstoryapp.R
//import com.bangkit.submissionstoryapp.data.remote.model.LoginResult
//import com.bangkit.submissionstoryapp.databinding.FragmentHomeBinding
//import com.bangkit.submissionstoryapp.ui.adapter.ListStoriesAdapter
//import com.bangkit.submissionstoryapp.ui.viewmodels.HomeViewmodels
//
//class HomeFragment : Fragment() {
//    private var _binding: FragmentHomeBinding? = null
//    private val binding get() = _binding!!
//    private val viewModel by viewModels<HomeViewmodels>()
//
//    private lateinit var user: LoginResult
//
//    private lateinit var adapter: ListStoriesAdapter
//
////    override fun onCreate(savedInstanceState: Bundle?) {
////        super.onCreate(savedInstanceState)
////        _binding = FragmentHomeBinding.inflate(layoutInflater)
////
////
////
////
////        adapter=ListStoriesAdapter()
////        binding?.rvItemStories?.layoutManager = LinearLayoutManager(this)
////
////
////    }
//
//    override fun onCreateView(
//        inflater: LayoutInflater, container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? {
//        // Inflate the layout for this fragment
//        _binding = FragmentHomeBinding.inflate(inflater, container, false)
//
//        return binding.root
//    }
//
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//
//
//
//        fetchListStories()
//
//
////        initObserve()
//    }
//
//    private fun fetchListStories() {
//        viewModel.showAllStories(user.token)
//        viewModel.listItem.observe(viewLifecycleOwner) {
//            adapter.setListStories(it)
//        }
//    }
//
////    private fun initObserve() {
////        viewModel.getName().observe(viewLifecycleOwner) {
////            binding.tvWelcomeName.text = it
////        }
////        viewModel.message.observe(viewLifecycleOwner) {
////            Toast.makeText(activity, it, Toast.LENGTH_SHORT).show()
////        }
//
//
//    companion object {
//        const val EXTRA_USER = "extra_user"
//
//    }
//}
