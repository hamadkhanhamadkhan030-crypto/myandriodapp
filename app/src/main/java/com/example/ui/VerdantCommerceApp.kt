package com.example.ui

import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.data.model.ScreenTab
import com.example.ui.components.CommerceBottomNav
import com.example.ui.components.MainHeader
import com.example.ui.components.TopAnnouncementBar
import com.example.ui.screens.*
import com.example.ui.theme.*
import kotlinx.coroutines.delay

@Composable
fun VerdantCommerceApp(
    viewModel: CommerceViewModel = viewModel()
) {
    val currentTab by viewModel.currentTab.collectAsStateWithLifecycle()
    val selectedProduct by viewModel.selectedProduct.collectAsStateWithLifecycle()
    val isCartDrawerOpen by viewModel.isCartDrawerOpen.collectAsStateWithLifecycle()
    val isCheckoutActive by viewModel.isCheckoutActive.collectAsStateWithLifecycle()

    val products by viewModel.filteredProducts.collectAsStateWithLifecycle()
    val allProducts = viewModel.allProducts
    val cartItems by viewModel.cartItems.collectAsStateWithLifecycle()
    val wishlistIds by viewModel.wishlistIds.collectAsStateWithLifecycle()
    val announcements = viewModel.announcements

    val subtotal by viewModel.cartSubtotal.collectAsStateWithLifecycle()
    val discount by viewModel.discountAmount.collectAsStateWithLifecycle()
    val shippingFee by viewModel.shippingFee.collectAsStateWithLifecycle()
    val total by viewModel.cartTotal.collectAsStateWithLifecycle()
    val cartCount by viewModel.cartItemCount.collectAsStateWithLifecycle()
    val freeShippingProgress by viewModel.freeShippingProgress.collectAsStateWithLifecycle()
    val amountUntilFreeShipping by viewModel.amountUntilFreeShipping.collectAsStateWithLifecycle()
    val appliedPromoCode by viewModel.appliedPromoCode.collectAsStateWithLifecycle()

    val searchQuery by viewModel.searchQuery.collectAsStateWithLifecycle()
    val recentSearches by viewModel.recentSearches.collectAsStateWithLifecycle()
    val selectedCategoryFilter by viewModel.selectedCategoryFilter.collectAsStateWithLifecycle()
    val sortBy by viewModel.sortBy.collectAsStateWithLifecycle()

    val userAddress by viewModel.userAddress.collectAsStateWithLifecycle()
    val orderHistory by viewModel.orderHistory.collectAsStateWithLifecycle()
    val notification by viewModel.notification.collectAsStateWithLifecycle()

    // Auto-dismiss notification
    LaunchedEffect(notification?.id) {
        if (notification != null) {
            delay(2800)
            viewModel.dismissNotification()
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(WarmCream)
    ) {
        Scaffold(
            topBar = {
                // Show announcement & main header only when not in detail or checkout
                if (selectedProduct == null && !isCheckoutActive) {
                    Column(modifier = Modifier.fillMaxWidth()) {
                        TopAnnouncementBar(announcements = announcements)
                        MainHeader(
                            currentTab = currentTab,
                            cartItemCount = cartCount,
                            wishlistCount = wishlistIds.size,
                            onNavigateTab = { viewModel.navigateToTab(it) },
                            onOpenCart = { viewModel.openCart() }
                        )
                    }
                }
            },
            bottomBar = {
                // Persistent bottom navigation
                if (selectedProduct == null && !isCheckoutActive) {
                    CommerceBottomNav(
                        currentTab = currentTab,
                        cartCount = cartCount,
                        wishlistCount = wishlistIds.size,
                        onTabSelected = { viewModel.navigateToTab(it) }
                    )
                }
            },
            modifier = Modifier.fillMaxSize()
        ) { innerPadding ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
            ) {
                when {
                    isCheckoutActive -> {
                        CheckoutScreen(
                            viewModel = viewModel,
                            cartItems = cartItems,
                            subtotal = subtotal,
                            discount = discount,
                            shippingFee = shippingFee,
                            total = total,
                            userAddress = userAddress,
                            onBackClick = { viewModel.cancelCheckout() },
                            onOrderSuccess = {
                                viewModel.navigateToTab(ScreenTab.ACCOUNT)
                            }
                        )
                    }
                    selectedProduct != null -> {
                        ProductDetailScreen(
                            product = selectedProduct!!,
                            viewModel = viewModel,
                            isWishlisted = wishlistIds.contains(selectedProduct!!.id),
                            onBackClick = { viewModel.closeProductDetail() },
                            onSelectRelatedProduct = { rel ->
                                viewModel.openProductDetail(rel)
                            }
                        )
                    }
                    currentTab == ScreenTab.HOME -> {
                        HomeScreen(
                            viewModel = viewModel,
                            products = allProducts,
                            wishlistIds = wishlistIds,
                            onProductClick = { viewModel.openProductDetail(it) },
                            onNavigateTab = { viewModel.navigateToTab(it) }
                        )
                    }
                    currentTab == ScreenTab.SHOP -> {
                        ShopCatalogScreen(
                            viewModel = viewModel,
                            products = products,
                            wishlistIds = wishlistIds,
                            selectedCategory = selectedCategoryFilter,
                            sortBy = sortBy,
                            onProductClick = { viewModel.openProductDetail(it) }
                        )
                    }
                    currentTab == ScreenTab.SEARCH -> {
                        SearchScreen(
                            viewModel = viewModel,
                            searchQuery = searchQuery,
                            searchResults = products,
                            recentSearches = recentSearches,
                            wishlistIds = wishlistIds,
                            onProductClick = { viewModel.openProductDetail(it) }
                        )
                    }
                    currentTab == ScreenTab.WISHLIST -> {
                        val wishlistProducts = allProducts.filter { wishlistIds.contains(it.id) }
                        WishlistScreen(
                            viewModel = viewModel,
                            wishlistProducts = wishlistProducts,
                            onProductClick = { viewModel.openProductDetail(it) },
                            onNavigateTab = { viewModel.navigateToTab(it) }
                        )
                    }
                    currentTab == ScreenTab.ACCOUNT -> {
                        AccountProfileScreen(
                            viewModel = viewModel,
                            userAddress = userAddress,
                            orderHistory = orderHistory
                        )
                    }
                }
            }
        }

        // Cart Slide-up Bottom Drawer
        if (isCartDrawerOpen) {
            CartDrawerSheet(
                viewModel = viewModel,
                cartItems = cartItems,
                subtotal = subtotal,
                discount = discount,
                shippingFee = shippingFee,
                total = total,
                freeShippingProgress = freeShippingProgress,
                amountUntilFreeShipping = amountUntilFreeShipping,
                appliedPromoCode = appliedPromoCode,
                onClose = { viewModel.closeCart() },
                onStartCheckout = { viewModel.startCheckout() },
                onExploreShop = { viewModel.navigateToTab(ScreenTab.SHOP) }
            )
        }

        // Animated Toast / Notification Banner
        AnimatedVisibility(
            visible = notification != null,
            enter = slideInVertically(initialOffsetY = { -it }) + fadeIn(),
            exit = slideOutVertically(targetOffsetY = { -it }) + fadeOut(),
            modifier = Modifier
                .align(Alignment.TopCenter)
                .statusBarsPadding()
                .padding(top = 8.dp, start = 16.dp, end = 16.dp)
        ) {
            notification?.let { note ->
                Surface(
                    color = if (note.isSuccess) DarkForestGreen else Color(0xFF5A1A1A),
                    shape = RoundedCornerShape(10.dp),
                    shadowElevation = 8.dp,
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("app_notification_toast")
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = if (note.isSuccess) Icons.Default.CheckCircle else Icons.Default.Info,
                            contentDescription = null,
                            tint = if (note.isSuccess) GoldAccent else PureWhite,
                            modifier = Modifier.size(20.dp)
                        )
                        Spacer(modifier = Modifier.width(10.dp))
                        Text(
                            text = note.message,
                            style = MaterialTheme.typography.bodyMedium.copy(
                                color = WarmCream,
                                fontWeight = FontWeight.Bold,
                                fontSize = 13.5.sp
                            ),
                            modifier = Modifier.weight(1f)
                        )
                    }
                }
            }
        }
    }
}
