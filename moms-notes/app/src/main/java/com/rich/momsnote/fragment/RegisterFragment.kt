package com.rich.momsnote.fragment

import android.content.Context
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
import com.rich.momsnote.database.User
import com.rich.momsnote.databinding.FragmentRegisterBinding
import com.rich.momsnote.viewmodel.UserViewModel

class RegisterFragment : Fragment() {
    private lateinit var binding: FragmentRegisterBinding
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
        binding = FragmentRegisterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        userVM = ViewModelProvider(requireActivity()).get(UserViewModel::class.java)
        sharedPreferences = requireActivity().getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        editor = sharedPreferences.edit()

        binding.btnRegister.setOnClickListener {
            val username = binding.etRegisUsername.text.toString()
            val email = binding.etRegisEmail.text.toString()
            val password = binding.etRegisPassword.text.toString()
            val confirmPassword = binding.etRegisPasswordConfirm.text.toString()
            if(verifyPassword(password, confirmPassword)){
                saveUser(username, email, password)
                Toast.makeText(context, "Registration Success", Toast.LENGTH_SHORT).show()
                findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
            }else{
                Toast.makeText(context, "Confirmation Password does not match", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun verifyPassword(password: String, confirmPassword: String): Boolean {
        return password == confirmPassword
    }

    private fun saveUser(username : String, email : String, password : String){
        userVM.insertUser(User(0,username,email,password))
    }
}