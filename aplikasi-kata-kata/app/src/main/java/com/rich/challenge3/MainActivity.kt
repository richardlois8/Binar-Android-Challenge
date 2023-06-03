package com.rich.challenge3

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels


class MainActivity : AppCompatActivity() {
    private lateinit var alphaFrag : ListAlphabetsFragment
    private val viewModel: AlphabetsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        val alphabetsFragment = ListAlphabetsFragment()
//        val fragmentTransaction = supportFragmentManager.beginTransaction()
//        fragmentTransaction.add(R.id.fragmentContainer, alphabetsFragment)
//        fragmentTransaction.commit()

        if (viewModel.clickedAlphabet.value == null) {
            alphaFrag  = ListAlphabetsFragment()
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainer, ListAlphabetsFragment())
                .commit()
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        supportFragmentManager.beginTransaction().replace(R.id.fragmentContainer, ListAlphabetsFragment()).commit()
        return super.onSupportNavigateUp()
    }
}