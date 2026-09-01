package com.example.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.Product
import com.example.ui.CommerceViewModel
import com.example.ui.components.ProductCard
import com.example.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShopCatalogScreen(
    viewModel: CommerceViewModel,
    products: List<Product>,
    wishlistIds: Set<String>,
    selectedCategory: String?,
    sortBy: String,
    onProductClick: (Product) -> Unit,
    modifier: Modifier = Modifier
) {
    val categories = listOf("All", "Daily Vitality", "Skin & Hair", "Cellular Longevity", "Deep Sleep & Calm", "Strength & Muscle", "Gut & Microbiome")
    var isSortMenuExpanded by remember { mutableStateOf(false) }
    val sortOptions = listOf("Featured", "Top Rated", "Most Popular", "Price: Low to High", "Price: High to Low")

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(WarmCream)
    ) {
        // Top Header
        Surface(
            color = PureWhite,
            border = BorderStroke(1.dp, BorderLight),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 12.dp)
            ) {
                Text(
                    text = "THE BOTANICAL APOTHECARY",
                    style = MaterialTheme.typography.labelSmall.copy(
                        color = SecondaryGreen,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 1.0.sp
                    )
                )
                Text(
                    text = "All Organic Formulas",
                    style = MaterialTheme.typography.headlineLarge.copy(
                        fontWeight = FontWeight.Black,
                        color = DarkForestGreen
                    )
                )
                Text(
                    text = "Clinical bioavailable plant solutions formulated without compromises.",
                    style = MaterialTheme.typography.bodySmall.copy(
                        color = TextSecondary
                    )
                )
            }
        }

        // Category Filter Chips
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(PureWhite)
                .horizontalScroll(rememberScrollState())
                .padding(horizontal = 16.dp, vertical = 10.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            categories.forEach { cat ->
                val isSelected = (selectedCategory == null && cat == "All") || (selectedCategory == cat)
                FilterChip(
                    selected = isSelected,
                    onClick = {
                        viewModel.setCategoryFilter(if (cat == "All") null else cat)
                    },
                    label = {
                        Text(
                            text = cat,
                            style = MaterialTheme.typography.labelMedium.copy(
                                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                                color = if (isSelected) PureWhite else TextPrimary
                            )
                        )
                    },
                    colors = FilterChipDefaults.filterChipColors(
                        selectedContainerColor = BotanicalGreen,
                        selectedLabelColor = PureWhite,
                        containerColor = SoftBeige,
                        labelColor = TextPrimary
                    ),
                    border = FilterChipDefaults.filterChipBorder(
                        enabled = true,
                        selected = isSelected,
                        borderColor = if (isSelected) BotanicalGreen else BorderLight
                    ),
                    shape = RoundedCornerShape(20.dp)
                )
            }
        }

        Divider(color = BorderLight, thickness = 1.dp)

        // Sub-bar: Products Count and Sort Dropdown
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 10.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "${products.size} Formulas Available",
                style = MaterialTheme.typography.labelMedium.copy(
                    color = TextSecondary,
                    fontWeight = FontWeight.SemiBold
                )
            )

            Box {
                Surface(
                    color = PureWhite,
                    shape = RoundedCornerShape(6.dp),
                    border = BorderStroke(1.dp, BorderLight),
                    modifier = Modifier
                        .clip(RoundedCornerShape(6.dp))
                        .clickable { isSortMenuExpanded = true }
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.Sort,
                            contentDescription = null,
                            tint = BotanicalGreen,
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = sortBy,
                            style = MaterialTheme.typography.labelMedium.copy(
                                color = DarkForestGreen,
                                fontWeight = FontWeight.Bold
                            )
                        )
                        Icon(
                            imageVector = Icons.Default.ArrowDropDown,
                            contentDescription = null,
                            tint = TextSecondary,
                            modifier = Modifier.size(18.dp)
                        )
                    }
                }

                DropdownMenu(
                    expanded = isSortMenuExpanded,
                    onDismissRequest = { isSortMenuExpanded = false }
                ) {
                    sortOptions.forEach { option ->
                        DropdownMenuItem(
                            text = {
                                Text(
                                    text = option,
                                    fontWeight = if (sortBy == option) FontWeight.Bold else FontWeight.Normal,
                                    color = if (sortBy == option) BotanicalGreen else TextPrimary
                                )
                            },
                            onClick = {
                                viewModel.setSortBy(option)
                                isSortMenuExpanded = false
                            }
                        )
                    }
                }
            }
        }

        // Product Grid
        if (products.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(32.dp),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(
                        imageVector = Icons.Default.Spa,
                        contentDescription = null,
                        tint = SecondaryGreen,
                        modifier = Modifier.size(48.dp)
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(
                        text = "No Formulas Found",
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.Bold,
                            color = DarkForestGreen
                        )
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = "Try clearing the active category filters to view our full collection.",
                        style = MaterialTheme.typography.bodyMedium.copy(
                            color = TextSecondary,
                            textAlign = TextAlign.Center
                        )
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Button(
                        onClick = { viewModel.setCategoryFilter(null) },
                        colors = ButtonDefaults.buttonColors(containerColor = BotanicalGreen)
                    ) {
                        Text("Show All Products")
                    }
                }
            }
        } else {
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                contentPadding = PaddingValues(horizontal = 14.dp, vertical = 6.dp),
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.fillMaxSize()
            ) {
                items(products, key = { it.id }) { product ->
                    ProductCard(
                        product = product,
                        isWishlisted = wishlistIds.contains(product.id),
                        onProductClick = { onProductClick(product) },
                        onWishlistToggle = { viewModel.toggleWishlist(product.id) },
                        onQuickAdd = { viewModel.addToCart(product) }
                    )
                }

                item(span = { GridItemSpan(2) }) {
                    Spacer(modifier = Modifier.height(32.dp))
                }
            }
        }
    }
}
