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
import io.reactivex.rxkotlin.zipWith
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.BehaviorSubject


class RouteViewModel(
        private val interactor: PointsRepository,
        private val repository: ImagesRepository
): ViewModel() {

    var state = MutableLiveData<RouteState>()
    var stateCon =BehaviorSubject.create<WaypointsState>()
    var stateImages = MutableLiveData<MainState>()

    private var from: Int = 0
    private var to: Int = 0

    private var route = emptyList<Waypoint>()
    private var floorImage = ""

    var currentFloor = 1
    set(value) {
        getFloorUrl(value)
        field = value
    }

    fun setDestinations(from: Int, to: Int) {
        this.from = from
        this.to = to
        createRoute(from, to)
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
                        state.postValue(RouteState.SuccessState(route.filter { it
                                .floor==currentFloor }, it))
                    },
                    {
                        stateImages.postValue(MainState.FailState(it))

                    }
            )

    fun createRoute(from: Int, to: Int) {
        val result = interactor.getRoute(from, to)
                .doOnSubscribe {
                    state.postValue(RouteState.LoadingState(true))
                }
                .doAfterTerminate {
                    state.postValue(RouteState.LoadingState(false))
                }
                .flattenAsObservable {it}
                .flatMap {
                    interactor.getPointsById(it).toObservable()
                }
                .toList()
                .zipWith(repository.getImageUrl(currentFloor))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        {
                            route = it.first
                            floorImage = it.second
                            state.postValue(RouteState.SuccessState(it.first.filter { it.floor ==
                                    currentFloor }, it
                                    .second))
                        },
                        {
                            state.postValue(RouteState.FailState(it))

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