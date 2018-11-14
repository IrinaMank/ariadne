package com.zapir.ariadne.presenter.di

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import com.zapir.ariadne.model.cache.cachesource.PointCache
import com.zapir.ariadne.model.cache.db.NstuDatabase
import com.zapir.ariadne.model.cache.db.NstuDatabase_Impl
import com.zapir.ariadne.model.interactors.RouteInteractor
import com.zapir.ariadne.model.mock.RouterApiMock
import com.zapir.ariadne.model.remote.RouterApi
import com.zapir.ariadne.model.repositories.PointsRepository
import com.zapir.ariadne.presenter.main.MainViewModel
import com.zapir.ariadne.presenter.route.RouteViewModel
import com.zapir.ariadne.presenter.search.SearchViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.android.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.module
import org.koin.experimental.builder.single

//Внедрение зависимости. Очень простая либа. Если вам нужно создать объект, прописываете его здесь.
val modelModule = module {
    single { RouterApi() }
    single { Room.databaseBuilder(androidContext(), NstuDatabase::class.java, NstuDatabase::class.java
            .simpleName).fallbackToDestructiveMigration().build() } //development migration mode
    single { get<NstuDatabase>().pointDao() }
    single { get<NstuDatabase>().cashDao() }
    single<PointCache>()
    single { PointsRepository(RouterApi(), get()) }
    single { RouteInteractor(get()) }
}
val uiModule = module {
    viewModel { SearchViewModel(get()) }
    viewModel { MainViewModel() }
    viewModel { RouteViewModel(get()) }
}
