package com.example.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.Product
import com.example.data.model.ScreenTab
import com.example.ui.CommerceViewModel
import com.example.ui.theme.*

@Composable
fun WishlistScreen(
    viewModel: CommerceViewModel,
    wishlistProducts: List<Product>,
    onProductClick: (Product) -> Unit,
    onNavigateTab: (ScreenTab) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(WarmCream)
    ) {
        // Header
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
                    text = "SAVED BOTANICALS",
                    style = MaterialTheme.typography.labelSmall.copy(
                        color = SecondaryGreen,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 1.0.sp
                    )
                )
                Text(
                    text = "Your Wishlist (${wishlistProducts.size})",
                    style = MaterialTheme.typography.headlineLarge.copy(
                        fontWeight = FontWeight.Black,
                        color = DarkForestGreen
                    )
                )
            }
        }

        if (wishlistProducts.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(32.dp),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Box(
                        modifier = Modifier
                            .size(72.dp)
                            .clip(CircleShape)
                            .background(SoftBeige),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.FavoriteBorder,
                            contentDescription = null,
                            tint = SecondaryGreen,
                            modifier = Modifier.size(36.dp)
                        )
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    Text(
                        text = "Your Wishlist is Empty",
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.Bold,
                            color = DarkForestGreen
                        )
                    )

                    Spacer(modifier = Modifier.height(6.dp))

                    Text(
                        text = "Save your favorite formulas and cellular elixirs here for easy ordering later.",
                        style = MaterialTheme.typography.bodyMedium.copy(
                            color = TextSecondary,
                            textAlign = TextAlign.Center
                        )
                    )

                    Spacer(modifier = Modifier.height(20.dp))

                    Button(
                        onClick = { onNavigateTab(ScreenTab.SHOP) },
                        colors = ButtonDefaults.buttonColors(containerColor = BotanicalGreen),
                        shape = RoundedCornerShape(8.dp),
                        modifier = Modifier
                            .height(46.dp)
                            .testTag("wishlist_explore_button")
                    ) {
                        Text(
                            text = "EXPLORE COLLECTION",
                            style = MaterialTheme.typography.labelLarge.copy(letterSpacing = 0.6.sp)
                        )
                    }
                }
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp, vertical = 12.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                items(wishlistProducts, key = { it.id }) { product ->
                    Card(
                        shape = RoundedCornerShape(12.dp),
                        colors = CardDefaults.cardColors(containerColor = PureWhite),
                        border = BorderStroke(1.dp, BorderLight),
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { onProductClick(product) }
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(12.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Image(
                                painter = painterResource(id = product.imageRes),
                                contentDescription = product.title,
                                contentScale = ContentScale.Crop,
                                modifier = Modifier
                                    .size(76.dp)
                                    .clip(RoundedCornerShape(8.dp))
                                    .background(WarmCream)
                            )

                            Spacer(modifier = Modifier.width(12.dp))

                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = product.category.uppercase(),
                                    style = MaterialTheme.typography.labelSmall.copy(
                                        color = SecondaryGreen,
                                        fontSize = 9.sp,
                                        fontWeight = FontWeight.Bold
                                    )
                                )

                                Text(
                                    text = product.title,
                                    style = MaterialTheme.typography.titleMedium.copy(
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 14.sp,
                                        color = DarkForestGreen
                                    ),
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis
                                )

                                Text(
                                    text = "$${"%.2f".format(product.price)}",
                                    style = MaterialTheme.typography.titleMedium.copy(
                                        fontWeight = FontWeight.Black,
                                        color = BotanicalGreen,
                                        fontSize = 15.sp
                                    )
                                )

                                Spacer(modifier = Modifier.height(8.dp))

                                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                                    Button(
                                        onClick = { viewModel.addToCart(product) },
                                        colors = ButtonDefaults.buttonColors(containerColor = BotanicalGreen),
                                        shape = RoundedCornerShape(6.dp),
                                        contentPadding = PaddingValues(horizontal = 10.dp, vertical = 4.dp),
                                        modifier = Modifier.height(32.dp)
                                    ) {
                                        Icon(
                                            imageVector = Icons.Outlined.ShoppingBag,
                                            contentDescription = null,
                                            modifier = Modifier.size(14.dp)
                                        )
                                        Spacer(modifier = Modifier.width(4.dp))
                                        Text("ADD TO BAG", fontSize = 10.5.sp, fontWeight = FontWeight.Bold)
                                    }

                                    OutlinedButton(
                                        onClick = { viewModel.toggleWishlist(product.id) },
                                        shape = RoundedCornerShape(6.dp),
                                        border = BorderStroke(1.dp, BorderLight),
                                        contentPadding = PaddingValues(horizontal = 8.dp, vertical = 4.dp),
                                        modifier = Modifier.height(32.dp)
                                    ) {
                                        Text("Remove", fontSize = 10.5.sp, color = TextSecondary)
                                    }
                                }
                            }
                        }
                    }
                }

                item {
                    Spacer(modifier = Modifier.height(24.dp))
                }
            }
        }
    }
}
