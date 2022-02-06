package com.example.downloadfiles.ui

import android.Manifest
import android.app.DownloadManager
import android.content.Context
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.widget.Toast
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.downloadfiles.R
import com.example.downloadfiles.adapters.DownloadClickListener
import com.example.downloadfiles.adapters.MovieAdapter
import com.example.downloadfiles.databinding.ActivityMainBinding
import com.example.downloadfiles.model.MovieResponse
import com.example.downloadfiles.model.MovieResponseItem
import com.example.downloadfiles.viewmodels.HomeViewModel
import dagger.android.support.DaggerAppCompatActivity
import javax.inject.Inject

class MainActivity : DaggerAppCompatActivity(), DownloadClickListener {

    private lateinit var downloadManager: DownloadManager

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private val homeViewModel: HomeViewModel by viewModels {
        viewModelFactory
    }

    private val REQUEST_CODE = 100

    private lateinit var binding: ActivityMainBinding
    private lateinit var movieAdapter: MovieAdapter

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        movieAdapter = MovieAdapter(this)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(
                    arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                    REQUEST_CODE
                )
            }
        }


        homeViewModel.getMoviesFromServer()
        homeViewModel.moviesResponse.observe(this) {
            when (it) {
                is MovieResponse -> {

                    binding.movieAdapter?.submitList(it.movieResponse)
                }
                is Throwable -> {
                    Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()

                }
            }
        }
        binding.movieAdapter = movieAdapter

        downloadManager = getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
    }


    override fun onDownloadClicked(url: String, name: String) {
        val request = DownloadManager.Request(Uri.parse(url))
        request.setTitle(name)
        request.setDescription("Downloading$name")
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, name)
        downloadManager.enqueue(request)

    }
}