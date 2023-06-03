package com.rich.momsnote.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.rich.momsnote.database.Notes
import com.rich.momsnote.databinding.AddDialogBinding
import com.rich.momsnote.viewmodel.NotesViewModel

@Suppress("ReplaceGetOrSet")

class AddDialogFragment : androidx.fragment.app.DialogFragment() {
    private lateinit var binding : AddDialogBinding
    private lateinit var notesVM : NotesViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = AddDialogBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var test = "Halo"
        test = test.lowercase()
        notesVM = ViewModelProvider(requireActivity()).get(NotesViewModel::class.java)
        binding.btnSubmit.setOnClickListener {
            val addedNote = Notes(
                0,
                binding.etTitle.text.toString(),
                binding.etContent.text.toString()
            )
            notesVM.insertNotes(addedNote)
            dismiss()
        }
        binding.etTitle.addTextChangedListener(object : android.text.TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: android.text.Editable?) {
                Toast.makeText(requireContext(), "Title: ${binding.etTitle.text}", Toast.LENGTH_SHORT).show()
            }
        })
    }

}