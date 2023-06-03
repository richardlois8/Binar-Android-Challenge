package com.rich.challenge3

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


class ListWordsFragment() : Fragment() {
    lateinit var clickedAlphabet: Alphabets
    lateinit var listWords: ArrayList<Words>
    lateinit var wordsAdapter: WordsAdapter
    lateinit var recyclerViewWords: RecyclerView
    private val viewModel : AlphabetsViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        clickedAlphabet = viewModel.clickedAlphabet.value!!
        viewModel.clickedAlphabet.observe(viewLifecycleOwner) { alpha ->
            clickedAlphabet = alpha
        }

        return inflater.inflate(R.layout.fragment_list_words, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dataInit(clickedAlphabet.alphabetValue)
        activity?.setTitle("Words That Start With $clickedAlphabet")
        (activity as MainActivity).supportActionBar?.setDisplayShowHomeEnabled(true)
        (activity as MainActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
        recyclerViewWords = view.findViewById(R.id.recyclerViewWords)
        recyclerViewWords.setHasFixedSize(true)
        recyclerViewWords.layoutManager = LinearLayoutManager(context)
        wordsAdapter = WordsAdapter(listWords)
        recyclerViewWords.adapter = wordsAdapter
    }

    private fun dataInit(data:String) {
        listWords = when(data){
            "A" -> arrayListOf(Words("Apple"), Words("Aqua"),Words("Amerika"),
                Words("Alamanda"),Words("Ariel"),Words("Asus"),Words("Acer"))
            "B" -> arrayListOf(Words("Buku"), Words("Bola"),Words("Baju"),
                Words("Blackberry"),Words("Blender"),Words("Bakso"),Words("Bakwan"))
            "C" -> arrayListOf(Words("Coklat"), Words("Cacing"),Words("Cicak"),
                Words("Congkak"),Words("Comel"),Words("Cengeng"),Words("Celaka"))
            "D" -> arrayListOf(Words("Domba"), Words("Dadu"),Words("Dada"),
                Words("Dekstop"),Words("Dekorasi"),Words("Dekat"),Words("Deklarasi"))
            "E" -> arrayListOf(Words("Elang"), Words("Ekor"),Words("Eksotis"),
                Words("Eksplorasi"),Words("Era"),Words("Ekumene"),Words("Ekuitas"))
            "F" -> arrayListOf(Words("Fajar"), Words("Fakta"),Words("Fakir"),
                Words("Fakultas"),Words("Fakultatif"),Words("Fathin"),Words("Ferrari"))
            "G" -> arrayListOf(Words("Gajah"), Words("Gelang"),Words("Gelap"),
                Words("Gelombang"),Words("Gelora"),Words("Gendhis"),Words("Gendut"))
            "H" -> arrayListOf(Words("Hijau"), Words("Hiraukan"),Words("Hijrah"),
                Words("Hantu"),Words("Hadiah"),Words("Haru"),Words("Hendra"))
            "I" -> arrayListOf(Words("Ikan"), Words("Ibu"),Words("Istri"),
                Words("Iphone"),Words("Ipad"),Words("India"),Words("Indonesia"))
            "J" -> arrayListOf(Words("Jeruk"), Words("Jendela"),Words("Jengkol"),
                Words("Jogja"),Words("Jepang"),Words("Jawa"),Words("Jomblo"))
            "K" -> arrayListOf(Words("Kucing"), Words("Kuda"),Words("Kunci"),
                Words("Kamera"),Words("Kamar"),Words("Kapal"),Words("Kapten"))
            "L" -> arrayListOf(Words("Laptop"), Words("Lampu"),Words("Lampung"),
                Words("Lambang"),Words("Lamborghini"),Words("Lambat"),Words("Lambat"))
            else -> {arrayListOf()}
        }
    }
}