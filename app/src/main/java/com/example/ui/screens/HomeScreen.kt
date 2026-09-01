package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.Product
import com.example.data.model.ScreenTab
import com.example.ui.CommerceViewModel
import com.example.ui.components.*
import com.example.ui.theme.*

@Composable
fun HomeScreen(
    viewModel: CommerceViewModel,
    products: List<Product>,
    wishlistIds: Set<String>,
    onProductClick: (Product) -> Unit,
    onNavigateTab: (ScreenTab) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(WarmCream)
    ) {
        // Hero section
        item {
            HeroSection(
                onShopNowClick = { onNavigateTab(ScreenTab.SHOP) },
                onExploreGoalsClick = { onNavigateTab(ScreenTab.SHOP) }
            )
        }

        // Trust value propositions
        item {
            TrustValueStrip()
        }

        // Shop by Goal / Category carousel
        item {
            GoalCategorySection(
                categories = viewModel.goalCategories,
                selectedCategory = null,
                onSelectCategory = { category ->
                    viewModel.setCategoryFilter(category)
                    onNavigateTab(ScreenTab.SHOP)
                }
            )
        }

        // Best Sellers Product Section
        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(PureWhite)
                    .padding(vertical = 20.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 16.dp, end = 16.dp, bottom = 14.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text = "MOST REVERED FORMULAS",
                            style = MaterialTheme.typography.labelSmall.copy(
                                color = SecondaryGreen,
                                fontWeight = FontWeight.Bold,
                                letterSpacing = 1.0.sp
                            )
                        )
                        Text(
                            text = "Featured Best Sellers",
                            style = MaterialTheme.typography.headlineMedium.copy(
                                fontWeight = FontWeight.Bold,
                                color = DarkForestGreen
                            )
                        )
                    }

                    TextButton(onClick = { onNavigateTab(ScreenTab.SHOP) }) {
                        Text(
                            text = "View All",
                            style = MaterialTheme.typography.labelMedium.copy(
                                color = BotanicalGreen,
                                fontWeight = FontWeight.Bold
                            )
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Icon(
                            imageVector = Icons.Default.ArrowForward,
                            contentDescription = null,
                            tint = BotanicalGreen,
                            modifier = Modifier.size(14.dp)
                        )
                    }
                }

                // Horizontal Carousel of Product Cards
                LazyRow(
                    contentPadding = PaddingValues(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(14.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    items(products.take(4), key = { it.id }) { product ->
                        ProductCard(
                            product = product,
                            isWishlisted = wishlistIds.contains(product.id),
                            onProductClick = { onProductClick(product) },
                            onWishlistToggle = { viewModel.toggleWishlist(product.id) },
                            onQuickAdd = { viewModel.addToCart(product) },
                            modifier = Modifier.width(220.dp)
                        )
                    }
                }
            }
        }

        // Brand Science & Purity Banner
        item {
            BrandScienceSection()
        }

        // Lifestyle Editorial Campaign Banner
        item {
            LifestyleCampaignBanner(
                onExploreClick = { onNavigateTab(ScreenTab.SHOP) }
            )
        }

        // Testimonials & Social Proof
        item {
            TestimonialsSection(testimonials = viewModel.testimonials)
        }

        // Community Newsletter & Discount Capture
        item {
            CommunityNewsletterSection(
                onJoinCommunity = { email ->
                    viewModel.applyPromoCode("VITAL15")
                }
            )
        }

        // Footer
        item {
            FooterSection(onNavigateTab = onNavigateTab)
        }

        // Bottom spacing for navigation bar
        item {
            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}
