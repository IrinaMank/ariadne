package com.zapir.ariadne.presenter.search

import com.zapir.ariadne.model.entity.Waypoint

sealed class WaypointsState {
    class LoadingState(val loading: Boolean) : WaypointsState()
    class SuccessState(val list: List<Waypoint>) : WaypointsState()
    class FailState(val error: Throwable) : WaypointsState()
}