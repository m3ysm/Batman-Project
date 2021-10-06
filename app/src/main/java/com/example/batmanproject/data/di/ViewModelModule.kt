package com.example.batmanproject.data.di

import com.example.batmanproject.ui.details.DetailsViewModel
import com.example.batmanproject.ui.home.HomeViewModel
import org.koin.dsl.module

val viewModelModule = module {
    single { HomeViewModel() }
    single { DetailsViewModel() }
}