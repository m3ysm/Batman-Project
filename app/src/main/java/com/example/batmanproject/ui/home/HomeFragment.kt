package com.example.batmanproject.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.batmanproject.R
import com.example.batmanproject.data.exception.common.NetworkException
import com.example.batmanproject.data.model.Status
import com.example.batmanproject.data.model.movies.GetMoviesResponseModel
import com.example.batmanproject.data.model.progressbar.ProgressBarStatus
import com.example.batmanproject.databinding.FragmentHomeBinding
import com.example.batmanproject.ui.base.BaseFragment
import com.example.batmanproject.ui.details.DetailsFragment
import com.example.batmanproject.util.extensions.initToolbar
import com.example.batmanproject.util.extensions.showSnack
import com.example.batmanproject.util.extensions.visible
import org.koin.android.ext.android.inject
import java.util.*

class HomeFragment : BaseFragment() {

    private val viewModel: HomeViewModel by inject()
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun initListeners() {
        setBottomNavigationViewListener()
    }

    private fun setBottomNavigationViewListener() {
        binding.bottomNavigationHomeFragment.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.menu_films -> {
                    showSnack(requireView(), getString(R.string.homeFragment_comingSoon))
                }
                R.id.menu_series -> {
                    showSnack(requireView(), getString(R.string.homeFragment_comingSoon))
                }
            }
            false
        }
    }

    override fun initObservers() {
        observeGetMoviesLiveData()
    }

    private fun observeGetMoviesLiveData() {
        viewModel.getMoviesLiveData.observe(viewLifecycleOwner, Observer { it ->
            when (it.status) {
                Status.LOADING -> {
                    binding.progressLayoutHomeFragment.setStatus(ProgressBarStatus.LOADING)
                }
                Status.SUCCESS -> {
                    binding.progressLayoutHomeFragment.setStatus(ProgressBarStatus.DONE)
                    it.data?.let {
                        initRecyclerView(it)
                    }
                }
                Status.FAILED -> {
                    binding.progressLayoutHomeFragment.setStatus(ProgressBarStatus.DONE)
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

    private fun initRecyclerView(it: ArrayList<GetMoviesResponseModel>) {
        val recyclerView = binding.recyclerViewHomeFragment
        val adapter = HomeRecyclerViewAdapter(it, onItemClicked = {
            val bundle = DetailsFragment.newInstance(it)
            findNavController().navigate(R.id.action_homeFragment_to_detailsFragment, bundle)
        })
        recyclerView.hasFixedSize()
        recyclerView.adapter = adapter
    }

    override fun initUiComponents() {
        super.addTranslucentStatusFlag()
        visibleProgressbar()
        initToolbar()
        getMovies()
        setHomePageSelected()

    }

    private fun setHomePageSelected() {
        binding.bottomNavigationHomeFragment.selectedItemId = R.id.menu_homePage
    }

    private fun getMovies() {
        viewModel.getMovies()
    }

    private fun initToolbar() {
        initToolbar(binding.mainToolbarHomeFragment)
    }

    private fun visibleProgressbar() {
        binding.progressLayoutHomeFragment.visible()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}