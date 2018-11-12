package com.zapir.ariadne.model.remote

import com.zapir.ariadne.model.entity.Point
import com.zapir.ariadne.model.entity.TextJson
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface RouterService {

    //ToDo: add real queries
    @GET("test")
    fun postText(
            @Query("q") query: String
    ): Single<TextJson>

    @GET("points")
    fun getPoints(): Single<List<Point>>
}
