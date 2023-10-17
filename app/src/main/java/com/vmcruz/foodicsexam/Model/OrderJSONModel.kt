package com.vmcruz.foodicsexam.Model

data class OrderJSONModel(
    private val orders: Orders,
    private val cart : List<Cart>
)
