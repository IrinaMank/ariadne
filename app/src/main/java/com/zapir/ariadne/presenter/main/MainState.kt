package com.zapir.ariadne.presenter.main

import com.zapir.ariadne.model.entity.Waypoint

sealed class MainState {
    class LoadingState(val loading: Boolean) : MainState()
    class SuccessState(val currentFloor: String) : MainState()
    class FailState(val error: Throwable) : MainState()
}