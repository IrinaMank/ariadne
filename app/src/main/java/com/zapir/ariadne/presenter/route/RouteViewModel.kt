package com.zapir.ariadne.presenter.route

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.zapir.ariadne.model.entity.Waypoint
import com.zapir.ariadne.model.interactors.RouteInteractor
import com.zapir.ariadne.model.repositories.ImagesRepository
import com.zapir.ariadne.model.repositories.PointsRepository
import com.zapir.ariadne.presenter.main.MainState
import com.zapir.ariadne.presenter.search.WaypointsState
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.BehaviorSubject


class RouteViewModel(
        private val interactor: PointsRepository,
        private val repository: ImagesRepository
): ViewModel() {

    val route = BehaviorSubject.create<List<Waypoint>>()// паттерн обсервер. Когда постим сюда новые
    // данные, они рассылаются по подписчикам
    var state = MutableLiveData<WaypointsState>()
    var stateCon =BehaviorSubject.create<WaypointsState>()
    var stateImages = MutableLiveData<MainState>()

    var currentFloor = 0
        set(value) {
            getFloorUrl(value)
            field = value
        }

    init {
       // getFloorUrl(0)
    }

    fun getFloorUrl(id: Int) = repository.getImageUrl(id)
            .doOnSubscribe {
                stateImages.postValue(MainState.LoadingState(true))
            }
            .doAfterTerminate {
                stateImages.postValue(MainState.LoadingState(false))
            }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                    {
                        stateImages.postValue(MainState.SuccessState(it))
                    },
                    {
                        stateImages.postValue(MainState.FailState(it))

                    }
            )


    fun createRoute(from: Int, to: Int) {
        val result = interactor.getRoute(from, to)
                .doOnSubscribe {
                    state.postValue(WaypointsState.LoadingState(true))
                }
                .doAfterTerminate {
                    state.postValue(WaypointsState.LoadingState(false))
                }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        {
                            state.postValue(WaypointsState.SuccessState(it.points))
                        },
                        {
                            state.postValue(WaypointsState.FailState(it))

                        }
                )
    }

    fun pointFromFloor(id: Int) {
        val result = interactor.getPointsOnFloor(id)
                .doOnSubscribe {
                    state.postValue(WaypointsState.LoadingState(true))
                }
                .doAfterTerminate {
                    state.postValue(WaypointsState.LoadingState(false))
                }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        {
                            for (item in it) {
                                debugConn(item, item.id)
                            }
                            state.postValue(WaypointsState.SuccessState(it))
                        },
                        {
                            state.postValue(WaypointsState.FailState(it))

                        }
                )
    }


    fun debugConn(curr: Waypoint, id: Int) {
        val result = interactor.getPointsById(id)
//                .doOnSubscribe {
//                    stateCon.onNext(WaypointsState.LoadingState(true))
//                }
//                .doAfterTerminate {
//                    stateCon.onNext(WaypointsState.LoadingState(false))
//                }
                .map {
                    it.relatedPoints }
                .flatMapObservable { list-> Observable.fromIterable(list)}       // 1 to N (цикл), теперь это Observable
                .flatMapSingle { profile-> interactor.getPointsById(profile) }
                .toList()

                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        {
                            it.add(curr)
                            stateCon.onNext(WaypointsState.SuccessState(it))
                        },
                        {
                            stateCon.onNext(WaypointsState.FailState(it))

                        }
                )
    }


}