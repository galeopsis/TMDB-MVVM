package com.galeopsis.mvvmtmdbmoviefinder.view

import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.galeopsis.mvvmtmdbmoviefinder.R
import com.galeopsis.mvvmtmdbmoviefinder.databinding.MovieSearchFragmentBinding
import com.galeopsis.mvvmtmdbmoviefinder.model.entity.Movies
import com.galeopsis.mvvmtmdbmoviefinder.utils.LoadingState
import com.galeopsis.mvvmtmdbmoviefinder.viewmodel.MainViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class MovieSearchFragment : Fragment() {

    companion object {
        fun newInstance() = MovieSearchFragment()
    }

    private lateinit var communicator: Communicator
    private val mainViewModel by viewModel<MainViewModel>()
    private var _binding: MovieSearchFragmentBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = MovieSearchFragmentBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerView: RecyclerView = binding.MyRecyclerView
        recyclerView.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)

        recyclerView.addOnItemTouchListener(
            RecyclerItemClickListener(
                this.requireActivity(),
                object : RecyclerItemClickListener.OnItemClickListener {
                    override fun onItemClick(view: View, position: Int) {
                        communicator = activity as Communicator
                        communicator.passData(position)
                        goToDetailsFragment()
                    }
                })
        )

        setHasOptionsMenu(true)

        val movies = ArrayList<Movies>()
        movies.clear()

        mainViewModel.data.observe(viewLifecycleOwner, {
            it.forEach { movieData ->
                movies.add(movieData)

                val adapter = RecycleViewAdapter(movies)
                recyclerView.adapter = adapter
                adapter.notifyDataSetChanged()
            }
        })

        mainViewModel.loadingState.observe(viewLifecycleOwner, {
            when (it.status) {

                LoadingState.Status.FAILED ->
                    Toast.makeText(context, it.msg, Toast.LENGTH_SHORT).show()

                LoadingState.Status.RUNNING ->
                    with(binding) {
                        loadingLayout.visibility = View.VISIBLE
                    }

                LoadingState.Status.SUCCESS ->
                    with(binding) {
                        loadingLayout.visibility = View.GONE
                    }
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_search -> {
                Log.d("API123", "done")
                true
            }
            R.id.action_adult -> {
                Log.d("API123", "done")
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun goToDetailsFragment() {
        activity?.supportFragmentManager?.beginTransaction()
            ?.replace(R.id.container, MovieDetailsFragment.newInstance())
            ?.addToBackStack(null)
            ?.commit()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}