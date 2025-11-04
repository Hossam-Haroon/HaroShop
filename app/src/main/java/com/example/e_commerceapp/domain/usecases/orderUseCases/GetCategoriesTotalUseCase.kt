package com.example.e_commerceapp.domain.usecases.orderUseCases

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetCategoriesTotalUseCase @Inject constructor(
    private val getAllOrdersForCurrentUserUseCase: GetAllOrdersForCurrentUserUseCase
) {
    operator fun invoke(): Flow<Map<String, Float>> {
        return getAllOrdersForCurrentUserUseCase().map { orders ->
            orders
                .flatMap { it.items }
                .groupBy { it.category }
                .mapValues { (_, items) ->
                    items.sumOf { it.pricePaid * it.quantity }.toFloat()
                }
                .toList()
                .sortedBy { it.second }
                .toMap()
        }
    }
}