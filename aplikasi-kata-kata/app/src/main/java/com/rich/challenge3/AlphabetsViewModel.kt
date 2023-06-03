package com.rich.challenge3

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class AlphabetsViewModel : ViewModel() {
    private var _clickedAlphabet = MutableLiveData<Alphabets>(null)
    val clickedAlphabet: LiveData<Alphabets> = _clickedAlphabet

    fun saveClickedAlphabet(alphabet: Alphabets) {
        _clickedAlphabet.value = alphabet
    }
}