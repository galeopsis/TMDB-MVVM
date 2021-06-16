package com.galeopsis.mvvmtmdbmoviefinder

import android.os.Bundle
import android.view.View
import android.view.View.*
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.galeopsis.mvvmtmdbmoviefinder.app.di.IMAGE_BASE_URL
import com.galeopsis.mvvmtmdbmoviefinder.databinding.ActivityMainBinding
import com.galeopsis.mvvmtmdbmoviefinder.utils.LoadingState
import com.galeopsis.mvvmtmdbmoviefinder.viewmodel.MainViewModel
import com.google.android.material.snackbar.Snackbar
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    private val mainViewModel by viewModel<MainViewModel>()
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        loadMovie()

        mainViewModel.loadingState.observe(this, Observer {
            when (it.status) {

                LoadingState.Status.FAILED ->
                    binding.mainView.showSnackBar(
                        getString(R.string.error),
                        getString(R.string.reload),
                        { loadMovie() })

                LoadingState.Status.RUNNING ->
                    with(binding) {
                        loadingLayout.visibility = VISIBLE
                        movieTitle.visibility = INVISIBLE
                        releaseDate.visibility = INVISIBLE
                        movieOverview.visibility = INVISIBLE
                        movieRating.visibility = INVISIBLE
                    }

                LoadingState.Status.SUCCESS ->
                    with(binding) {
                        loadingLayout.visibility = GONE
                        movieTitle.visibility = VISIBLE
                        releaseDate.visibility = VISIBLE
                        movieOverview.visibility = VISIBLE
                        movieRating.visibility = VISIBLE
                    }

            }
        })
    }

    private fun loadMovie() {
        mainViewModel.data.observe(this, Observer {
            it.forEach { movieData ->
                setPoster(movieData.poster_path)
                with(binding) {
                    movieTitle.text = movieData.title
                    releaseDate.text = movieData.release_date
                    movieOverview.text = movieData.overview
                    movieRating.text = movieData.vote_average.toString()
                }
            }
        })
    }

    private fun setPoster(posterPath: String) {
        val poster = IMAGE_BASE_URL + posterPath

        Glide.with(this)
            .load(poster)
            .into(binding.imageView)
    }

    private fun View.showSnackBar(
        text: String,
        actionText: String,
        action: (View) -> Unit,
        length: Int = Snackbar.LENGTH_INDEFINITE
    ) {
        Snackbar.make(this, text, length).setAction(actionText, action).show()
    }
}
