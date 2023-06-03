package com.rich.momsnote.fragment

import android.annotation.SuppressLint
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.rich.momsnote.R
import com.rich.momsnote.adapter.NotesAdapter
import com.rich.momsnote.database.Notes
import com.rich.momsnote.databinding.FragmentHomeBinding
import com.rich.momsnote.viewmodel.NotesViewModel

class HomeFragment : Fragment() {
    private lateinit var binding : FragmentHomeBinding
    private lateinit var notesAdapter: NotesAdapter
    private lateinit var notesVM : NotesViewModel
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
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        sharedPreferences = requireActivity().getSharedPreferences(PREFS_NAME, 0)
        editor = sharedPreferences.edit()

        setRecyclerView()
        notesVM = ViewModelProvider(requireActivity()).get(NotesViewModel::class.java)
        notesVM.getAllNotesObserver().observe(viewLifecycleOwner) {
            notesAdapter.setNotes(it as ArrayList<Notes>)
            notesAdapter.notifyDataSetChanged()
        }

        binding.btnAddNote.setOnClickListener {
            val addDialog = AddDialogFragment()
            addDialog.show(parentFragmentManager, "addDialog")
        }

        binding.toolbarHome.btnLogout.setOnClickListener {
            editor.putString("username", "")
            editor.putBoolean("isLogin",false)
            editor.apply()
            findNavController().navigate(R.id.action_homeFragment_to_loginFragment)
        }

        binding.username = sharedPreferences.getString("username","")
    }

    private fun setRecyclerView() {
        notesAdapter = NotesAdapter()
        binding.recylerView.adapter = notesAdapter
        binding.recylerView.layoutManager = GridLayoutManager(requireContext(), 2)
        notesAdapter.onDeleteclick = {
            AlertDialog.Builder(requireContext())
                .setTitle("Delete")
                .setMessage("Are you sure want to delete this note?")
                .setPositiveButton("Yes") { _, _ ->
                    notesVM.deleteNotes(it)
                }
                .setNegativeButton("No") { dialog, _ ->
                    dialog.dismiss()
                }
                .show()
        }
        notesAdapter.onEditClick = {
            val editDialog = UpdateDialogFragment()
            val bundle = Bundle()
            bundle.putParcelable("oldNote", it)
            editDialog.arguments = bundle
            editDialog.show(parentFragmentManager, "editDialog")
        }
    }
}