package com.rich.challenge3

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

class ListAlphabetsFragment : Fragment() {
    lateinit var alphabetsList : ArrayList<Alphabets>
    lateinit var alphabetsAdapter: AlphabetsAdapter
    lateinit var recyclerView: RecyclerView
    lateinit var layoutManager: GridLayoutManager
    private val viewModel : AlphabetsViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        setHasOptionsMenu(true)
        return inflater.inflate(R.layout.fragment_list_alphabets, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activity?.setTitle("Alphabets")
        (activity as MainActivity).supportActionBar?.setDisplayShowHomeEnabled(false)
        (activity as MainActivity).supportActionBar?.setDisplayHomeAsUpEnabled(false)
        dataInit()
        recyclerView = view.findViewById(R.id.recyclerViewAlphabets)
        recyclerView.setHasFixedSize(true)
        layoutManager = GridLayoutManager(context, 3)
        recyclerView.layoutManager = layoutManager
        alphabetsAdapter = AlphabetsAdapter(alphabetsList,layoutManager)
        alphabetsAdapter.onClick = {
            viewModel.saveClickedAlphabet(it)
            var activity : AppCompatActivity = context as AppCompatActivity
            val wordsFragment = ListWordsFragment()
            activity.supportFragmentManager.beginTransaction().replace(R.id.fragmentContainer,wordsFragment).addToBackStack("frag").commit()
        }
        recyclerView.adapter = alphabetsAdapter
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        activity?.menuInflater?.inflate(R.menu.toolbar_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.btnChangeLayout -> {
                if (layoutManager.spanCount == 3) {
                    layoutManager.spanCount = 1
                    item.setIcon(R.drawable.ic_grid).setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS)
                }else{
                    layoutManager.spanCount = 3
                    item.setIcon(R.drawable.ic_list).setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS)
                }
                alphabetsAdapter.notifyItemRangeChanged(0,alphabetsAdapter.itemCount)
            }else -> return false
        }
        return true
    }

    private fun dataInit() {
        alphabetsList = arrayListOf(Alphabets("A"), Alphabets("B"),Alphabets("C"),
            Alphabets("D"), Alphabets("E"),Alphabets("F"),Alphabets("G"),Alphabets("H"),
            Alphabets("I"),Alphabets("J"),Alphabets("K"),Alphabets("L"))
    }
}