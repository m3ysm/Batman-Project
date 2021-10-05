package com.example.batmanproject.data.di

import com.example.batmanproject.ui.home.domain.GetMoviesUseCase
import org.koin.dsl.module

val useCaseModule = module {
    single { GetMoviesUseCase(get()) }
}