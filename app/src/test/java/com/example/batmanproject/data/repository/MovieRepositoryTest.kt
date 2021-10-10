package com.example.batmanproject.data.repository

import com.example.batmanproject.base.BaseTest
import com.example.batmanproject.data.model.ApiResponse
import com.example.batmanproject.data.model.movies.GetMoviesResponseModel
import com.example.batmanproject.data.webservice.MovieApi
import io.reactivex.Single
import io.reactivex.observers.TestObserver
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import retrofit2.Response

class MovieRepositoryTest : BaseTest() {

    private lateinit var movieRepository: MovieRepository

    @Mock
    private lateinit var movieApi: MovieApi

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        movieRepository = MovieRepository(movieApi)
    }

    @Test
    fun basicInput_worksFine() {
        val response = Response.success(
            200, ApiResponse(
                ArrayList<GetMoviesResponseModel>(),
                100,
                true
            )
        )
        Mockito.`when`(movieApi.getMovies("Sample Data", "Sample Data"))
            .thenReturn(
                Single.just(response)
            )
        movieRepository.getMovies("Sample Data", "Sample Data")
        val observer = movieApi.getMovies("Sample Data", "Sample Data")
        val testObserver =
            TestObserver.create<Response<ApiResponse<ArrayList<GetMoviesResponseModel>>>>()
        observer.subscribe(testObserver)

        testObserver.assertSubscribed()
        testObserver.awaitCount(1)
        testObserver.assertComplete()
        testObserver.assertResult(response)
    }

    @After
    fun stop() {
        Mockito.reset(movieApi)
    }
}