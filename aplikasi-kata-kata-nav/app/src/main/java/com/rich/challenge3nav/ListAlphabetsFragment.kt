package com.rich.challenge3nav

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

class ListAlphabetsFragment : Fragment() {
    lateinit var alphabetsList : ArrayList<Alphabets>
    lateinit var alphabetsAdapter: AlphabetsAdapter
    lateinit var recyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_list_alphabets, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dataInit()
        recyclerView = view.findViewById(R.id.recyclerViewAlphabets)
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = GridLayoutManager(context, 3)
        alphabetsAdapter = AlphabetsAdapter(alphabetsList)
        recyclerView.adapter = alphabetsAdapter
    }

    private fun dataInit() {
        alphabetsList = arrayListOf(Alphabets("A"), Alphabets("B"),Alphabets("C"),
            Alphabets("D"), Alphabets("E"),Alphabets("F"),Alphabets("G"),Alphabets("H"),
            Alphabets("I"),Alphabets("J"),Alphabets("K"),Alphabets("L"))
    }
}