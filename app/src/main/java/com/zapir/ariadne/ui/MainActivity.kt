package com.zapir.ariadne.ui

import android.os.Bundle
import com.arellomobile.mvp.MvpAppCompatActivity
import com.zapir.ariadne.R
import com.zapir.ariadne.ui.search.SearchFragment

class MainActivity : MvpAppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportFragmentManager
                .beginTransaction()
                .replace(R.id.container, SearchFragment())
                .commitNow()
    }
}
