package com.zapir.ariadne.model.remote

class RouterApi {

    private val routerService by lazy { RetrofitServiceFactory.routerService }

    fun postText(query: String) = routerService.postText(query)//

    fun getPoints() = routerService.getPoints()
}