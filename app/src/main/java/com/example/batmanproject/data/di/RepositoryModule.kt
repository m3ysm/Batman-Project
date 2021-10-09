package com.example.batmanproject.data.di

import com.example.batmanproject.data.repository.MovieRepository
import org.koin.dsl.module

val repositoryModule = module {
    single { MovieRepository(get()) }
}