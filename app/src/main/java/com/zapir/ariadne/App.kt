package com.zapir.ariadne

import android.app.Application
import com.zapir.ariadne.presenter.di.modelModule
import com.zapir.ariadne.presenter.di.uiModule
import org.koin.android.ext.android.startKoin

class App: Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin(context = applicationContext, modules = listOf(modelModule, uiModule))
        //разрешаем все зависимости
    }
}