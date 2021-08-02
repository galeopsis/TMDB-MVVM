package com.galeopsis.mvvmtmdbmoviefinder.view

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.galeopsis.mvvmtmdbmoviefinder.R
import com.galeopsis.mvvmtmdbmoviefinder.app.di.IMAGE_BASE_URL
import com.galeopsis.mvvmtmdbmoviefinder.databinding.MainFragmentBinding
import com.galeopsis.mvvmtmdbmoviefinder.model.entity.Movies
import com.galeopsis.mvvmtmdbmoviefinder.viewmodel.MainViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class MovieDetailsFragment : Fragment() {

    companion object {
        fun newInstance() = MovieDetailsFragment()
    }

    private val mainViewModel by viewModel<MainViewModel>()
    private var _binding: MainFragmentBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = MainFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            goToSearchFragment()
        }
        setHasOptionsMenu(true)
        mainViewModel.data.observe(viewLifecycleOwner, {

            if (it != null) {
                setMovieDetails(it)
            }
        })
    }

    private fun setMovieDetails(it: List<Movies>) {

        val index = arguments?.getInt("POS")
        if (index != null) {
            val data = it[index]
            with(binding) {

                movieTitle.text = data.title
                movieRating.text = data.vote_average.toString()
                movieOverview.text = data.overview
                releaseDate.text = data.release_date
            }
            val posterPath = IMAGE_BASE_URL + data.poster_path

            Glide.with(this)
                .load(posterPath)
                .into(binding.imageView)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.details_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {

            R.id.action_favorite -> {
                Log.d("API123", "done")
                goToSearchFragment()
                true
            }

            R.id.action_back -> {
                Log.d("API123", "done")
                goToSearchFragment()
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun goToSearchFragment() {
        activity?.supportFragmentManager?.beginTransaction()
            ?.replace(R.id.container, MovieSearchFragment.newInstance())
            ?.commitNow()
    }
}
