package com.example.ui.components

import androidx.compose.animation.*
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.Announcement
import com.example.data.model.ScreenTab
import com.example.ui.theme.*
import kotlinx.coroutines.delay

@Composable
fun TopAnnouncementBar(
    announcements: List<Announcement>,
    modifier: Modifier = Modifier
) {
    var currentIndex by remember { mutableIntStateOf(0) }

    LaunchedEffect(announcements.size) {
        if (announcements.isNotEmpty()) {
            while (true) {
                delay(4000)
                currentIndex = (currentIndex + 1) % announcements.size
            }
        }
    }

    Box(
        modifier = modifier
            .fillMaxWidth()
            .background(NaturalDarkGreen)
            .padding(horizontal = 16.dp, vertical = 7.dp),
        contentAlignment = Alignment.Center
    ) {
        AnimatedContent(
            targetState = currentIndex,
            transitionSpec = {
                (slideInVertically { height -> height } + fadeIn()).togetherWith(
                    slideOutVertically { height -> -height } + fadeOut()
                )
            },
            label = "AnnouncementTicker"
        ) { index ->
            val item = announcements.getOrNull(index) ?: return@AnimatedContent
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxWidth()
            ) {
                Icon(
                    imageVector = Icons.Default.Verified,
                    contentDescription = null,
                    tint = NaturalPillSage,
                    modifier = Modifier.size(13.dp)
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    text = item.text,
                    style = MaterialTheme.typography.labelSmall.copy(
                        color = NaturalBg,
                        letterSpacing = 0.8.sp,
                        fontWeight = FontWeight.Bold
                    ),
                    textAlign = TextAlign.Center,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}

@Composable
fun MainHeader(
    currentTab: ScreenTab,
    cartItemCount: Int,
    wishlistCount: Int,
    onNavigateTab: (ScreenTab) -> Unit,
    onOpenCart: () -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        color = NaturalBg,
        border = BorderStroke(0.8.dp, NaturalBorder),
        shadowElevation = 0.dp,
        modifier = modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            // Brand Logo & Mark in Natural Tones Pill Style
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .clickable { onNavigateTab(ScreenTab.HOME) }
                    .testTag("header_brand_logo")
            ) {
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .clip(CircleShape)
                        .background(NaturalPillSage),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Spa,
                        contentDescription = "Verdant Pure Emblem",
                        tint = NaturalGreen,
                        modifier = Modifier.size(22.dp)
                    )
                }
                Spacer(modifier = Modifier.width(10.dp))
                Column {
                    Text(
                        text = "Good morning,",
                        style = MaterialTheme.typography.labelSmall.copy(
                            color = NaturalTextSecondary,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Medium
                        )
                    )
                    Text(
                        text = "Verdant Pure",
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.Bold,
                            letterSpacing = 0.2.sp,
                            color = NaturalTextPrimary,
                            fontSize = 17.sp
                        )
                    )
                }
            }

            // Action Icons
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                // Search Action
                IconButton(
                    onClick = { onNavigateTab(ScreenTab.SEARCH) },
                    modifier = Modifier
                        .size(40.dp)
                        .testTag("header_search_button")
                ) {
                    Icon(
                        imageVector = if (currentTab == ScreenTab.SEARCH) Icons.Filled.Search else Icons.Outlined.Search,
                        contentDescription = "Search Catalog",
                        tint = if (currentTab == ScreenTab.SEARCH) NaturalGreen else NaturalTextBody,
                        modifier = Modifier.size(22.dp)
                    )
                }

                // Wishlist Action with Badge
                IconButton(
                    onClick = { onNavigateTab(ScreenTab.WISHLIST) },
                    modifier = Modifier
                        .size(40.dp)
                        .testTag("header_wishlist_button")
                ) {
                    BadgedBox(
                        badge = {
                            if (wishlistCount > 0) {
                                Badge(
                                    containerColor = NaturalPillSage,
                                    contentColor = NaturalGreen
                                ) {
                                    Text(
                                        text = wishlistCount.toString(),
                                        fontSize = 9.sp,
                                        fontWeight = FontWeight.Bold
                                    )
                                }
                            }
                        }
                    ) {
                        Icon(
                            imageVector = if (currentTab == ScreenTab.WISHLIST) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder,
                            contentDescription = "Saved Wishlist",
                            tint = if (currentTab == ScreenTab.WISHLIST) NaturalGreen else NaturalTextBody,
                            modifier = Modifier.size(22.dp)
                        )
                    }
                }

                // Shopping Cart Bag with Badge
                IconButton(
                    onClick = onOpenCart,
                    modifier = Modifier
                        .size(40.dp)
                        .testTag("header_cart_button")
                ) {
                    BadgedBox(
                        badge = {
                            if (cartItemCount > 0) {
                                Badge(
                                    containerColor = NaturalGreen,
                                    contentColor = NaturalBg
                                ) {
                                    Text(
                                        text = cartItemCount.toString(),
                                        fontSize = 9.sp,
                                        fontWeight = FontWeight.Bold
                                    )
                                }
                            }
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.ShoppingBag,
                            contentDescription = "Shopping Bag",
                            tint = NaturalTextPrimary,
                            modifier = Modifier.size(22.dp)
                        )
                    }
                }
            }
        }
    }
}
