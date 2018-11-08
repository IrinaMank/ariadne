package com.zapir.ariadne.presenter.findway

import android.arch.lifecycle.ViewModel
import com.zapir.ariadne.model.entity.Point
import com.zapir.ariadne.ui.route.RouteFragment

class FindWayViewModel: ViewModel() {

    fun onFindWayBtnClicked(from: Point, to: Point) {
        //передача точек в след фргамент
        val intent = RouteFragment.startIntent(from.name!!, to.name!!)//id instead of point
        //это во фрагменте
    }
}