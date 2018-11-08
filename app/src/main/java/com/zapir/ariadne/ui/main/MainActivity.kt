package com.zapir.ariadne.ui.main

//import com.zapir.ariadne.ui.map.MapView
//import com.zapir.ariadne.ui.map.MapLayer
import android.app.SearchManager
import android.content.Context
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import com.zapir.ariadne.R
import com.zapir.ariadne.ui.search.SearchFragment
import android.support.v4.view.MenuItemCompat
import android.support.v4.view.MenuItemCompat.setOnActionExpandListener
import android.support.v7.widget.SearchView
import com.zapir.ariadne.presenter.di.uiModule
import org.koin.android.ext.android.getKoin
import org.koin.android.ext.android.startKoin


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        supportFragmentManager
                .beginTransaction()
                .replace(R.id.container, MainFragment())
                .commitNow()

        startKoin(context = applicationContext, modules = listOf(uiModule))
    }

}
