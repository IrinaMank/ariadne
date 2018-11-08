package com.zapir.ariadne.presenter.di

import com.zapir.ariadne.model.interactors.RouteInteractor
import com.zapir.ariadne.model.repositories.PointsRepository
import com.zapir.ariadne.presenter.main.MainViewModel
import com.zapir.ariadne.presenter.route.RouteViewModel
import com.zapir.ariadne.presenter.search.SearchViewModel
import org.koin.android.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.module

//Внедрение зависимости. Очень простая либа. Если вам нужно создать объект, прописываете его здесь.
val modelModule = module {

    single { PointsRepository() }
    single { RouteInteractor(get()) }
}
val uiModule = module {
    viewModel { SearchViewModel() }
    viewModel { MainViewModel() }
    viewModel { RouteViewModel(get()) }
}
