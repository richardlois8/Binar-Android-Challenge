package com.rich.momsnote.fragment

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.rich.momsnote.R
import com.rich.momsnote.databinding.FragmentSplashScreenBinding
import com.rich.momsnote.viewmodel.UserViewModel

@SuppressLint("CustomSplashScreen")
class SplashScreenFragment : Fragment() {
    private lateinit var binding : FragmentSplashScreenBinding
    private lateinit var userVM : UserViewModel
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var editor: SharedPreferences.Editor
    companion object{
        const val PREFS_NAME = "dataUser"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentSplashScreenBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        userVM = ViewModelProvider(requireActivity()).get(UserViewModel::class.java)

        sharedPreferences = requireActivity().getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        editor = sharedPreferences.edit()

        Handler(Looper.myLooper()!!).postDelayed({
            checkIsLogin()
        }, 2000)

    }

    private fun checkIsLogin(){
        if(sharedPreferences.getBoolean("isLogin",false)){
            navigateToHome()
        }else{
            navigateToLogin()
        }
    }

    private fun navigateToLogin(){
        findNavController().navigate(R.id.action_splashScreenFragment_to_loginFragment)
    }

    private fun navigateToHome(){
        findNavController().navigate(R.id.action_splashScreenFragment_to_homeFragment)
    }
}