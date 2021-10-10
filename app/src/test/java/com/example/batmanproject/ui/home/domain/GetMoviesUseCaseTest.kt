package com.example.batmanproject.ui.home.domain

import com.example.batmanproject.base.BaseTest
import com.example.batmanproject.data.model.ApiResponse
import com.example.batmanproject.data.model.MyResponse
import com.example.batmanproject.data.model.Status
import com.example.batmanproject.data.model.movies.GetMoviesResponseModel
import com.example.batmanproject.data.repository.MovieRepository
import io.reactivex.Single
import io.reactivex.observers.TestObserver
import okhttp3.MediaType
import okhttp3.ResponseBody
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.koin.core.context.stopKoin
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import retrofit2.Response

class GetMoviesUseCaseTest : BaseTest() {

    @Mock
    private lateinit var movieRepository: MovieRepository
    @Mock
    private lateinit var getMoviesUseCase: GetMoviesUseCase

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        getMoviesUseCase = GetMoviesUseCase(movieRepository)
    }

    @Test
    fun response200_callMyResponseSuccess() {
        val response = Response.success(
            200, ApiResponse(ArrayList<GetMoviesResponseModel>(), 100, true)
        )

        Mockito.`when`(movieRepository.getMovies("Sample Data", "Sample Data"))
            .thenReturn(Single.just(response))
        val observer = getMoviesUseCase.invoke("Sample Data", "Sample Data")
        val testObserver = TestObserver.create<MyResponse<ArrayList<GetMoviesResponseModel>>>()
        observer.subscribe(testObserver)

        testObserver.awaitCount(1)
        testObserver.assertValue { it.status == Status.SUCCESS }
        testObserver.assertValue { it.throwable == null }
        testObserver.assertValue { it.message == null }
    }

    @Test
    fun response200_callMyResponseFailed() {
        val response = Response.error<ApiResponse<ArrayList<GetMoviesResponseModel>>>(
            400,
            ResponseBody.create(MediaType.parse("text/plain"), "text")
        )

        Mockito.`when`(movieRepository.getMovies("Sample Data", "Sample Data"))
            .thenReturn(Single.just(response))
        val observer = getMoviesUseCase.invoke("Sample Data", "Sample Data")
        val testObserver = TestObserver.create<MyResponse<ArrayList<GetMoviesResponseModel>>>()
        observer.subscribe(testObserver)

        testObserver.awaitCount(1)
        testObserver.assertValue { it.status == Status.FAILED }
        testObserver.assertValue { it.data == null }
        testObserver.assertValue { it.throwable == null }
    }

    @After
    fun tearDown() {
        Mockito.reset(movieRepository)
        stopKoin()
    }
}