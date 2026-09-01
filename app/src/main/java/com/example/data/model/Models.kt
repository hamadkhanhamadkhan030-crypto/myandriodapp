package com.example.data.model

import androidx.annotation.DrawableRes

data class Product(
    val id: String,
    val title: String,
    val subtitle: String,
    val category: String,
    val goal: String,
    val price: Double,
    val compareAtPrice: Double? = null,
    val rating: Double,
    val reviewCount: Int,
    val badge: String? = null,
    @DrawableRes val imageRes: Int,
    val description: String,
    val benefits: List<String>,
    val ingredients: String,
    val howToUse: String,
    val sizes: List<String> = listOf("30 Servings", "60 Servings", "90 Servings"),
    val inStock: Boolean = true,
    val servingsCount: Int = 30,
    val certs: List<String> = listOf("100% Organic", "Non-GMO", "Third-Party Tested", "Gluten-Free")
)

data class GoalCategory(
    val id: String,
    val title: String,
    val tag: String,
    val description: String,
    val iconName: String
)

data class CartItem(
    val product: Product,
    val quantity: Int = 1,
    val selectedSize: String = "30 Servings"
) {
    val totalPrice: Double
        get() = product.price * quantity
}

data class Testimonial(
    val id: String,
    val author: String,
    val role: String,
    val rating: Int,
    val quote: String,
    val verifiedPurchase: Boolean = true,
    val productPurchased: String
)

data class Announcement(
    val id: String,
    val text: String,
    val highlight: String
)

data class Address(
    val fullName: String,
    val street: String,
    val city: String,
    val state: String,
    val zipCode: String,
    val country: String = "United States",
    val phone: String,
    val isDefault: Boolean = true
)

data class UserOrder(
    val orderId: String,
    val date: String,
    val status: String,
    val items: List<CartItem>,
    val subtotal: Double,
    val shipping: Double,
    val discount: Double,
    val total: Double,
    val deliveryAddress: Address
)

enum class ScreenTab {
    HOME,
    SHOP,
    SEARCH,
    WISHLIST,
    ACCOUNT
}
