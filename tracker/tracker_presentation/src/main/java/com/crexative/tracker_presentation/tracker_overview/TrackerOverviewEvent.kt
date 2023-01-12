package com.crexative.tracker_presentation.tracker_overview

import com.crexative.tracker_domain.model.TrackedFood

sealed class TrackerOverviewEvent {
    object OnNextDayClick: TrackerOverviewEvent()
    object OnPreviousDayClick: TrackerOverviewEvent()
    data class OnToggleMealClick(val meal: Meal): TrackerOverviewEvent()
    data class OnDeleteTrackedFood(val trackedFood: TrackedFood): TrackerOverviewEvent()
}
