//@file:Suppress("DeferredResultUnused")

package com.rich.momsnote.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.rich.momsnote.database.Notes
import com.rich.momsnote.database.NotesTakingDB
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

@Suppress("DeferredResultUnused")
class NotesViewModel(app:Application) : AndroidViewModel(app) {
    private var allNotes : MutableLiveData<List<Notes>?> = MutableLiveData()

    init {
        getAllNotes()
    }

    fun getAllNotesObserver() : MutableLiveData<List<Notes>?> {
        return allNotes
    }

    private fun getAllNotes(){
        GlobalScope.launch {
            val notesDAO = NotesTakingDB.getInstance((getApplication()))!!.notesDao()
            val listNotes = notesDAO.getAllNotes()
            allNotes.postValue(listNotes)
        }
    }

    fun insertNotes(notes: Notes){
        GlobalScope.async {
            val notesDAO = NotesTakingDB.getInstance((getApplication()))!!.notesDao()
            notesDAO.insertNote(notes)
            getAllNotes()
        }
    }

    fun deleteNotes(notes : Notes){
        GlobalScope.launch {
            val notesDAO = NotesTakingDB.getInstance((getApplication()))!!.notesDao()
            notesDAO.deleteNote(notes)
            getAllNotes()
        }
    }

    fun updateNotes(notes: Notes){
        GlobalScope.async {
            val notesDAO = NotesTakingDB.getInstance((getApplication()))!!.notesDao()
            notesDAO.updateNote(notes)
            getAllNotes()
        }
    }
}