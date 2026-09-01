package com.example.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.model.*
import com.example.data.repository.CommerceRepository
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

data class UiNotification(
    val id: Long = System.currentTimeMillis(),
    val message: String,
    val isSuccess: Boolean = true
)

class CommerceViewModel(
    private val repository: CommerceRepository = CommerceRepository()
) : ViewModel() {

    // Repository flows
    val announcements = repository.sampleAnnouncements
    val goalCategories = repository.goalCategories
    val allProducts = repository.products
    val testimonials = repository.testimonials
    val cartItems = repository.cartItems
    val wishlistIds = repository.wishlistProductIds
    val appliedPromoCode = repository.appliedPromoCode
    val recentSearches = repository.recentSearches
    val userAddress = repository.userAddress
    val orderHistory = repository.orderHistory

    // Navigation State
    private val _currentTab = MutableStateFlow(ScreenTab.HOME)
    val currentTab: StateFlow<ScreenTab> = _currentTab.asStateFlow()

    private val _selectedProduct = MutableStateFlow<Product?>(null)
    val selectedProduct: StateFlow<Product?> = _selectedProduct.asStateFlow()

    private val _isCartDrawerOpen = MutableStateFlow(false)
    val isCartDrawerOpen: StateFlow<Boolean> = _isCartDrawerOpen.asStateFlow()

    private val _isCheckoutActive = MutableStateFlow(false)
    val isCheckoutActive: StateFlow<Boolean> = _isCheckoutActive.asStateFlow()

    private val _selectedCategoryFilter = MutableStateFlow<String?>(null)
    val selectedCategoryFilter: StateFlow<String?> = _selectedCategoryFilter.asStateFlow()

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    private val _sortBy = MutableStateFlow("Featured")
    val sortBy: StateFlow<String> = _sortBy.asStateFlow()

    private val _notification = MutableStateFlow<UiNotification?>(null)
    val notification: StateFlow<UiNotification?> = _notification.asStateFlow()

    // Derived cart calculations
    val cartSubtotal: StateFlow<Double> = cartItems.map { items ->
        items.sumOf { it.totalPrice }
    }.stateIn(viewModelScope, SharingStarted.Eagerly, 0.0)

    val freeShippingThreshold = 50.0

    val freeShippingProgress: StateFlow<Float> = cartSubtotal.map { subtotal ->
        (subtotal / freeShippingThreshold).toFloat().coerceIn(0f, 1f)
    }.stateIn(viewModelScope, SharingStarted.Eagerly, 0f)

    val amountUntilFreeShipping: StateFlow<Double> = cartSubtotal.map { subtotal ->
        (freeShippingThreshold - subtotal).coerceAtLeast(0.0)
    }.stateIn(viewModelScope, SharingStarted.Eagerly, freeShippingThreshold)

    val discountAmount: StateFlow<Double> = combine(cartSubtotal, appliedPromoCode) { subtotal, promo ->
        when (promo) {
            "VITAL15" -> subtotal * 0.15
            "VERDANT10" -> subtotal * 0.10
            "WELCOME20" -> subtotal * 0.20
            else -> 0.0
        }
    }.stateIn(viewModelScope, SharingStarted.Eagerly, 0.0)

    val shippingFee: StateFlow<Double> = cartSubtotal.map { subtotal ->
        if (subtotal >= freeShippingThreshold || subtotal == 0.0) 0.0 else 5.95
    }.stateIn(viewModelScope, SharingStarted.Eagerly, 0.0)

    val cartTotal: StateFlow<Double> = combine(cartSubtotal, discountAmount, shippingFee) { subtotal, discount, shipping ->
        (subtotal - discount + shipping).coerceAtLeast(0.0)
    }.stateIn(viewModelScope, SharingStarted.Eagerly, 0.0)

    val cartItemCount: StateFlow<Int> = cartItems.map { items ->
        items.sumOf { it.quantity }
    }.stateIn(viewModelScope, SharingStarted.Eagerly, 0)

    // Filtered Products for Catalog & Search
    val filteredProducts: StateFlow<List<Product>> = combine(
        _selectedCategoryFilter,
        _searchQuery,
        _sortBy
    ) { category, query, sort ->
        var list = allProducts

        if (!category.isNullOrBlank() && category != "All") {
            list = list.filter { it.category.equals(category, ignoreCase = true) || it.goal.equals(category, ignoreCase = true) }
        }

        if (query.isNotBlank()) {
            val q = query.trim().lowercase()
            list = list.filter {
                it.title.lowercase().contains(q) ||
                it.subtitle.lowercase().contains(q) ||
                it.category.lowercase().contains(q) ||
                it.ingredients.lowercase().contains(q) ||
                it.description.lowercase().contains(q)
            }
        }

        when (sort) {
            "Price: Low to High" -> list.sortedBy { it.price }
            "Price: High to Low" -> list.sortedByDescending { it.price }
            "Top Rated" -> list.sortedByDescending { it.rating }
            "Most Popular" -> list.sortedByDescending { it.reviewCount }
            else -> list
        }
    }.stateIn(viewModelScope, SharingStarted.Eagerly, allProducts)

    fun navigateToTab(tab: ScreenTab) {
        _currentTab.value = tab
        _selectedProduct.value = null
        _isCheckoutActive.value = false
    }

    fun openProductDetail(product: Product) {
        _selectedProduct.value = product
    }

    fun closeProductDetail() {
        _selectedProduct.value = null
    }

    fun openCart() {
        _isCartDrawerOpen.value = true
    }

    fun closeCart() {
        _isCartDrawerOpen.value = false
    }

    fun startCheckout() {
        _isCartDrawerOpen.value = false
        _isCheckoutActive.value = true
    }

    fun cancelCheckout() {
        _isCheckoutActive.value = false
    }

    fun setCategoryFilter(category: String?) {
        _selectedCategoryFilter.value = category
    }

    fun setSearchQuery(query: String) {
        _searchQuery.value = query
    }

    fun setSortBy(sort: String) {
        _sortBy.value = sort
    }

    fun addToCart(product: Product, size: String = product.sizes.first(), quantity: Int = 1) {
        repository.addToCart(product, size, quantity)
        showNotification("Added ${product.title} to bag")
    }

    fun updateCartQuantity(product: Product, size: String, newQuantity: Int) {
        repository.updateQuantity(product, size, newQuantity)
    }

    fun removeFromCart(productId: String, size: String) {
        repository.removeFromCart(productId, size)
    }

    fun toggleWishlist(productId: String) {
        val isAdded = !repository.isWishlisted(productId)
        repository.toggleWishlist(productId)
        val product = allProducts.find { it.id == productId }
        val name = product?.title ?: "Item"
        showNotification(if (isAdded) "Saved $name to wishlist" else "Removed from wishlist")
    }

    fun applyPromoCode(code: String) {
        val success = repository.applyPromoCode(code)
        if (success) {
            showNotification("Promo code applied: $code", isSuccess = true)
        } else {
            showNotification("Invalid promo code. Try VITAL15", isSuccess = false)
        }
    }

    fun removePromoCode() {
        repository.removePromoCode()
        showNotification("Promo code removed")
    }

    fun submitSearch(query: String) {
        repository.addRecentSearch(query)
    }

    fun removeRecentSearch(query: String) {
        repository.removeRecentSearch(query)
    }

    fun completeOrder(paymentMethod: String) {
        val items = cartItems.value
        if (items.isEmpty()) return

        val order = UserOrder(
            orderId = "VP-${(10000..99999).random()}",
            date = "Today",
            status = "Confirmed & Preparing",
            items = items,
            subtotal = cartSubtotal.value,
            shipping = shippingFee.value,
            discount = discountAmount.value,
            total = cartTotal.value,
            deliveryAddress = userAddress.value
        )
        repository.placeOrder(order)
        _isCheckoutActive.value = false
        showNotification("Order ${order.orderId} successfully placed!", isSuccess = true)
    }

    fun updateAddress(newAddress: Address) {
        repository.updateAddress(newAddress)
        showNotification("Delivery address updated")
    }

    fun dismissNotification() {
        _notification.value = null
    }

    private fun showNotification(message: String, isSuccess: Boolean = true) {
        viewModelScope.launch {
            _notification.value = UiNotification(message = message, isSuccess = isSuccess)
        }
    }
}
