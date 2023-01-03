package com.crexative.calorietrackerapp.navigation

import androidx.navigation.NavController
import com.crexative.core.util.UiEvent

fun NavController.navigate(event: UiEvent.Navigate) {
    this.navigate(event.route)
}