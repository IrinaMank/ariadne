package com.zapir.ariadne.model

import com.zapir.ariadne.model.entity.TextJson
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface RouterService {

    //ToDo
    @GET("test")
    fun postText(
            @Query("q") query: String
    ): Single<TextJson>

}
