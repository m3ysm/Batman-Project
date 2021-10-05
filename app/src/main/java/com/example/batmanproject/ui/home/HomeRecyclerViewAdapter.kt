package com.example.batmanproject.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.batmanproject.R
import com.example.batmanproject.data.model.movies.GetMoviesResponseModel
import com.example.batmanproject.databinding.ItemMoviesBinding

class HomeRecyclerViewAdapter(
    private val movies: ArrayList<GetMoviesResponseModel>,
    val onItemClicked: ((itemId: String) -> Unit)? = null
) : RecyclerView.Adapter<HomeRecyclerViewAdapter.ViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val binding = ItemMoviesBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding, onItemClicked)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindData(movies[position])
    }

    override fun getItemCount() = movies.size

    class ViewHolder(binding: ItemMoviesBinding, onItemClicked: ((itemId: String) -> Unit)?) :
        RecyclerView.ViewHolder(binding.root) {
        private val rootLayout = binding.root
        private val poster = binding.imageViewMoviesItemPoster
        private val title = binding.textViewMoviesItemTitle
        private val year = binding.textViewMoviesItemYear
        private val type = binding.textViewMoviesItemType
        private val details = binding.buttonMoviesItemDetails
        private var movieId = ""

        init {
            rootLayout.setOnClickListener {
                onItemClicked?.invoke(movieId)
            }
            details.setOnClickListener {
                onItemClicked?.invoke(movieId)
            }
        }

        fun bindData(item: GetMoviesResponseModel) {
            Glide.with(poster.context)
                .load(item.poster)
                .into(poster)
            title.text = item.title
            year.text = "${year.context.getString(R.string.homeFragment_year)} ${item.year}"
            type.text = "${year.context.getString(R.string.homeFragment_type)} ${item.type}"
            movieId = item.imdbID
        }
    }
}