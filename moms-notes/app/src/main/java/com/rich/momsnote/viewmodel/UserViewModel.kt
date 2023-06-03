package com.rich.momsnote.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.rich.momsnote.database.NotesTakingDB
import com.rich.momsnote.database.User
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async

class UserViewModel(app : Application) : AndroidViewModel(app) {
    private var allUser : MutableLiveData<List<User>> = MutableLiveData()

    fun verifyUser(email : String, password : String) : LiveData<User> = NotesTakingDB.getInstance((getApplication()))!!.userDao().verifyUser(email, password)

    fun insertUser(user : User){
        GlobalScope.async {
            val userDAO = NotesTakingDB.getInstance(getApplication())?.userDao()!!
            userDAO.insertUser(user)
        }
    }
}