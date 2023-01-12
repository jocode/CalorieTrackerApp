package com.crexative.tracker_data.repository

import com.crexative.tracker_data.local.TrackerDao
import com.crexative.tracker_data.mapper.toTrackableFood
import com.crexative.tracker_data.mapper.toTrackedFood
import com.crexative.tracker_data.mapper.toTrackedFoodEntity
import com.crexative.tracker_data.remote.OpenFoodApi
import com.crexative.tracker_domain.model.TrackableFood
import com.crexative.tracker_domain.model.TrackedFood
import com.crexative.tracker_domain.repository.TrackerRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.time.LocalDate

class TrackerRepositoryImpl(
    private val dao: TrackerDao,
    private val api: OpenFoodApi
) : TrackerRepository {

    override suspend fun searchFood(
        query: String,
        page: Int,
        pageSize: Int
    ): Result<List<TrackableFood>> {
        return try {
            val searchDto = api.searchFood(
                query = query,
                page = page,
                pageSize = pageSize
            )
            Result.success(
                searchDto.products
                    .filter {
                        val calculateCalories = it.nutriments.carbohydrates100g * 4f +
                                it.nutriments.proteins100g * 4f +
                                it.nutriments.fat100g * 9f

                        val lowerBound = calculateCalories * 0.99f
                        val upperBound = calculateCalories * 1.01f

                        it.nutriments.energyKcal100g in (lowerBound..upperBound)
                    }
                    .mapNotNull { it.toTrackableFood() }
            )
        } catch (e: Exception) {
            e.printStackTrace()
            Result.failure(e)
        }
    }

    override suspend fun insertTrackedFood(food: TrackedFood) {
        dao.insertTrackedFood(food.toTrackedFoodEntity())
    }

    override suspend fun deleteTrackedFood(food: TrackedFood) {
        dao.deleteTrackedFood(food.toTrackedFoodEntity())
    }

    override fun getFoodsForDate(localDate: LocalDate): Flow<List<TrackedFood>> {
        return dao.getFoodsForDate(
            localDate.dayOfMonth,
            localDate.monthValue,
            localDate.year
        ).map { entities ->
            entities.map { it.toTrackedFood() }
        }
    }

}