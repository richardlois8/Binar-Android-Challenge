package com.rich.challenge3nav

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView

class AlphabetsAdapter(private val alphabetsList: ArrayList<Alphabets>):
    RecyclerView.Adapter<AlphabetsAdapter.AlphabetsViewHolder>() {

    class AlphabetsViewHolder(itemView:View):RecyclerView.ViewHolder(itemView){

        fun bindView(alphabetParam: Alphabets){
            val btnAlphabets = itemView.findViewById<Button>(R.id.btnAlphabets)
            btnAlphabets.text = alphabetParam.alphabetValue
            btnAlphabets.setOnClickListener {
                val dataBundle = Bundle()
                dataBundle.putString("clickedAlphabet",btnAlphabets.text.toString())
                it.findNavController().navigate(R.id.action_listAlphabetsFragment_to_listWordsFragment,dataBundle)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlphabetsViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_alphabets,parent,false)
        return AlphabetsViewHolder(view)
    }

    override fun onBindViewHolder(holder: AlphabetsViewHolder, position: Int) {
        holder.bindView(alphabetsList[position])
    }

    override fun getItemCount(): Int {
        return alphabetsList.size
    }
}