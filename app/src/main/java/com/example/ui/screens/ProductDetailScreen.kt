package com.example.ui.screens

import androidx.compose.animation.*
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
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
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.Product
import com.example.ui.CommerceViewModel
import com.example.ui.components.ProductCard
import com.example.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductDetailScreen(
    product: Product,
    viewModel: CommerceViewModel,
    isWishlisted: Boolean,
    onBackClick: () -> Unit,
    onSelectRelatedProduct: (Product) -> Unit,
    modifier: Modifier = Modifier
) {
    var selectedSize by remember(product.id) { mutableStateOf(product.sizes.firstOrNull() ?: "30 Servings") }
    var quantity by remember(product.id) { mutableIntStateOf(1) }

    // Accordion Expansion States
    var isBenefitsExpanded by remember { mutableStateOf(true) }
    var isIngredientsExpanded by remember { mutableStateOf(false) }
    var isHowToUseExpanded by remember { mutableStateOf(false) }
    var isTestingExpanded by remember { mutableStateOf(false) }
    var isShippingExpanded by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = product.category.uppercase(),
                        style = MaterialTheme.typography.labelSmall.copy(
                            color = SecondaryGreen,
                            fontWeight = FontWeight.Bold,
                            letterSpacing = 1.0.sp
                        )
                    )
                },
                navigationIcon = {
                    IconButton(
                        onClick = onBackClick,
                        modifier = Modifier.testTag("detail_back_button")
                    ) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back",
                            tint = DarkForestGreen
                        )
                    }
                },
                actions = {
                    IconButton(
                        onClick = { viewModel.toggleWishlist(product.id) },
                        modifier = Modifier.testTag("detail_wishlist_button")
                    ) {
                        Icon(
                            imageVector = if (isWishlisted) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder,
                            contentDescription = "Wishlist",
                            tint = if (isWishlisted) BotanicalGreen else DarkForestGreen
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = PureWhite
                )
            )
        },
        bottomBar = {
            // Sticky Purchase Bar
            Surface(
                color = PureWhite,
                shadowElevation = 12.dp,
                border = BorderStroke(1.dp, BorderLight),
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 12.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Column {
                        Text(
                            text = "TOTAL PRICE",
                            style = MaterialTheme.typography.labelSmall.copy(
                                color = TextMuted,
                                fontSize = 9.sp
                            )
                        )
                        Text(
                            text = "$${"%.2f".format(product.price * quantity)}",
                            style = MaterialTheme.typography.titleLarge.copy(
                                fontWeight = FontWeight.Black,
                                color = DarkForestGreen
                            )
                        )
                    }

                    Button(
                        onClick = {
                            viewModel.addToCart(product, selectedSize, quantity)
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = BotanicalGreen,
                            contentColor = PureWhite
                        ),
                        shape = RoundedCornerShape(8.dp),
                        modifier = Modifier
                            .weight(1f)
                            .height(48.dp)
                            .testTag("detail_add_to_cart_button")
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.ShoppingBag,
                            contentDescription = null,
                            modifier = Modifier.size(18.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "ADD TO BAG",
                            style = MaterialTheme.typography.labelLarge.copy(
                                letterSpacing = 0.8.sp
                            )
                        )
                    }
                }
            }
        },
        modifier = modifier.fillMaxSize()
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(WarmCream)
                .padding(innerPadding)
        ) {
            // Hero Product Image Banner
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(300.dp)
                        .background(WarmCream)
                ) {
                    Image(
                        painter = painterResource(id = product.imageRes),
                        contentDescription = product.title,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize()
                    )

                    if (product.badge != null) {
                        Surface(
                            color = BotanicalGreen,
                            shape = RoundedCornerShape(4.dp),
                            modifier = Modifier
                                .padding(16.dp)
                                .align(Alignment.TopStart)
                        ) {
                            Text(
                                text = product.badge,
                                style = MaterialTheme.typography.labelSmall.copy(
                                    color = PureWhite,
                                    fontWeight = FontWeight.Bold,
                                    letterSpacing = 0.6.sp
                                ),
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                            )
                        }
                    }
                }
            }

            // Certifications Badges Bar
            item {
                Surface(
                    color = LightBotanicalGreen,
                    border = BorderStroke(1.dp, SageGreen.copy(alpha = 0.3f)),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 8.dp),
                        horizontalArrangement = Arrangement.SpaceAround,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        product.certs.forEach { cert ->
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    imageVector = Icons.Default.Check,
                                    contentDescription = null,
                                    tint = BotanicalGreen,
                                    modifier = Modifier.size(14.dp)
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(
                                    text = cert,
                                    style = MaterialTheme.typography.labelSmall.copy(
                                        color = DarkForestGreen,
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 10.sp
                                    )
                                )
                            }
                        }
                    }
                }
            }

            // Core Product Information Card
            item {
                Surface(
                    color = PureWhite,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(18.dp)
                    ) {
                        // Rating & Reviews
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(6.dp)
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
                            Text(
                                text = "${product.rating} (${product.reviewCount} Verified Reviews)",
                                style = MaterialTheme.typography.labelMedium.copy(
                                    fontWeight = FontWeight.SemiBold,
                                    color = TextPrimary
                                )
                            )
                        }

                        Spacer(modifier = Modifier.height(8.dp))

                        // Title & Subtitle
                        Text(
                            text = product.title,
                            style = MaterialTheme.typography.headlineLarge.copy(
                                fontWeight = FontWeight.Black,
                                color = DarkForestGreen
                            )
                        )

                        Spacer(modifier = Modifier.height(4.dp))

                        Text(
                            text = product.subtitle,
                            style = MaterialTheme.typography.bodyLarge.copy(
                                color = SecondaryGreen,
                                fontWeight = FontWeight.Medium
                            )
                        )

                        Spacer(modifier = Modifier.height(14.dp))

                        // Price Row
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            Text(
                                text = "$${"%.2f".format(product.price)}",
                                style = MaterialTheme.typography.displayMedium.copy(
                                    fontWeight = FontWeight.Black,
                                    color = DarkForestGreen
                                )
                            )

                            if (product.compareAtPrice != null) {
                                Text(
                                    text = "$${"%.2f".format(product.compareAtPrice)}",
                                    style = MaterialTheme.typography.titleMedium.copy(
                                        color = TextMuted,
                                        textDecoration = TextDecoration.LineThrough
                                    )
                                )
                                Surface(
                                    color = LightBotanicalGreen,
                                    shape = RoundedCornerShape(4.dp)
                                ) {
                                    Text(
                                        text = "SAVE $${"%.0f".format(product.compareAtPrice - product.price)}",
                                        style = MaterialTheme.typography.labelSmall.copy(
                                            color = BotanicalGreen,
                                            fontWeight = FontWeight.Bold
                                        ),
                                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 3.dp)
                                    )
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(16.dp))
                        Divider(color = BorderLight, thickness = 0.8.dp)
                        Spacer(modifier = Modifier.height(16.dp))

                        // Variant / Size Selector
                        Text(
                            text = "SELECT SUPPLY SIZE",
                            style = MaterialTheme.typography.labelSmall.copy(
                                color = DarkForestGreen,
                                fontWeight = FontWeight.Bold,
                                letterSpacing = 0.8.sp
                            )
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            product.sizes.forEach { size ->
                                val isSelected = selectedSize == size
                                Surface(
                                    color = if (isSelected) LightBotanicalGreen else SoftBeige,
                                    shape = RoundedCornerShape(8.dp),
                                    border = BorderStroke(
                                        width = if (isSelected) 2.dp else 1.dp,
                                        color = if (isSelected) BotanicalGreen else BorderLight
                                    ),
                                    modifier = Modifier
                                        .weight(1f)
                                        .clickable { selectedSize = size }
                                ) {
                                    Text(
                                        text = size,
                                        style = MaterialTheme.typography.labelMedium.copy(
                                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                                            color = if (isSelected) DarkForestGreen else TextPrimary
                                        ),
                                        modifier = Modifier.padding(vertical = 10.dp, horizontal = 6.dp)
                                    )
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        // Quantity Stepper
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(
                                text = "QUANTITY",
                                style = MaterialTheme.typography.labelSmall.copy(
                                    color = DarkForestGreen,
                                    fontWeight = FontWeight.Bold,
                                    letterSpacing = 0.8.sp
                                )
                            )

                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier
                                    .border(1.dp, BorderLight, RoundedCornerShape(6.dp))
                                    .padding(horizontal = 4.dp, vertical = 2.dp)
                            ) {
                                IconButton(
                                    onClick = { if (quantity > 1) quantity-- },
                                    modifier = Modifier.size(32.dp)
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Remove,
                                        contentDescription = "Decrease",
                                        tint = TextPrimary,
                                        modifier = Modifier.size(16.dp)
                                    )
                                }

                                Text(
                                    text = quantity.toString(),
                                    style = MaterialTheme.typography.titleMedium.copy(
                                        fontWeight = FontWeight.Bold,
                                        color = DarkForestGreen
                                    ),
                                    modifier = Modifier.padding(horizontal = 12.dp)
                                )

                                IconButton(
                                    onClick = { quantity++ },
                                    modifier = Modifier.size(32.dp)
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Add,
                                        contentDescription = "Increase",
                                        tint = TextPrimary,
                                        modifier = Modifier.size(16.dp)
                                    )
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        // Free Delivery Banner
                        Surface(
                            color = SoftBeige,
                            shape = RoundedCornerShape(8.dp),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Row(
                                modifier = Modifier.padding(12.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    imageVector = Icons.Outlined.LocalShipping,
                                    contentDescription = null,
                                    tint = BotanicalGreen,
                                    modifier = Modifier.size(20.dp)
                                )
                                Spacer(modifier = Modifier.width(10.dp))
                                Text(
                                    text = "Carbon-neutral shipping • Free on orders over $50",
                                    style = MaterialTheme.typography.bodySmall.copy(
                                        color = DarkForestGreen,
                                        fontWeight = FontWeight.Medium
                                    )
                                )
                            }
                        }
                    }
                }
            }

            item {
                Spacer(modifier = Modifier.height(12.dp))
            }

            // Expandable Accordions Section
            item {
                Surface(
                    color = PureWhite,
                    border = BorderStroke(1.dp, BorderLight),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.fillMaxWidth()) {
                        // 1. Clinical Benefits
                        AccordionItem(
                            title = "KEY CLINICAL BENEFITS",
                            isExpanded = isBenefitsExpanded,
                            onToggle = { isBenefitsExpanded = !isBenefitsExpanded }
                        ) {
                            Column(modifier = Modifier.padding(horizontal = 16.dp, vertical = 10.dp)) {
                                product.benefits.forEach { benefit ->
                                    Row(
                                        modifier = Modifier.padding(vertical = 4.dp),
                                        verticalAlignment = Alignment.Top
                                    ) {
                                        Icon(
                                            imageVector = Icons.Default.CheckCircle,
                                            contentDescription = null,
                                            tint = BotanicalGreen,
                                            modifier = Modifier.size(16.dp)
                                        )
                                        Spacer(modifier = Modifier.width(8.dp))
                                        Text(
                                            text = benefit,
                                            style = MaterialTheme.typography.bodyMedium.copy(
                                                color = TextPrimary,
                                                lineHeight = 20.sp
                                            )
                                        )
                                    }
                                }
                            }
                        }

                        Divider(color = BorderLight, thickness = 0.8.dp)

                        // 2. Ingredients
                        AccordionItem(
                            title = "100% PURE INGREDIENTS",
                            isExpanded = isIngredientsExpanded,
                            onToggle = { isIngredientsExpanded = !isIngredientsExpanded }
                        ) {
                            Column(modifier = Modifier.padding(horizontal = 16.dp, vertical = 10.dp)) {
                                Text(
                                    text = product.ingredients,
                                    style = MaterialTheme.typography.bodyMedium.copy(
                                        color = TextSecondary,
                                        lineHeight = 22.sp
                                    )
                                )
                            }
                        }

                        Divider(color = BorderLight, thickness = 0.8.dp)

                        // 3. How to Use
                        AccordionItem(
                            title = "SUGGESTED USE & DOSAGE",
                            isExpanded = isHowToUseExpanded,
                            onToggle = { isHowToUseExpanded = !isHowToUseExpanded }
                        ) {
                            Column(modifier = Modifier.padding(horizontal = 16.dp, vertical = 10.dp)) {
                                Text(
                                    text = product.howToUse,
                                    style = MaterialTheme.typography.bodyMedium.copy(
                                        color = TextSecondary,
                                        lineHeight = 22.sp
                                    )
                                )
                            }
                        }

                        Divider(color = BorderLight, thickness = 0.8.dp)

                        // 4. Lab Certifications
                        AccordionItem(
                            title = "TRIPLE 3RD-PARTY LAB VERIFIED",
                            isExpanded = isTestingExpanded,
                            onToggle = { isTestingExpanded = !isTestingExpanded }
                        ) {
                            Column(modifier = Modifier.padding(horizontal = 16.dp, vertical = 10.dp)) {
                                Text(
                                    text = "Every production run is independently tested by ISO-certified laboratory Eurofins for heavy metal toxicity (Lead, Cadmium, Arsenic, Mercury), microbial purity, and guaranteed active polyphenol potencies. Complete Certificate of Analysis (COA) is available on batch barcode scan.",
                                    style = MaterialTheme.typography.bodyMedium.copy(
                                        color = TextSecondary,
                                        lineHeight = 22.sp
                                    )
                                )
                            }
                        }

                        Divider(color = BorderLight, thickness = 0.8.dp)

                        // 5. Shipping & Returns
                        AccordionItem(
                            title = "SHIPPING & 30-DAY GUARANTEE",
                            isExpanded = isShippingExpanded,
                            onToggle = { isShippingExpanded = !isShippingExpanded }
                        ) {
                            Column(modifier = Modifier.padding(horizontal = 16.dp, vertical = 10.dp)) {
                                Text(
                                    text = "Standard delivery ships within 24 business hours in temperature-stabilized biodegradable containers. Try Verdant Pure risk-free: if you don't feel the vitality difference within 30 days, we'll issue a 100% refund with zero questions asked.",
                                    style = MaterialTheme.typography.bodyMedium.copy(
                                        color = TextSecondary,
                                        lineHeight = 22.sp
                                    )
                                )
                            }
                        }
                    }
                }
            }

            // Related Products Carousel
            item {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 24.dp, bottom = 16.dp)
                ) {
                    Text(
                        text = "FREQUENTLY PAIRED WITH",
                        style = MaterialTheme.typography.labelSmall.copy(
                            color = SecondaryGreen,
                            fontWeight = FontWeight.Bold,
                            letterSpacing = 1.0.sp
                        ),
                        modifier = Modifier.padding(horizontal = 16.dp)
                    )
                    Text(
                        text = "Complete Your Daily Ritual",
                        style = MaterialTheme.typography.titleLarge.copy(
                            fontWeight = FontWeight.Bold,
                            color = DarkForestGreen
                        ),
                        modifier = Modifier.padding(start = 16.dp, end = 16.dp, bottom = 12.dp)
                    )

                    val related = viewModel.allProducts.filter { it.id != product.id }.take(3)
                    LazyRow(
                        contentPadding = PaddingValues(horizontal = 16.dp),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        items(related, key = { it.id }) { rel ->
                            ProductCard(
                                product = rel,
                                isWishlisted = viewModel.wishlistIds.value.contains(rel.id),
                                onProductClick = { onSelectRelatedProduct(rel) },
                                onWishlistToggle = { viewModel.toggleWishlist(rel.id) },
                                onQuickAdd = { viewModel.addToCart(rel) },
                                modifier = Modifier.width(200.dp)
                            )
                        }
                    }
                }
            }

            item {
                Spacer(modifier = Modifier.height(20.dp))
            }
        }
    }
}

@Composable
private fun AccordionItem(
    title: String,
    isExpanded: Boolean,
    onToggle: () -> Unit,
    content: @Composable () -> Unit
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { onToggle() }
                .padding(horizontal = 16.dp, vertical = 14.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.labelMedium.copy(
                    fontWeight = FontWeight.Bold,
                    color = DarkForestGreen,
                    letterSpacing = 0.6.sp
                )
            )

            Icon(
                imageVector = if (isExpanded) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                contentDescription = if (isExpanded) "Collapse" else "Expand",
                tint = BotanicalGreen
            )
        }

        AnimatedVisibility(
            visible = isExpanded,
            enter = expandVertically() + fadeIn(),
            exit = shrinkVertically() + fadeOut()
        ) {
            content()
        }
    }
}
