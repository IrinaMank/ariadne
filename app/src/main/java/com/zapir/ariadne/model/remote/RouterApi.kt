package com.zapir.ariadne.model.remote

class RouterApi {

    private val routerService by lazy { RetrofitServiceFactory.routerService }

//    fun getPoint() = routerService.getPoint()
//
//    fun getPoints() = routerService.getPoints()

    fun getBuilding() = routerService.getBuilding()
}