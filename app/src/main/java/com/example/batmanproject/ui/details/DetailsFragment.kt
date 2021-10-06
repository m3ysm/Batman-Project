package com.example.batmanproject.ui.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.example.batmanproject.R
import com.example.batmanproject.data.exception.common.NetworkException
import com.example.batmanproject.data.model.Status
import com.example.batmanproject.data.model.movies.GetMovieDetailsResponseModel
import com.example.batmanproject.data.model.progressbar.ProgressBarStatus
import com.example.batmanproject.databinding.FragmentDetailsBinding
import com.example.batmanproject.ui.base.BaseFragment
import com.example.batmanproject.util.extensions.initToolbar
import com.example.batmanproject.util.extensions.showSnack
import com.example.batmanproject.util.extensions.visible
import org.koin.android.ext.android.inject

class DetailsFragment : BaseFragment() {

    private val viewModel: DetailsViewModel by inject()
    private var _binding: FragmentDetailsBinding? = null
    private val binding get() = _binding!!
    private var movieId = ""

    companion object {
        private const val MOVIE_ID = "movie_id"
        fun newInstance(movieId: String): Bundle {
            val bundle = Bundle()
            bundle.putString(MOVIE_ID, movieId)
            return bundle
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            movieId = it.getString(MOVIE_ID, "")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun initListeners() {
    }

    override fun initObservers() {
        observeGetMovieDetails()
    }

    private fun observeGetMovieDetails() {
        viewModel.getMovieDetailsLiveData.observe(viewLifecycleOwner, Observer { it ->
            when (it.status) {
                Status.LOADING -> {
                    binding.progressLayoutDetailsFragment.setStatus(ProgressBarStatus.LOADING)
                }
                Status.SUCCESS -> {
                    binding.progressLayoutDetailsFragment.setStatus(ProgressBarStatus.DONE)
                    it.data?.let {
                        fillViews(it)
                    }
                }
                Status.FAILED -> {
                    binding.progressLayoutDetailsFragment.setStatus(ProgressBarStatus.DONE)
                    it.throwable?.let { throwable ->
                        when (throwable) {
                            is NetworkException -> {
                                showSnack(requireView(), getString(R.string.all_error))
                            }
                        }
                    }
                    it.message?.let {
                        showSnack(requireView(), it)
                    }
                }
            }
        })
    }

    private fun fillViews(it: GetMovieDetailsResponseModel) {
        Glide.with(requireContext())
            .load(it.poster)
            .into(binding.imageViewDetailsFragmentBackImage)
        Glide.with(requireContext())
            .load(it.poster)
            .into(binding.imageViewDetailsFragmentPoster)
        binding.textViewDetailsFragmentTitle.text = it.title
        binding.textViewDetailsFragmentImdbRate.text = it.imdbRating.toString()
        binding.textViewDetailsFragmentReleased.text = it.released
        binding.textViewDetailsFragmentDuration.text = it.runtime
        binding.textViewDetailsFragmentActors.text = it.actors
        binding.textViewDetailsFragmentDirector.text = it.director
        binding.textViewDetailsFragmentAwards.text = it.awards
        binding.textViewDetailsFragmentBoxOffice.text = it.boxOffice
        binding.textViewDetailsFragmentPlot.text = it.plot
    }

    override fun initUiComponents() {
        super.addTranslucentStatusFlag()
        visibleProgressBar()
        initToolbar()
        getDetails()
    }

    private fun getDetails() {
        viewModel.getMovieDetails(movieId)
    }

    private fun initToolbar() {
        initToolbar(binding.mainToolbarDetailsFragment)
    }

    private fun visibleProgressBar() {
        binding.progressLayoutDetailsFragment.visible()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}