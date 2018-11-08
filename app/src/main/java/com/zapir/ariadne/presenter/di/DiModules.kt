package com.zapir.ariadne.presenter.di

import com.zapir.ariadne.presenter.findway.FindWayViewModel
import com.zapir.ariadne.presenter.main.MainViewModel
import com.zapir.ariadne.presenter.route.RouteViewModel
import com.zapir.ariadne.presenter.search.SearchViewModel
import org.koin.android.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.module


val modelModule = module {

}
val uiModule = module {
    viewModel { SearchViewModel() }
    viewModel { FindWayViewModel() }
    viewModel { MainViewModel() }
    viewModel { RouteViewModel() }
}
