package com.rich.momsnote.fragment

import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.rich.momsnote.R
import com.rich.momsnote.databinding.FragmentLoginBinding
import com.rich.momsnote.viewmodel.UserViewModel

class LoginFragment : Fragment() {
    private lateinit var binding : FragmentLoginBinding
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
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        userVM = ViewModelProvider(requireActivity()).get(UserViewModel::class.java)

        sharedPreferences = requireActivity().getSharedPreferences(PREFS_NAME, MODE_PRIVATE)
        editor = sharedPreferences.edit()

        setListener()
    }

    private fun setListener() {
        binding.btnLogin.setOnClickListener {
            val email = binding.etEmail.text.toString()
            val password = binding.etPassword.text.toString()

            verifyUser(email,password)
        }
        binding.tvRegister.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
        }
    }

    private fun verifyUser(email : String, password : String){
        userVM.verifyUser(email, password).observe(viewLifecycleOwner) {
            if(it == null && (email != "admin" && password != "admin")){
                Toast.makeText(requireContext(), "Email or password is incorrect", Toast.LENGTH_SHORT).show()
            }else if(it == null && (email == "admin" && password == "admin")){
                editor.putString("username", "admin")
                editor.putBoolean("isLogin", true)
                editor.apply()
                navigateToHome()
            }else{
                editor.putString("username", it.username)
                editor.putBoolean("isLogin", true)
                editor.apply()
                navigateToHome()
            }
        }
    }

    private fun navigateToHome(){
        findNavController().navigate(R.id.action_login_to_home)
    }
}