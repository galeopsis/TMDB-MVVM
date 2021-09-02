package com.galeopsis.mvvmtmdbmoviefinder

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.galeopsis.mvvmtmdbmoviefinder.view.Communicator
import com.galeopsis.mvvmtmdbmoviefinder.view.MovieDetailsFragment
import com.galeopsis.mvvmtmdbmoviefinder.view.MovieSearchFragment

class MainActivity : AppCompatActivity(), Communicator {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, MovieSearchFragment.newInstance())
                .commitNow()
        }
    }

    override fun passData(position: Int) {
        val bundle = Bundle()
        bundle.putInt("POS", position)

        val transaction = this.supportFragmentManager.beginTransaction()
        val fragmentB = MovieDetailsFragment()
        fragmentB.arguments = bundle
        transaction.replace(R.id.container, fragmentB)
        transaction.commit()
    }
}
