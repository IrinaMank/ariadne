package com.zapir.ariadne.model.remote

import com.zapir.ariadne.model.entity.Building
import com.zapir.ariadne.model.entity.Waypoint
import io.reactivex.Single
import retrofit2.Call
import retrofit2.Callback
import retrofit2.http.GET
import retrofit2.http.Path

interface RouterService {

    //ToDo
//    @GET("/points/2/")
//    fun getPoint(): Single<Waypoint>
//
//    @GET("/points")
//    fun getPoints(): Single<List<Waypoint>>

    @GET("/")
    fun getBuilding(): Single<Building>
}
