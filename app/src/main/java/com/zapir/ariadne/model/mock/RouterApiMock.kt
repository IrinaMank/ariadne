package com.zapir.ariadne.model.mock

import com.zapir.ariadne.model.RetrofitServiceFactory
import com.zapir.ariadne.model.entity.Point
import io.reactivex.Scheduler
import io.reactivex.Single

class RouterApiMock {

    private val routerService by lazy { RetrofitServiceFactory.routerService }

    fun postText(query: String) = routerService.postText(query)

    fun getPoints(): Single<MutableList<Point>> {
        return Single
                .just(mutableListOf(
                        Point("into the hell", 5f, 10f),
                        Point("na piki toche", 25f, 10f),
                        Point("k bekareviioy", 45f, 10f),
                        Point("into the hell", 5f,  45f),
                        Point("na piki tocie", 25f, 45f),
                        Point("k bekarejjvoy", 45f, 45f),
                        Point("variantooooic", 25f, 25f)

                ))
                //.subscribeOn(Schedulers.io())
                //.observeOn(schedulers.ui())
    }
}