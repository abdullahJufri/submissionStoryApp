//package com.bangkit.submissionstoryapp.ui.fragment
//
//import android.content.Intent
//import android.os.Bundle
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import android.widget.Toast
//import androidx.fragment.app.Fragment
//import androidx.fragment.app.viewModels
//import com.bangkit.submissionstoryapp.R
//import com.bangkit.submissionstoryapp.data.remote.model.LoginResult
//import com.bangkit.submissionstoryapp.databinding.FragmentLoginBinding
//import com.bangkit.submissionstoryapp.ui.viewmodels.LoginViewmodels
//
//// TODO: Rename parameter arguments, choose names that match
//
//class LoginFragment : Fragment() {
//    private var _binding: FragmentLoginBinding? = null
//    private val binding get() = _binding!!
//    private val viewModel by viewModels<LoginViewmodels>()
//
//    private lateinit var user: LoginResult
//
//    override fun onCreateView(
//        inflater: LayoutInflater, container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? {
//        // Inflate the layout for this fragment
//        _binding = FragmentLoginBinding.inflate(inflater, container, false)
//        // Inflate the layout for this fragment
//
//        return binding.root
//    }
//
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//
////        factory = ViewModelFactory.getInstance(requireActivity())
////        initObserve()
//
//
//        binding.btnLogin.setOnClickListener {
//            val email = binding.myEmail.text.toString()
//            val password = binding.myPassword.text.toString()
//
//
//            if (email.isEmpty() || password.isEmpty()) {
//                val msg = getString(R.string.fill_field)
//                Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
//            } else {
//                viewModel.login(email, password)
////                viewModel.error.observe(viewLifecycleOwner) { event ->
////                    event.getContentIfNotHandled()?.let { error ->
////                        if (!error) {
////                            viewModel.user.observe(viewLifecycleOwner) {event ->
////                                event.getContentIfNotHandled()?.let {
////                                    Toast.makeText(context, "sukses", Toast.LENGTH_SHORT).show()
//////                                    val intent = Intent(activity, MainActivity::class.java)
//////                                    startActivity(intent)
////                                    activity?.finish()
////                                }
////
////                            }
////
////                        }
////                    }
////                }
//                val moveToListStoryActivity = Intent(requireActivity(), HomeFragment::class.java)
//                moveToListStoryActivity.putExtra(HomeFragment.EXTRA_USER, user.token)
//                startActivity(moveToListStoryActivity)
//            }
//
//
//
//        }
//    }
//}
//
//
