package com.example.batmanproject.ui.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.example.batmanproject.data.model.movies.GetMovieDetailsResponseModel
import com.example.batmanproject.databinding.FragmentDetailsBinding
import com.example.batmanproject.ui.base.BaseFragment
import com.example.batmanproject.util.extensions.initToolbar
import com.example.batmanproject.util.extensions.visible
import org.koin.android.ext.android.inject

class DetailsFragment : BaseFragment() {

    private val viewModel: DetailsViewModel by inject()
    private var _binding: FragmentDetailsBinding? = null
    private val binding get() = _binding!!
    private lateinit var details: GetMovieDetailsResponseModel

    companion object {
        private const val DETAILS = "details"
        fun newInstance(details: GetMovieDetailsResponseModel): Bundle {
            val bundle = Bundle()
            bundle.putSerializable(DETAILS, details)
            return bundle
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            details = it.getSerializable(DETAILS) as GetMovieDetailsResponseModel
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
    }

    override fun initUiComponents() {
        super.addTranslucentStatusFlag()
        visibleProgressBar()
        initToolbar()
        fillViews()
    }

    private fun fillViews() {
        Glide.with(requireContext())
            .load(details.poster)
            .into(binding.imageViewDetailsFragmentBackImage)
        Glide.with(requireContext())
            .load(details.poster)
            .into(binding.imageViewDetailsFragmentPoster)
        binding.textViewDetailsFragmentTitle.text = details.title
        binding.textViewDetailsFragmentImdbRate.text = details.imdbRating.toString()
        binding.textViewDetailsFragmentReleased.text = details.released
        binding.textViewDetailsFragmentDuration.text = details.runtime
        binding.textViewDetailsFragmentActors.text = details.actors
        binding.textViewDetailsFragmentDirector.text = details.director
        binding.textViewDetailsFragmentAwards.text = details.awards
        binding.textViewDetailsFragmentBoxOffice.text = details.boxOffice
        binding.textViewDetailsFragmentPlot.text = details.plot
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