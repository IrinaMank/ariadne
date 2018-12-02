package com.zapir.ariadne.model.remote

class RouterApi {

    private val routerService by lazy { RetrofitServiceFactory.routerService }

    fun getBuilding() = routerService.getBuilding()

    fun getFloorUrls() = routerService.getFloorUrls()
}