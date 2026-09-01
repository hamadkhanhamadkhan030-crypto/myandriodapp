package com.example.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.Product
import com.example.ui.CommerceViewModel
import com.example.ui.components.ProductCard
import com.example.ui.theme.*

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun SearchScreen(
    viewModel: CommerceViewModel,
    searchQuery: String,
    searchResults: List<Product>,
    recentSearches: List<String>,
    wishlistIds: Set<String>,
    onProductClick: (Product) -> Unit,
    modifier: Modifier = Modifier
) {
    val popularSearches = listOf("Supergreens", "Collagen", "Magnesium Sleep", "Clean Protein", "Probiotics", "Longevity NAD+")

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(WarmCream)
    ) {
        // Search Input Bar
        Surface(
            color = PureWhite,
            border = BorderStroke(1.dp, BorderLight),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                OutlinedTextField(
                    value = searchQuery,
                    onValueChange = { viewModel.setSearchQuery(it) },
                    placeholder = { Text("Search ingredients, formulas, goals...", color = TextMuted) },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = null,
                            tint = BotanicalGreen
                        )
                    },
                    trailingIcon = {
                        if (searchQuery.isNotBlank()) {
                            IconButton(onClick = { viewModel.setSearchQuery("") }) {
                                Icon(
                                    imageVector = Icons.Default.Close,
                                    contentDescription = "Clear",
                                    tint = TextSecondary
                                )
                            }
                        }
                    },
                    singleLine = true,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedContainerColor = SoftBeige,
                        unfocusedContainerColor = SoftBeige,
                        focusedBorderColor = BotanicalGreen,
                        unfocusedBorderColor = BorderLight
                    ),
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("search_text_input")
                )
            }
        }

        if (searchQuery.isBlank()) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp, vertical = 12.dp)
            ) {
                // Recent Searches
                if (recentSearches.isNotEmpty()) {
                    item {
                        Text(
                            text = "RECENT SEARCHES",
                            style = MaterialTheme.typography.labelSmall.copy(
                                color = SecondaryGreen,
                                fontWeight = FontWeight.Bold,
                                letterSpacing = 0.8.sp
                            )
                        )
                        Spacer(modifier = Modifier.height(10.dp))

                        FlowRow(
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                            verticalArrangement = Arrangement.spacedBy(8.dp),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            recentSearches.forEach { term ->
                                Surface(
                                    color = PureWhite,
                                    shape = RoundedCornerShape(20.dp),
                                    border = BorderStroke(1.dp, BorderLight),
                                    modifier = Modifier.clickable {
                                        viewModel.setSearchQuery(term)
                                    }
                                ) {
                                    Row(
                                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Icon(
                                            imageVector = Icons.Outlined.History,
                                            contentDescription = null,
                                            tint = SecondaryGreen,
                                            modifier = Modifier.size(14.dp)
                                        )
                                        Spacer(modifier = Modifier.width(6.dp))
                                        Text(
                                            text = term,
                                            style = MaterialTheme.typography.bodySmall.copy(
                                                color = TextPrimary,
                                                fontWeight = FontWeight.Medium
                                            )
                                        )
                                        Spacer(modifier = Modifier.width(6.dp))
                                        Icon(
                                            imageVector = Icons.Default.Close,
                                            contentDescription = "Remove",
                                            tint = TextMuted,
                                            modifier = Modifier
                                                .size(14.dp)
                                                .clickable { viewModel.removeRecentSearch(term) }
                                        )
                                    }
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(20.dp))
                        Divider(color = BorderLight, thickness = 0.8.dp)
                        Spacer(modifier = Modifier.height(16.dp))
                    }
                }

                // Trending Searches
                item {
                    Text(
                        text = "POPULAR BOTANICAL SEARCHES",
                        style = MaterialTheme.typography.labelSmall.copy(
                            color = SecondaryGreen,
                            fontWeight = FontWeight.Bold,
                            letterSpacing = 0.8.sp
                        )
                    )
                    Spacer(modifier = Modifier.height(10.dp))

                    FlowRow(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        popularSearches.forEach { term ->
                            Surface(
                                color = LightBotanicalGreen,
                                shape = RoundedCornerShape(20.dp),
                                border = BorderStroke(1.dp, SageGreen.copy(alpha = 0.3f)),
                                modifier = Modifier.clickable {
                                    viewModel.setSearchQuery(term)
                                    viewModel.submitSearch(term)
                                }
                            ) {
                                Row(
                                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 7.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.TrendingUp,
                                        contentDescription = null,
                                        tint = BotanicalGreen,
                                        modifier = Modifier.size(14.dp)
                                    )
                                    Spacer(modifier = Modifier.width(6.dp))
                                    Text(
                                        text = term,
                                        style = MaterialTheme.typography.labelMedium.copy(
                                            color = DarkForestGreen,
                                            fontWeight = FontWeight.Bold
                                        )
                                    )
                                }
                            }
                        }
                    }
                }
            }
        } else {
            // Results list/grid
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 10.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Results for “$searchQuery”",
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Bold,
                        color = DarkForestGreen
                    )
                )
                Text(
                    text = "${searchResults.size} found",
                    style = MaterialTheme.typography.labelSmall.copy(
                        color = TextSecondary
                    )
                )
            }

            if (searchResults.isEmpty()) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(32.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Icon(
                            imageVector = Icons.Outlined.SearchOff,
                            contentDescription = null,
                            tint = SecondaryGreen,
                            modifier = Modifier.size(48.dp)
                        )
                        Spacer(modifier = Modifier.height(12.dp))
                        Text(
                            text = "No Formulas Matched",
                            style = MaterialTheme.typography.titleMedium.copy(
                                fontWeight = FontWeight.Bold,
                                color = DarkForestGreen
                            )
                        )
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(
                            text = "Try searching for ingredients like ashwagandha, matcha, or collagen.",
                            style = MaterialTheme.typography.bodyMedium.copy(
                                color = TextSecondary,
                                textAlign = TextAlign.Center
                            )
                        )
                    }
                }
            } else {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    contentPadding = PaddingValues(horizontal = 14.dp, vertical = 4.dp),
                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    modifier = Modifier.fillMaxSize()
                ) {
                    items(searchResults, key = { it.id }) { product ->
                        ProductCard(
                            product = product,
                            isWishlisted = wishlistIds.contains(product.id),
                            onProductClick = {
                                viewModel.submitSearch(searchQuery)
                                onProductClick(product)
                            },
                            onWishlistToggle = { viewModel.toggleWishlist(product.id) },
                            onQuickAdd = { viewModel.addToCart(product) }
                        )
                    }
                }
            }
        }
    }
}
