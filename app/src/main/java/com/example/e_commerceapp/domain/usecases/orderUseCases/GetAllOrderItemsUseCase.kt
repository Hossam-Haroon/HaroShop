package com.example.e_commerceapp.domain.usecases.orderUseCases

import com.example.e_commerceapp.domain.model.OrderItem
import com.example.e_commerceapp.domain.model.OrderItemWithOrderDate
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetAllOrderItemsUseCase @Inject constructor(
    private val getAllOrdersForCurrentUserUseCase: GetAllOrdersForCurrentUserUseCase
) {
    operator fun invoke(): Flow<List<OrderItemWithOrderDate>> {
        return getAllOrdersForCurrentUserUseCase().map { orders ->
            orders.flatMap {order ->
                order.items.map { orderItem ->
                    OrderItemWithOrderDate(
                        orderItem.productId,
                        orderItem.title,
                        orderItem.imageUrl,
                        orderItem.pricePaid,
                        orderItem.quantity,
                        orderItem.size,
                        orderItem.color,
                        orderItem.category,
                        order.orderDate,
                        order.orderNumber
                    )
                }
            }
        }
    }
}