package com.zapir.ariadne.model.remote

import com.zapir.ariadne.model.entity.Building
import com.zapir.ariadne.model.entity.Static
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface RouterService {

    @GET("/building/")
    fun getBuilding(): Single<Building>

    @GET("/static/")
    fun getFloorUrls(): Single<Static>

    @GET("/route/")
    fun getRoute(@Query("from") from: Int,
                 @Query("to") to: Int): Single<Building>

}
