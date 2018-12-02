package com.zapir.ariadne.presenter.main

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.zapir.ariadne.model.repositories.ImagesRepository
import com.zapir.ariadne.presenter.search.WaypointsState
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class MainViewModel(
        private val repository: ImagesRepository
): ViewModel() {

    var state = MutableLiveData<MainState>()
    var currentFloor = 0
    set(value) {
        getFloorUrl(value)
        field = value
    }

    init {
        getFloorUrl(0)
    }

    fun getFloorUrl(id: Int) = repository.getImageUrl(id)
            .doOnSubscribe {
                state.postValue(MainState.LoadingState(true))
            }
            .doAfterTerminate {
                state.postValue(MainState.LoadingState(false))
            }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                    {
                        state.postValue(MainState.SuccessState(it))
                    },
                    {
                        state.postValue(MainState.FailState(it))

                    }
            )


}