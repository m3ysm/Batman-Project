package com.example.batmanproject.ui.home

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.batmanproject.base.BaseTest
import com.example.batmanproject.data.exception.common.NetworkException
import com.example.batmanproject.data.model.MyResponse
import com.example.batmanproject.data.model.Status
import com.example.batmanproject.data.model.movies.GetMoviesResponseModel
import com.example.batmanproject.ui.home.domain.GetMovieDetailsUseCase
import com.example.batmanproject.ui.home.domain.GetMoviesUseCase
import com.example.batmanproject.util.LiveDataTestUtil
import io.reactivex.Single
import org.junit.*
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.dsl.module
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class HomeViewModelTest : BaseTest() {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var getMoviesUseCase: GetMoviesUseCase

    @Mock
    private lateinit var getMovieDetailsUseCase: GetMovieDetailsUseCase

    private lateinit var homeViewModel: HomeViewModel

    private val modules = module {
        single { getMoviesUseCase }
        single { getMovieDetailsUseCase }
    }

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        startKoin { modules(modules) }
        homeViewModel = HomeViewModel()
    }

    @Test
    fun getMovies_callMyResponseSuccess() {

        Mockito.`when`(getMoviesUseCase.invoke("", ""))
            .thenReturn(Single.just(MyResponse.failed(NetworkException())))
        homeViewModel.getMovies(ArrayList<GetMoviesResponseModel>())
        Mockito.verify(getMoviesUseCase).invoke("", "")
        Assert.assertEquals(
            LiveDataTestUtil.getValue(homeViewModel.getMoviesLiveData).status,
            Status.SUCCESS
        )
    }

    @Test
    fun getMovies_callMyResponseFailed() {

        Mockito.`when`(getMoviesUseCase.invoke("", ""))
            .thenReturn(Single.just(MyResponse.success()))
        homeViewModel.getMovies(ArrayList<GetMoviesResponseModel>())
        Mockito.verify(getMoviesUseCase).invoke("", "")
        Assert.assertEquals(
            LiveDataTestUtil.getValue(homeViewModel.getMoviesLiveData).status,
            Status.FAILED
        )
    }

    @After
    fun tearDown() {
        stopKoin()
        Mockito.reset(getMoviesUseCase)
        Mockito.reset(getMovieDetailsUseCase)
    }
}