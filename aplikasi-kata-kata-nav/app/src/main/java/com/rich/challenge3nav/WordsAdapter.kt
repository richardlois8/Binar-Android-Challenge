package com.rich.challenge3nav

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView

class WordsAdapter(private val wordsList : ArrayList<Words>) : RecyclerView.Adapter<WordsAdapter.WordViewHolder>() {
    class WordViewHolder(val view: View) : RecyclerView.ViewHolder(view){
        fun bindView(words: Words) {
            val btnWords = view.findViewById<Button>(R.id.btnWords)
            btnWords.text = words.wordValue
            btnWords.setOnClickListener {
                Toast.makeText(view.context, words.wordValue, Toast.LENGTH_SHORT).show()
                val activity = view.context as MainActivity
                val intent = Intent(Intent.ACTION_VIEW)
                intent.setData(Uri.parse("https://www.google.com/search?q=${words.wordValue}"))
                activity.startActivity(intent)

            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WordViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_words, parent, false)
        return WordViewHolder(view)
    }

    override fun onBindViewHolder(holder: WordViewHolder, position: Int) {
        holder.bindView(wordsList[position])
    }

    override fun getItemCount(): Int {
        return wordsList.size
    }
}