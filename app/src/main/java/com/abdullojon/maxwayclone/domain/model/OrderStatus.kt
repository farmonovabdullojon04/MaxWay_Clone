package com.abdullojon.maxwayclone.domain.model

enum class OrderStatus(val title: String,val step: Int) {
    ORDERED("Заказ оформлен", 0),
    ACCEPTED("Филиал принял", 1),
    COURIER("Курьер забрал", 2),
    DELIVERED("Доставлен", 3)
}