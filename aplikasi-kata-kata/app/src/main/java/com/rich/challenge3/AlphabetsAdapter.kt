package com.rich.challenge3

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

class AlphabetsAdapter(private val alphabetsList: ArrayList<Alphabets>, val layoutManager: GridLayoutManager):
    RecyclerView.Adapter<AlphabetsAdapter.AlphabetsViewHolder>() {
    val grid_view = 1
    val list_view = 2
    var onClick : ((Alphabets) -> Unit)? = null

    class AlphabetsViewHolder(itemView:View):RecyclerView.ViewHolder(itemView){
        val btnAlphabetsGrid = itemView.findViewById<Button>(R.id.btnAlphabets)
        val btnAlphabetsList = itemView.findViewById<Button>(R.id.btnText)

//        fun bindViewGrid(alphabetParam: Alphabets){
//            val btnAlphabets = itemView.findViewById<Button>(R.id.btnAlphabets)
//            btnAlphabets.text = alphabetParam.alphabetValue
//            btnAlphabets.setOnClickListener {
//                var activity : AppCompatActivity = itemView.context as AppCompatActivity
//                val wordsFragment = ListWordsFragment(btnAlphabets.text.toString())
//                activity.supportFragmentManager.beginTransaction().replace(R.id.fragmentContainer,wordsFragment).addToBackStack("frag").commit()
//            }
//        }
//
//        fun bindViewList(alphabetParam: Alphabets){
//            val btnAlphabets = itemView.findViewById<Button>(R.id.btnText)
//            btnAlphabets.text = alphabetParam.alphabetValue
//            btnAlphabets.setOnClickListener {
//                var activity : AppCompatActivity = itemView.context as AppCompatActivity
//                val wordsFragment = ListWordsFragment(btnAlphabets.text.toString())
//                activity.supportFragmentManager.beginTransaction().replace(R.id.fragmentContainer,wordsFragment).addToBackStack("frag").commit()
//            }
//        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlphabetsViewHolder {
        var view : View
        if(viewType == grid_view) {
            view = LayoutInflater.from(parent.context)
                .inflate(R.layout.list_alphabets, parent, false)
        }else{
            view = LayoutInflater.from(parent.context)
                .inflate(R.layout.list_words, parent, false)
        }
        return AlphabetsViewHolder(view)
    }

    override fun onBindViewHolder(holder: AlphabetsViewHolder, position: Int) {
        if(layoutManager.spanCount == 1){
//            holder.bindViewList(alphabetsList[position])
            holder.btnAlphabetsList.text = alphabetsList[position].alphabetValue
            holder.btnAlphabetsList.setOnClickListener {
                onClick?.invoke(alphabetsList[position])
            }
        }else {
//            holder.bindViewGrid(alphabetsList[position])
            holder.btnAlphabetsGrid.text = alphabetsList[position].alphabetValue
            holder.btnAlphabetsGrid.setOnClickListener {
                onClick?.invoke(alphabetsList[position])
            }
        }
    }

    override fun getItemCount(): Int {
        return alphabetsList.size
    }

    override fun getItemViewType(position: Int): Int {
        val gridCount = layoutManager.spanCount
        if(gridCount == 3){
            return grid_view
        }else{
            return list_view
        }
    }
}