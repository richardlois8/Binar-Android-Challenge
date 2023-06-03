@file:Suppress("PrivatePropertyName", "RedundantNullableReturnType", "RedundantNullableReturnType",
    "RedundantNullableReturnType"
)

package com.rich.movielistapi.fragment

import android.content.SharedPreferences
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import androidx.viewpager2.widget.ViewPager2
import com.rich.movielistapi.adapter.NowPlayingAdapter
import com.rich.movielistapi.adapter.PosterAdapter
import com.rich.movielistapi.databinding.FragmentMovieListBinding
import com.rich.movielistapi.response.MovieResult
import com.rich.movielistapi.response.NowPlayingMovieItem
import com.rich.movielistapi.viewmodel.MovieViewModel

class MovieListFragment : Fragment() {
    private lateinit var binding : FragmentMovieListBinding
    private lateinit var movieVM : MovieViewModel
    private lateinit var handler: Handler
    private lateinit var sharedPref: SharedPreferences
    private lateinit var editor: SharedPreferences.Editor
    private val PREF_NAME = "dataUser"

    companion object;

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentMovieListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        movieVM = ViewModelProvider(this).get(MovieViewModel::class.java)
        sharedPref = requireActivity().getSharedPreferences(PREF_NAME, 0)
        editor = sharedPref.edit()

        setUsername()
        gotoProfile()
        getPopularMovies()
        getNowPlayingMovies()
        setupTransformer()
        binding.posterViewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                handler.removeCallbacks(runnable)
                handler.postDelayed(runnable, 3000)
            }
        })

        binding.imgProfile.setOnClickListener {
            gotoProfile()
        }
    }

    private fun gotoProfile() {
        binding.imgProfile.setOnClickListener {
            val id = sharedPref.getString("id","")
            val action = MovieListFragmentDirections.actionMovieListFragmentToProfileFragment(id!!)
            findNavController().navigate(action)
        }
    }

    private fun setUsername() {
        binding.username = sharedPref.getString("username", "")
    }

    override fun onPause() {
        super.onPause()
        handler.removeCallbacks(runnable)
    }

    override fun onResume() {
        super.onResume()
        handler.postDelayed(runnable, 3000)
    }

    private fun setupTransformer() {
        val transformer = CompositePageTransformer()
        transformer.addTransformer(MarginPageTransformer(40))
        transformer.addTransformer { page, position ->
            val r = 1 - kotlin.math.abs(position)
            page.scaleY = 0.85f + r * 0.14f
        }

        binding.posterViewPager.setPageTransformer(transformer)
    }

    private val runnable = Runnable {
        binding.posterViewPager.currentItem = binding.posterViewPager.currentItem + 1
    }

    private fun getPopularMovies() {
        handler = Handler(Looper.myLooper()!!)
        showLoading(true)
        movieVM.callGetPopularMovieApi()
        movieVM.getLDMovie().observe(viewLifecycleOwner){
            if (it != null) {
                Log.d("RESULT",it.toString())
                showLoading(false)
                showPopularMovies(it)
            }
        }
    }

    private fun showPopularMovies(data: List<MovieResult>) {
        val adapter = PosterAdapter(data,binding.posterViewPager)
        binding.posterViewPager.adapter = adapter
        adapter.onClick = {
            gotoDetailMovie(it.id!!)
        }
        binding.posterViewPager.offscreenPageLimit = 2
        binding.posterViewPager.clipToPadding = false
        binding.posterViewPager.clipChildren = false
        binding.posterViewPager.getChildAt(0).overScrollMode = RecyclerView.OVER_SCROLL_NEVER
    }

    private fun getNowPlayingMovies(){
        movieVM.callGetNowPlayingMovie()
        movieVM.observerGetNowPlayingMovie().observe(viewLifecycleOwner){
            if (it != null) {
                showLoading(false)
                showNowPlayingMovies(it)
            }
        }
    }

    private fun showNowPlayingMovies(data : List<NowPlayingMovieItem>) {
        val nowPlayAdapter = NowPlayingAdapter(data)
        binding.recViewNowPlaying.adapter = nowPlayAdapter
        val layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        val itemDecor = DividerItemDecoration(requireContext(), layoutManager.orientation)
        binding.recViewNowPlaying.layoutManager = layoutManager
        binding.recViewNowPlaying.addItemDecoration(itemDecor)
        nowPlayAdapter.onClick = {
            gotoDetailMovie(it.id!!)
        }
    }

    private fun gotoDetailMovie(idMovie : Int) {
        val action = MovieListFragmentDirections.actionMovieListFragmentToDetailMovieFragment(idMovie)
        findNavController().navigate(action)
    }

    private fun showLoading(isLoading : Boolean){
        if(isLoading){
            binding.shimmerLayout.visibility = View.VISIBLE
            binding.shimmerNowPlayLayout.visibility = View.VISIBLE
            binding.shimmerLayout.startShimmerAnimation()
            binding.shimmerNowPlayLayout.startShimmerAnimation()
        }else{
            binding.shimmerLayout.stopShimmerAnimation()
            binding.shimmerNowPlayLayout.stopShimmerAnimation()
            binding.shimmerLayout.visibility = View.GONE
            binding.shimmerNowPlayLayout.visibility = View.GONE
        }
    }
}