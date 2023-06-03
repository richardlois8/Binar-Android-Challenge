package com.rich.challenge3

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView

class WordsAdapter(private val wordsList : ArrayList<Words>) : RecyclerView.Adapter<WordsAdapter.WordViewHolder>() {
    class WordViewHolder(val view: View) : RecyclerView.ViewHolder(view){
        fun bindView(words: Words) {
            val btnWords = view.findViewById<Button>(R.id.btnText)
            btnWords.text = words.wordValue
            btnWords.setOnClickListener {
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