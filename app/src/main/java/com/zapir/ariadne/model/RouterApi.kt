package com.zapir.ariadne.model

class RouterApi {

    private val routerService by lazy { RetrofitServiceFactory.routerService }

    fun postText(query: String) = routerService.postText(query)
}