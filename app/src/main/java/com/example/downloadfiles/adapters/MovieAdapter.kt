package com.example.downloadfiles.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.downloadfiles.BR
import com.example.downloadfiles.R
import com.example.downloadfiles.databinding.MovieItemBinding
import com.example.downloadfiles.model.MovieResponseItem

class MovieAdapter(private val onDownloadClickListener: DownloadClickListener) :
    ListAdapter<MovieResponseItem, MovieAdapter.MovieHolder>(DIFF_CALLBACKS) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = MovieHolder(
        DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.movie_item,
            parent,
            false
        )
    )


    override fun onBindViewHolder(holder: MovieHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
        holder.binding.downloadFile.setOnClickListener {
            onDownloadClickListener.onDownloadClicked(item.url!!, item.name!!)

        }
    }


    inner class MovieHolder(item: MovieItemBinding) : RecyclerView.ViewHolder(item.root) {

        val binding = item
        fun bind(obj: Any) {
            binding.setVariable(BR.movie, obj)
            binding.executePendingBindings()

        }

    }

    companion object {

        public final val DIFF_CALLBACKS = object : DiffUtil.ItemCallback<MovieResponseItem>() {
            override fun areItemsTheSame(
                oldItem: MovieResponseItem,
                newItem: MovieResponseItem
            ): Boolean = oldItem.url == newItem.url

            override fun areContentsTheSame(
                oldItem: MovieResponseItem,
                newItem: MovieResponseItem
            ): Boolean = oldItem == newItem


        }
    }
}