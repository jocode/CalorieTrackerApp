package com.crexative.tracker_domain.use_case

import com.crexative.core.domain.model.ActivityLevel
import com.crexative.core.domain.model.Gender
import com.crexative.core.domain.model.GoalType
import com.crexative.core.domain.model.UserInfo
import com.crexative.core.domain.preferences.Preferences
import com.crexative.tracker_domain.model.MealType
import com.crexative.tracker_domain.model.TrackedFood
import com.google.common.truth.Truth.assertThat
import io.mockk.every
import io.mockk.mockk
import org.junit.Before
import org.junit.Test
import java.time.LocalDate
import kotlin.random.Random

class CalculateMealNutrientsTest {

    private lateinit var calculateMealNutrients: CalculateMealNutrients

    @Before
    fun setUp() {
        val preferences = mockk<Preferences>(relaxed = true)
        every { preferences.loadUserInfo() } returns UserInfo(
            gender = Gender.Male,
            age = 20,
            weight = 70f,
            height = 170,
            activityLevel = ActivityLevel.Medium,
            goalType = GoalType.KeepWeight,
            carbRatio = 0.4f,
            proteinRatio = 0.3f,
            fatRatio = 0.3f
        )
        calculateMealNutrients = CalculateMealNutrients(preferences)
    }

    @Test
    fun `Calories for breakfast properly calculated`() {
        // Given
        val trackedFoods = (1..30).map {
            TrackedFood(
                name = "name",
                carbs = Random.nextInt(100),
                protein = Random.nextInt(100),
                fat = Random.nextInt(100),
                mealType = MealType.fromString(
                    listOf("breakfast", "lunch", "dinner", "snack").random()
                ),
                imageUrl = null,
                amount = 100,
                date = LocalDate.now(),
                calories = Random.nextInt(2000)
            )
        }

        // When
        val result = calculateMealNutrients(trackedFoods)

        val breakFastCalories = result.mealNutrients.values
            .filter { it.mealType is MealType.Breakfast }
            .sumOf { it.calories }

        val expectedCalories = trackedFoods
            .filter { it.mealType is MealType.Breakfast }
            .sumOf { it.calories }

        // Then
        assertThat(breakFastCalories).isEqualTo(expectedCalories)
    }

    @Test
    fun `Calories for lunch properly calculated`() {
        // Given
        val trackedFoods = (1..30).map {
            TrackedFood(
                name = "name",
                carbs = Random.nextInt(100),
                protein = Random.nextInt(100),
                fat = Random.nextInt(100),
                mealType = MealType.fromString(
                    listOf("breakfast", "lunch", "dinner", "snack").random()
                ),
                imageUrl = null,
                amount = 100,
                date = LocalDate.now(),
                calories = Random.nextInt(2000)
            )
        }

        // When
        val result = calculateMealNutrients(trackedFoods)

        val lunchCalories = result.mealNutrients.values
            .filter { it.mealType is MealType.Lunch }
            .sumOf { it.calories }

        val expectedCalories = trackedFoods
            .filter { it.mealType is MealType.Lunch }
            .sumOf { it.calories }

        // Then
        assertThat(lunchCalories).isEqualTo(expectedCalories)
    }

    @Test
    fun `Fat for dinner properly calculated`() {
        // Given
        val trackedFoods = (1..30).map {
            TrackedFood(
                name = "name",
                carbs = Random.nextInt(100),
                protein = Random.nextInt(100),
                fat = Random.nextInt(100),
                mealType = MealType.fromString(
                    listOf("breakfast", "lunch", "dinner", "snack").random()
                ),
                imageUrl = null,
                amount = 100,
                date = LocalDate.now(),
                calories = Random.nextInt(2000)
            )
        }

        // When
        val result = calculateMealNutrients(trackedFoods)

        val dinnerFat = result.mealNutrients.values
            .filter { it.mealType is MealType.Dinner }
            .sumOf { it.fat }

        val expectedFat = trackedFoods
            .filter { it.mealType is MealType.Dinner }
            .sumOf { it.fat }

        // Then
        assertThat(dinnerFat).isEqualTo(expectedFat)
    }

}