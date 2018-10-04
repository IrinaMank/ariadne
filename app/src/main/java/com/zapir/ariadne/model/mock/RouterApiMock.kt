package com.zapir.ariadne.model.mock

import com.zapir.ariadne.model.RetrofitServiceFactory
import com.zapir.ariadne.model.entity.Point
import io.reactivex.Scheduler
import io.reactivex.Single

class RouterApiMock {

    private val routerService by lazy { RetrofitServiceFactory.routerService }

    fun postText(query: String) = routerService.postText(query)

    fun getPoints(): Single<List<Point>> {
        return Single
                .just(listOf(Point(name = "into the hell"), Point(name = "na piki tochonie"), Point
                (name = "k bekarevoy"), Point(name = "variantic")))
                //.subscribeOn(Schedulers.io())
                //.observeOn(schedulers.ui())
    }
}