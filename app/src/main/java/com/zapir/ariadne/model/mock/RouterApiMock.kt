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
                        Point("into the hell", 50f, 100f),
                        Point("na piki toche", 250f, 100f),
                        Point("k bekareviioy", 450f, 100f),
                        Point("into the hell", 50f,  450f),
                        Point("na piki tocie", 250f, 450f),
                        Point("k bekarejjvoy", 450f, 450f),
                        Point("variantooooic", 250f, 250f)

                ))
                //.subscribeOn(Schedulers.io())
                //.observeOn(schedulers.ui())
    }
}