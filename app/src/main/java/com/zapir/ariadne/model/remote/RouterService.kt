package com.zapir.ariadne.model.remote

import com.zapir.ariadne.model.entity.Building
import com.zapir.ariadne.model.entity.Static
import io.reactivex.Single
import retrofit2.http.GET

interface RouterService {

    @GET("/building/")
    fun getBuilding(): Single<Building>

    @GET("/static/")
    fun getFloorUrls(): Single<Static>

}
