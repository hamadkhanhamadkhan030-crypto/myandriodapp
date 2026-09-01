package com.example.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
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
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.data.model.Product
import com.example.data.model.ScreenTab
import com.example.data.model.Testimonial
import com.example.ui.theme.*

@Composable
fun ProductCard(
    product: Product,
    isWishlisted: Boolean,
    onProductClick: () -> Unit,
    onWishlistToggle: () -> Unit,
    onQuickAdd: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = PureWhite),
        border = BorderStroke(1.dp, NaturalBorder),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(20.dp))
            .clickable { onProductClick() }
            .testTag("product_card_${product.id}")
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            // Product Image Container with Badges & Wishlist Heart
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(175.dp)
                    .background(NaturalHeroBg)
            ) {
                Image(
                    painter = painterResource(id = product.imageRes),
                    contentDescription = product.title,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )

                // Badge top left in Natural Tones pill
                if (product.badge != null) {
                    Surface(
                        color = NaturalGreen,
                        shape = RoundedCornerShape(100.dp),
                        modifier = Modifier
                            .padding(top = 10.dp, start = 10.dp)
                            .align(Alignment.TopStart)
                    ) {
                        Text(
                            text = product.badge,
                            style = MaterialTheme.typography.labelSmall.copy(
                                color = PureWhite,
                                fontWeight = FontWeight.Bold,
                                fontSize = 9.sp,
                                letterSpacing = 0.5.sp
                            ),
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                        )
                    }
                }

                // Wishlist Icon button top right
                Box(
                    modifier = Modifier
                        .padding(top = 8.dp, end = 8.dp)
                        .size(34.dp)
                        .clip(CircleShape)
                        .background(NaturalPillSage.copy(alpha = 0.92f))
                        .clickable { onWishlistToggle() }
                        .align(Alignment.TopEnd),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = if (isWishlisted) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder,
                        contentDescription = "Save to wishlist",
                        tint = if (isWishlisted) NaturalGreen else NaturalTextBody,
                        modifier = Modifier.size(18.dp)
                    )
                }
            }

            // Info details
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp)
            ) {
                // Category & Rating
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = product.category.uppercase(),
                        style = MaterialTheme.typography.labelSmall.copy(
                            color = NaturalGreen,
                            fontSize = 9.5.sp,
                            fontWeight = FontWeight.Bold,
                            letterSpacing = 0.6.sp
                        )
                    )

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Filled.Star,
                            contentDescription = null,
                            tint = StarRatingGold,
                            modifier = Modifier.size(13.dp)
                        )
                        Spacer(modifier = Modifier.width(3.dp))
                        Text(
                            text = "%.1f".format(product.rating),
                            style = MaterialTheme.typography.labelSmall.copy(
                                fontWeight = FontWeight.Bold,
                                color = NaturalTextPrimary,
                                fontSize = 11.sp
                            )
                        )
                        Text(
                            text = " (${product.reviewCount})",
                            style = MaterialTheme.typography.labelSmall.copy(
                                color = NaturalTextSecondary,
                                fontSize = 10.sp
                            )
                        )
                    }
                }

                Spacer(modifier = Modifier.height(4.dp))

                // Title
                Text(
                    text = product.title,
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Bold,
                        color = NaturalTextPrimary,
                        fontSize = 14.5.sp,
                        lineHeight = 19.sp
                    ),
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.heightIn(min = 38.dp)
                )

                Spacer(modifier = Modifier.height(8.dp))

                // Price & Quick Add Button
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column {
                        Row(verticalAlignment = Alignment.Bottom) {
                            Text(
                                text = "$${"%.2f".format(product.price)}",
                                style = MaterialTheme.typography.titleMedium.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = NaturalTextPrimary,
                                    fontSize = 16.sp
                                )
                            )
                            if (product.compareAtPrice != null) {
                                Spacer(modifier = Modifier.width(5.dp))
                                Text(
                                    text = "$${"%.2f".format(product.compareAtPrice)}",
                                    style = MaterialTheme.typography.bodySmall.copy(
                                        color = NaturalTextSecondary,
                                        textDecoration = TextDecoration.LineThrough,
                                        fontSize = 12.sp
                                    )
                                )
                            }
                        }
                    }

                    // Quick Add button with Natural Tones pill styling
                    Button(
                        onClick = onQuickAdd,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = NaturalPillSage,
                            contentColor = NaturalGreen
                        ),
                        shape = RoundedCornerShape(12.dp),
                        contentPadding = PaddingValues(horizontal = 10.dp, vertical = 6.dp),
                        modifier = Modifier
                            .height(34.dp)
                            .testTag("quick_add_${product.id}")
                    ) {
                        Icon(
                            imageVector = Icons.Default.Add,
                            contentDescription = null,
                            tint = NaturalGreen,
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(3.dp))
                        Text(
                            text = "ADD",
                            style = MaterialTheme.typography.labelSmall.copy(
                                fontWeight = FontWeight.Bold,
                                color = NaturalGreen,
                                letterSpacing = 0.5.sp
                            )
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun BrandScienceSection(
    modifier: Modifier = Modifier
) {
    Surface(
        color = DarkForestGreen,
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 28.dp)
        ) {
            // Header
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(bottom = 6.dp)
            ) {
                Box(
                    modifier = Modifier
                        .size(8.dp)
                        .clip(CircleShape)
                        .background(GoldAccent)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "SCIENCE & INTEGRITY",
                    style = MaterialTheme.typography.labelSmall.copy(
                        color = GoldAccent,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 1.0.sp
                    )
                )
            }

            Text(
                text = "Uncompromising Botanical Purity.",
                style = MaterialTheme.typography.headlineLarge.copy(
                    color = WarmCream,
                    fontWeight = FontWeight.Black
                ),
                modifier = Modifier.padding(bottom = 8.dp)
            )

            Text(
                text = "We bridge centuries of herbal wisdom with advanced modern clinical extraction so your cells receive unmatched bioavailability.",
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = LightBotanicalGreen.copy(alpha = 0.9f),
                    lineHeight = 20.sp
                ),
                modifier = Modifier.padding(bottom = 20.dp)
            )

            Divider(color = BorderDark, thickness = 1.dp)
            Spacer(modifier = Modifier.height(16.dp))

            // 3 Value Pillars
            val pillars = listOf(
                Triple(Icons.Default.Biotech, "Sub-Critical Cold Extraction", "Preserves delicate polyphenols and living enzymes without chemical solvents."),
                Triple(Icons.Default.GppGood, "Triple 3rd-Party Lab Verified", "Every single batch is tested for heavy metals, pesticides, and microbial purity."),
                Triple(Icons.Default.Recycling, "Carbon-Negative Packaging", "Recyclable amber glass and biodegradable compostable pouches.")
            )

            pillars.forEachIndexed { idx, (icon, title, desc) ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 10.dp),
                    verticalAlignment = Alignment.Top
                ) {
                    Box(
                        modifier = Modifier
                            .size(38.dp)
                            .clip(RoundedCornerShape(8.dp))
                            .background(Color(0xFF2E5333)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = icon,
                            contentDescription = null,
                            tint = GoldAccent,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                    Spacer(modifier = Modifier.width(14.dp))
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = title,
                            style = MaterialTheme.typography.titleMedium.copy(
                                color = WarmCream,
                                fontWeight = FontWeight.Bold,
                                fontSize = 15.sp
                            )
                        )
                        Spacer(modifier = Modifier.height(3.dp))
                        Text(
                            text = desc,
                            style = MaterialTheme.typography.bodySmall.copy(
                                color = LightBotanicalGreen.copy(alpha = 0.8f),
                                lineHeight = 18.sp
                            )
                        )
                    }
                }
                if (idx < pillars.size - 1) {
                    Divider(color = BorderDark.copy(alpha = 0.6f), thickness = 0.8.dp)
                }
            }
        }
    }
}

@Composable
fun LifestyleCampaignBanner(
    onExploreClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = WarmCream),
        border = BorderStroke(1.dp, BorderLight),
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 12.dp)
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.img_promo_lifestyle),
                    contentDescription = "Verdant Pure Morning Wellness Routine",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )
                Surface(
                    color = DarkForestGreen.copy(alpha = 0.85f),
                    shape = RoundedCornerShape(topStart = 0.dp, bottomEnd = 12.dp),
                    modifier = Modifier.align(Alignment.TopStart)
                ) {
                    Text(
                        text = "THE MORNING RITUAL",
                        style = MaterialTheme.typography.labelSmall.copy(
                            color = WarmCream,
                            fontWeight = FontWeight.Bold,
                            letterSpacing = 0.8.sp
                        ),
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
                    )
                }
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(18.dp)
            ) {
                Text(
                    text = "A Daily Habit of Unstoppable Energy",
                    style = MaterialTheme.typography.headlineMedium.copy(
                        fontWeight = FontWeight.Bold,
                        color = DarkForestGreen
                    )
                )
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text = "Swap jittery caffeine crashes for pure cellular micro-nutrition. Over 10,000+ conscious achievers start every morning with Verdant Pure.",
                    style = MaterialTheme.typography.bodyMedium.copy(
                        color = TextSecondary,
                        lineHeight = 20.sp
                    )
                )
                Spacer(modifier = Modifier.height(14.dp))
                Button(
                    onClick = onExploreClick,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = BotanicalGreen,
                        contentColor = PureWhite
                    ),
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier.height(44.dp)
                ) {
                    Text(
                        text = "DISCOVER DAILY BUNDLES",
                        style = MaterialTheme.typography.labelLarge.copy(
                            fontWeight = FontWeight.Bold,
                            letterSpacing = 0.6.sp
                        )
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Icon(
                        imageVector = Icons.Default.ArrowForward,
                        contentDescription = null,
                        modifier = Modifier.size(16.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun TestimonialsSection(
    testimonials: List<Testimonial>,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(PureWhite)
            .padding(vertical = 24.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, end = 16.dp, bottom = 14.dp)
        ) {
            Text(
                text = "REAL CLINICAL RESULTS",
                style = MaterialTheme.typography.labelSmall.copy(
                    color = SecondaryGreen,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 1.0.sp
                )
            )
            Text(
                text = "Loved by Practitioners & Customers",
                style = MaterialTheme.typography.headlineMedium.copy(
                    fontWeight = FontWeight.Bold,
                    color = DarkForestGreen
                )
            )
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(top = 4.dp)
            ) {
                Row {
                    repeat(5) {
                        Icon(
                            imageVector = Icons.Filled.Star,
                            contentDescription = null,
                            tint = StarRatingGold,
                            modifier = Modifier.size(16.dp)
                        )
                    }
                }
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "4.95 / 5.0 Average Rating (5,000+ Reviews)",
                    style = MaterialTheme.typography.labelMedium.copy(
                        fontWeight = FontWeight.SemiBold,
                        color = TextPrimary
                    )
                )
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(rememberScrollState())
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            testimonials.forEach { t ->
                Card(
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = WarmCream),
                    border = BorderStroke(1.dp, BorderLight),
                    modifier = Modifier
                        .width(280.dp)
                        .height(200.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp),
                        verticalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween,
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Row {
                                    repeat(t.rating) {
                                        Icon(
                                            imageVector = Icons.Filled.Star,
                                            contentDescription = null,
                                            tint = StarRatingGold,
                                            modifier = Modifier.size(14.dp)
                                        )
                                    }
                                }
                                if (t.verifiedPurchase) {
                                    Surface(
                                        color = LightBotanicalGreen,
                                        shape = RoundedCornerShape(4.dp)
                                    ) {
                                        Text(
                                            text = "VERIFIED",
                                            style = MaterialTheme.typography.labelSmall.copy(
                                                color = DarkForestGreen,
                                                fontSize = 8.5.sp,
                                                fontWeight = FontWeight.Bold
                                            ),
                                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                        )
                                    }
                                }
                            }

                            Spacer(modifier = Modifier.height(10.dp))

                            Text(
                                text = "“${t.quote}”",
                                style = MaterialTheme.typography.bodyMedium.copy(
                                    color = TextPrimary,
                                    lineHeight = 19.sp,
                                    fontWeight = FontWeight.Normal
                                ),
                                maxLines = 4,
                                overflow = TextOverflow.Ellipsis
                            )
                        }

                        Column {
                            Divider(color = BorderLight, thickness = 0.8.dp)
                            Spacer(modifier = Modifier.height(6.dp))
                            Text(
                                text = t.author,
                                style = MaterialTheme.typography.titleMedium.copy(
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 13.sp,
                                    color = DarkForestGreen
                                )
                            )
                            Text(
                                text = "${t.role} • ${t.productPurchased}",
                                style = MaterialTheme.typography.labelSmall.copy(
                                    color = TextMuted,
                                    fontSize = 10.sp
                                )
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun CommunityNewsletterSection(
    onJoinCommunity: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    var emailInput by remember { mutableStateOf("") }
    var isSubmitted by remember { mutableStateOf(false) }

    Surface(
        color = WarmCream,
        border = BorderStroke(1.dp, BorderLight),
        shape = RoundedCornerShape(14.dp),
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 14.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .size(44.dp)
                    .clip(CircleShape)
                    .background(LightBotanicalGreen),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.MailOutline,
                    contentDescription = null,
                    tint = BotanicalGreen,
                    modifier = Modifier.size(24.dp)
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = "JOIN THE VERDANT CIRCLE",
                style = MaterialTheme.typography.labelSmall.copy(
                    color = SecondaryGreen,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 1.0.sp
                )
            )

            Text(
                text = "Enjoy 15% Off Your First Order",
                style = MaterialTheme.typography.headlineMedium.copy(
                    fontWeight = FontWeight.Bold,
                    color = DarkForestGreen,
                    textAlign = TextAlign.Center
                )
            )

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = "Receive physician wellness guides, seasonal botanical recipes, and exclusive private formula drops.",
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = TextSecondary,
                    textAlign = TextAlign.Center,
                    lineHeight = 19.sp
                )
            )

            Spacer(modifier = Modifier.height(16.dp))

            if (!isSubmitted) {
                OutlinedTextField(
                    value = emailInput,
                    onValueChange = { emailInput = it },
                    placeholder = { Text("Enter your email address...", style = MaterialTheme.typography.bodyMedium) },
                    singleLine = true,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedContainerColor = PureWhite,
                        unfocusedContainerColor = PureWhite,
                        focusedBorderColor = BotanicalGreen,
                        unfocusedBorderColor = BorderLight
                    ),
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("newsletter_email_input")
                )

                Spacer(modifier = Modifier.height(10.dp))

                Button(
                    onClick = {
                        if (emailInput.isNotBlank() && emailInput.contains("@")) {
                            isSubmitted = true
                            onJoinCommunity(emailInput)
                        }
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = BotanicalGreen,
                        contentColor = PureWhite
                    ),
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(46.dp)
                        .testTag("newsletter_submit_button")
                ) {
                    Text(
                        text = "CLAIM 15% OFF (CODE: VITAL15)",
                        style = MaterialTheme.typography.labelLarge.copy(
                            letterSpacing = 0.5.sp
                        )
                    )
                }

                Spacer(modifier = Modifier.height(6.dp))

                Text(
                    text = "No spam ever. Unsubscribe with 1-click anytime.",
                    style = MaterialTheme.typography.labelSmall.copy(
                        color = TextMuted,
                        fontSize = 10.sp
                    )
                )
            } else {
                Surface(
                    color = LightBotanicalGreen,
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier.padding(14.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.CheckCircle,
                            contentDescription = null,
                            tint = BotanicalGreen,
                            modifier = Modifier.size(22.dp)
                        )
                        Spacer(modifier = Modifier.width(10.dp))
                        Column {
                            Text(
                                text = "Welcome to the Community!",
                                style = MaterialTheme.typography.titleMedium.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = DarkForestGreen,
                                    fontSize = 14.sp
                                )
                            )
                            Text(
                                text = "Use code VITAL15 at checkout for 15% off.",
                                style = MaterialTheme.typography.bodySmall.copy(
                                    color = DarkForestGreen
                                )
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun FooterSection(
    onNavigateTab: (ScreenTab) -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        color = PureWhite,
        border = BorderStroke(1.dp, BorderLight),
        modifier = modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp)
        ) {
            // Brand & Summary
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(30.dp)
                        .clip(RoundedCornerShape(6.dp))
                        .background(BotanicalGreen),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Spa,
                        contentDescription = null,
                        tint = WarmCream,
                        modifier = Modifier.size(18.dp)
                    )
                }
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "VERDANT PURE",
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Black,
                        letterSpacing = 1.0.sp,
                        color = DarkForestGreen
                    )
                )
            }

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = "Crafted with pristine organic botanicals, non-GMO wholefoods, and triple lab-tested purity to empower your peak human health and longevity.",
                style = MaterialTheme.typography.bodySmall.copy(
                    color = TextSecondary,
                    lineHeight = 18.sp
                )
            )

            Spacer(modifier = Modifier.height(16.dp))
            Divider(color = BorderLight, thickness = 0.8.dp)
            Spacer(modifier = Modifier.height(16.dp))

            // Quick Nav Links
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = "DISCOVER",
                        style = MaterialTheme.typography.labelMedium.copy(
                            fontWeight = FontWeight.Bold,
                            color = DarkForestGreen,
                            letterSpacing = 0.8.sp
                        )
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text("All Formulas", style = MaterialTheme.typography.bodySmall, modifier = Modifier.clickable { onNavigateTab(ScreenTab.SHOP) })
                    Spacer(modifier = Modifier.height(6.dp))
                    Text("Daily Greens", style = MaterialTheme.typography.bodySmall, modifier = Modifier.clickable { onNavigateTab(ScreenTab.SHOP) })
                    Spacer(modifier = Modifier.height(6.dp))
                    Text("Plant Collagen", style = MaterialTheme.typography.bodySmall, modifier = Modifier.clickable { onNavigateTab(ScreenTab.SHOP) })
                }

                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = "STANDARDS",
                        style = MaterialTheme.typography.labelMedium.copy(
                            fontWeight = FontWeight.Bold,
                            color = DarkForestGreen,
                            letterSpacing = 0.8.sp
                        )
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text("3rd Party Lab Reports", style = MaterialTheme.typography.bodySmall)
                    Spacer(modifier = Modifier.height(6.dp))
                    Text("Organic Sourcing", style = MaterialTheme.typography.bodySmall)
                    Spacer(modifier = Modifier.height(6.dp))
                    Text("30-Day Guarantee", style = MaterialTheme.typography.bodySmall)
                }
            }

            Spacer(modifier = Modifier.height(18.dp))
            Divider(color = BorderLight, thickness = 0.8.dp)
            Spacer(modifier = Modifier.height(14.dp))

            // Guarantees & Copyright
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "© 2026 Verdant Pure Labs Inc.",
                    style = MaterialTheme.typography.labelSmall.copy(
                        color = TextMuted,
                        fontSize = 10.5.sp
                    )
                )

                Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                    Surface(color = SoftBeige, shape = RoundedCornerShape(4.dp)) {
                        Text(
                            text = "SECURE SSL",
                            style = MaterialTheme.typography.labelSmall.copy(
                                fontSize = 8.5.sp,
                                color = DarkForestGreen,
                                fontWeight = FontWeight.Bold
                            ),
                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun CommerceBottomNav(
    currentTab: ScreenTab,
    cartCount: Int,
    wishlistCount: Int,
    onTabSelected: (ScreenTab) -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        color = NaturalSurface,
        shape = RoundedCornerShape(topStart = 28.dp, topEnd = 28.dp),
        border = BorderStroke(1.dp, NaturalBorder),
        modifier = modifier.fillMaxWidth()
    ) {
        NavigationBar(
            containerColor = NaturalSurface,
            contentColor = NaturalTextPrimary,
            tonalElevation = 0.dp,
            modifier = Modifier.windowInsetsPadding(WindowInsets.navigationBars)
        ) {
            NavigationBarItem(
                selected = currentTab == ScreenTab.HOME,
                onClick = { onTabSelected(ScreenTab.HOME) },
                icon = {
                    Icon(
                        imageVector = if (currentTab == ScreenTab.HOME) Icons.Filled.Home else Icons.Outlined.Home,
                        contentDescription = "Home"
                    )
                },
                label = { Text("Home", fontSize = 11.sp, fontWeight = if (currentTab == ScreenTab.HOME) FontWeight.Bold else FontWeight.Medium) },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = NaturalGreen,
                    selectedTextColor = NaturalTextPrimary,
                    indicatorColor = NaturalPillSage,
                    unselectedIconColor = NaturalTextBody.copy(alpha = 0.65f),
                    unselectedTextColor = NaturalTextSecondary
                )
            )

            NavigationBarItem(
                selected = currentTab == ScreenTab.SHOP,
                onClick = { onTabSelected(ScreenTab.SHOP) },
                icon = {
                    Icon(
                        imageVector = if (currentTab == ScreenTab.SHOP) Icons.Filled.Storefront else Icons.Outlined.Storefront,
                        contentDescription = "Shop"
                    )
                },
                label = { Text("Shop", fontSize = 11.sp, fontWeight = if (currentTab == ScreenTab.SHOP) FontWeight.Bold else FontWeight.Medium) },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = NaturalGreen,
                    selectedTextColor = NaturalTextPrimary,
                    indicatorColor = NaturalPillSage,
                    unselectedIconColor = NaturalTextBody.copy(alpha = 0.65f),
                    unselectedTextColor = NaturalTextSecondary
                )
            )

            NavigationBarItem(
                selected = currentTab == ScreenTab.SEARCH,
                onClick = { onTabSelected(ScreenTab.SEARCH) },
                icon = {
                    Icon(
                        imageVector = if (currentTab == ScreenTab.SEARCH) Icons.Filled.Search else Icons.Outlined.Search,
                        contentDescription = "Search"
                    )
                },
                label = { Text("Search", fontSize = 11.sp, fontWeight = if (currentTab == ScreenTab.SEARCH) FontWeight.Bold else FontWeight.Medium) },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = NaturalGreen,
                    selectedTextColor = NaturalTextPrimary,
                    indicatorColor = NaturalPillSage,
                    unselectedIconColor = NaturalTextBody.copy(alpha = 0.65f),
                    unselectedTextColor = NaturalTextSecondary
                )
            )

            NavigationBarItem(
                selected = currentTab == ScreenTab.WISHLIST,
                onClick = { onTabSelected(ScreenTab.WISHLIST) },
                icon = {
                    BadgedBox(
                        badge = {
                            if (wishlistCount > 0) {
                                Badge(
                                    containerColor = NaturalPillSage,
                                    contentColor = NaturalGreen
                                ) {
                                    Text(text = wishlistCount.toString(), fontSize = 9.sp, fontWeight = FontWeight.Bold)
                                }
                            }
                        }
                    ) {
                        Icon(
                            imageVector = if (currentTab == ScreenTab.WISHLIST) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder,
                            contentDescription = "Saved"
                        )
                    }
                },
                label = { Text("Saved", fontSize = 11.sp, fontWeight = if (currentTab == ScreenTab.WISHLIST) FontWeight.Bold else FontWeight.Medium) },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = NaturalGreen,
                    selectedTextColor = NaturalTextPrimary,
                    indicatorColor = NaturalPillSage,
                    unselectedIconColor = NaturalTextBody.copy(alpha = 0.65f),
                    unselectedTextColor = NaturalTextSecondary
                )
            )

            NavigationBarItem(
                selected = currentTab == ScreenTab.ACCOUNT,
                onClick = { onTabSelected(ScreenTab.ACCOUNT) },
                icon = {
                    Icon(
                        imageVector = if (currentTab == ScreenTab.ACCOUNT) Icons.Filled.Person else Icons.Outlined.Person,
                        contentDescription = "Account"
                    )
                },
                label = { Text("Account", fontSize = 11.sp, fontWeight = if (currentTab == ScreenTab.ACCOUNT) FontWeight.Bold else FontWeight.Medium) },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = NaturalGreen,
                    selectedTextColor = NaturalTextPrimary,
                    indicatorColor = NaturalPillSage,
                    unselectedIconColor = NaturalTextBody.copy(alpha = 0.65f),
                    unselectedTextColor = NaturalTextSecondary
                )
            )
        }
    }
}
