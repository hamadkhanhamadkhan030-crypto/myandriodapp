package com.example.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.CartItem
import com.example.ui.CommerceViewModel
import com.example.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CartDrawerSheet(
    viewModel: CommerceViewModel,
    cartItems: List<CartItem>,
    subtotal: Double,
    discount: Double,
    shippingFee: Double,
    total: Double,
    freeShippingProgress: Float,
    amountUntilFreeShipping: Double,
    appliedPromoCode: String?,
    onClose: () -> Unit,
    onStartCheckout: () -> Unit,
    onExploreShop: () -> Unit,
    modifier: Modifier = Modifier
) {
    var promoInput by remember { mutableStateOf("") }

    ModalBottomSheet(
        onDismissRequest = onClose,
        containerColor = WarmCream,
        dragHandle = {
            BottomSheetDefaults.DragHandle(color = SageGreen)
        },
        shape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp),
        modifier = modifier.fillMaxHeight(0.92f)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .navigationBarsPadding()
        ) {
            // Header
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 6.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Outlined.ShoppingBag,
                        contentDescription = null,
                        tint = DarkForestGreen,
                        modifier = Modifier.size(24.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Your Wellness Bag",
                        style = MaterialTheme.typography.titleLarge.copy(
                            fontWeight = FontWeight.Black,
                            color = DarkForestGreen
                        )
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Surface(
                        color = LightBotanicalGreen,
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Text(
                            text = "${cartItems.sumOf { it.quantity }} items",
                            style = MaterialTheme.typography.labelSmall.copy(
                                color = DarkForestGreen,
                                fontWeight = FontWeight.Bold
                            ),
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp)
                        )
                    }
                }

                IconButton(
                    onClick = onClose,
                    modifier = Modifier.testTag("cart_close_button")
                ) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "Close",
                        tint = TextSecondary
                    )
                }
            }

            Divider(color = BorderLight, thickness = 1.dp)

            // Free Shipping Progress Bar
            Surface(
                color = PureWhite,
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp, vertical = 12.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = if (freeShippingProgress >= 1f) Icons.Default.CheckCircle else Icons.Outlined.LocalShipping,
                                contentDescription = null,
                                tint = if (freeShippingProgress >= 1f) SuccessGreen else BotanicalGreen,
                                modifier = Modifier.size(16.dp)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = if (freeShippingProgress >= 1f) {
                                    "Unlocked Free Carbon-Neutral Shipping!"
                                } else {
                                    "Add $${"%.2f".format(amountUntilFreeShipping)} for FREE Delivery"
                                },
                                style = MaterialTheme.typography.labelMedium.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = if (freeShippingProgress >= 1f) SuccessGreen else DarkForestGreen
                                )
                            )
                        }

                        Text(
                            text = "${(freeShippingProgress * 100).toInt()}%",
                            style = MaterialTheme.typography.labelSmall.copy(
                                color = TextMuted,
                                fontWeight = FontWeight.Bold
                            )
                        )
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    LinearProgressIndicator(
                        progress = { freeShippingProgress },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(6.dp)
                            .clip(RoundedCornerShape(3.dp)),
                        color = BotanicalGreen,
                        trackColor = SoftBeige
                    )
                }
            }

            Divider(color = BorderLight, thickness = 1.dp)

            if (cartItems.isEmpty()) {
                // Empty Cart State
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .padding(32.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Box(
                            modifier = Modifier
                                .size(70.dp)
                                .clip(CircleShape)
                                .background(SoftBeige),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Outlined.ShoppingBag,
                                contentDescription = null,
                                tint = SecondaryGreen,
                                modifier = Modifier.size(36.dp)
                            )
                        }

                        Spacer(modifier = Modifier.height(14.dp))

                        Text(
                            text = "Your Wellness Bag is Empty",
                            style = MaterialTheme.typography.titleMedium.copy(
                                fontWeight = FontWeight.Bold,
                                color = DarkForestGreen
                            )
                        )

                        Spacer(modifier = Modifier.height(6.dp))

                        Text(
                            text = "Discover our bio-active botanicals and superfoods to begin your vitality journey.",
                            style = MaterialTheme.typography.bodyMedium.copy(
                                color = TextSecondary,
                                textAlign = TextAlign.Center
                            )
                        )

                        Spacer(modifier = Modifier.height(20.dp))

                        Button(
                            onClick = {
                                onClose()
                                onExploreShop()
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = BotanicalGreen),
                            shape = RoundedCornerShape(8.dp),
                            modifier = Modifier.height(44.dp)
                        ) {
                            Text(
                                text = "EXPLORE FORMULAS",
                                style = MaterialTheme.typography.labelLarge.copy(
                                    letterSpacing = 0.6.sp
                                )
                            )
                        }
                    }
                }
            } else {
                // Cart Items List
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .padding(horizontal = 16.dp, vertical = 8.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    items(cartItems, key = { "${it.product.id}_${it.selectedSize}" }) { item ->
                        Card(
                            shape = RoundedCornerShape(10.dp),
                            colors = CardDefaults.cardColors(containerColor = PureWhite),
                            border = BorderStroke(1.dp, BorderLight),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(12.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                // Thumbnail
                                Image(
                                    painter = painterResource(id = item.product.imageRes),
                                    contentDescription = item.product.title,
                                    contentScale = ContentScale.Crop,
                                    modifier = Modifier
                                        .size(68.dp)
                                        .clip(RoundedCornerShape(8.dp))
                                        .background(WarmCream)
                                )

                                Spacer(modifier = Modifier.width(12.dp))

                                Column(modifier = Modifier.weight(1f)) {
                                    Text(
                                        text = item.product.title,
                                        style = MaterialTheme.typography.titleMedium.copy(
                                            fontWeight = FontWeight.Bold,
                                            fontSize = 13.5.sp,
                                            color = DarkForestGreen,
                                            lineHeight = 17.sp
                                        ),
                                        maxLines = 2,
                                        overflow = TextOverflow.Ellipsis
                                    )

                                    Spacer(modifier = Modifier.height(3.dp))

                                    Surface(
                                        color = SoftBeige,
                                        shape = RoundedCornerShape(4.dp)
                                    ) {
                                        Text(
                                            text = item.selectedSize,
                                            style = MaterialTheme.typography.labelSmall.copy(
                                                color = TextSecondary,
                                                fontSize = 9.5.sp
                                            ),
                                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                        )
                                    }

                                    Spacer(modifier = Modifier.height(6.dp))

                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.SpaceBetween,
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Text(
                                            text = "$${"%.2f".format(item.product.price)}",
                                            style = MaterialTheme.typography.titleMedium.copy(
                                                fontWeight = FontWeight.Black,
                                                color = BotanicalGreen,
                                                fontSize = 15.sp
                                            )
                                        )

                                        // Stepper Controls
                                        Row(
                                            verticalAlignment = Alignment.CenterVertically,
                                            modifier = Modifier
                                                .border(1.dp, BorderLight, RoundedCornerShape(4.dp))
                                                .padding(horizontal = 2.dp)
                                        ) {
                                            IconButton(
                                                onClick = {
                                                    viewModel.updateCartQuantity(item.product, item.selectedSize, item.quantity - 1)
                                                },
                                                modifier = Modifier.size(26.dp)
                                            ) {
                                                Icon(
                                                    imageVector = Icons.Default.Remove,
                                                    contentDescription = "Decrease",
                                                    modifier = Modifier.size(12.dp)
                                                )
                                            }

                                            Text(
                                                text = item.quantity.toString(),
                                                style = MaterialTheme.typography.labelMedium.copy(
                                                    fontWeight = FontWeight.Bold,
                                                    fontSize = 12.sp
                                                ),
                                                modifier = Modifier.padding(horizontal = 6.dp)
                                            )

                                            IconButton(
                                                onClick = {
                                                    viewModel.updateCartQuantity(item.product, item.selectedSize, item.quantity + 1)
                                                },
                                                modifier = Modifier.size(26.dp)
                                            ) {
                                                Icon(
                                                    imageVector = Icons.Default.Add,
                                                    contentDescription = "Increase",
                                                    modifier = Modifier.size(12.dp)
                                                )
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }

                    // Promo Code Card
                    item {
                        Surface(
                            color = PureWhite,
                            shape = RoundedCornerShape(10.dp),
                            border = BorderStroke(1.dp, BorderLight),
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.dp)
                        ) {
                            Column(modifier = Modifier.padding(12.dp)) {
                                Text(
                                    text = "PROMO CODE / GIFT CARD",
                                    style = MaterialTheme.typography.labelSmall.copy(
                                        color = DarkForestGreen,
                                        fontWeight = FontWeight.Bold,
                                        letterSpacing = 0.6.sp
                                    )
                                )
                                Spacer(modifier = Modifier.height(6.dp))

                                if (appliedPromoCode != null) {
                                    Row(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .background(LightBotanicalGreen, RoundedCornerShape(6.dp))
                                            .padding(horizontal = 10.dp, vertical = 8.dp),
                                        horizontalArrangement = Arrangement.SpaceBetween,
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Row(verticalAlignment = Alignment.CenterVertically) {
                                            Icon(
                                                imageVector = Icons.Default.CheckCircle,
                                                contentDescription = null,
                                                tint = BotanicalGreen,
                                                modifier = Modifier.size(16.dp)
                                            )
                                            Spacer(modifier = Modifier.width(6.dp))
                                            Text(
                                                text = "Code '$appliedPromoCode' Applied",
                                                style = MaterialTheme.typography.labelMedium.copy(
                                                    color = DarkForestGreen,
                                                    fontWeight = FontWeight.Bold
                                                )
                                            )
                                        }

                                        Text(
                                            text = "Remove",
                                            style = MaterialTheme.typography.labelSmall.copy(
                                                color = ErrorRed,
                                                fontWeight = FontWeight.Bold
                                            ),
                                            modifier = Modifier.clickable { viewModel.removePromoCode() }
                                        )
                                    }
                                } else {
                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        OutlinedTextField(
                                            value = promoInput,
                                            onValueChange = { promoInput = it },
                                            placeholder = { Text("e.g. VITAL15", fontSize = 12.sp) },
                                            singleLine = true,
                                            modifier = Modifier
                                                .weight(1f)
                                                .height(44.dp),
                                            colors = OutlinedTextFieldDefaults.colors(
                                                focusedBorderColor = BotanicalGreen,
                                                unfocusedBorderColor = BorderLight
                                            ),
                                            shape = RoundedCornerShape(6.dp)
                                        )

                                        Button(
                                            onClick = {
                                                if (promoInput.isNotBlank()) {
                                                    viewModel.applyPromoCode(promoInput)
                                                    promoInput = ""
                                                }
                                            },
                                            colors = ButtonDefaults.buttonColors(containerColor = BotanicalGreen),
                                            shape = RoundedCornerShape(6.dp),
                                            modifier = Modifier.height(44.dp)
                                        ) {
                                            Text("APPLY", fontSize = 11.sp, fontWeight = FontWeight.Bold)
                                        }
                                    }
                                }
                            }
                        }
                    }
                }

                // Summary & Checkout Drawer Bottom
                Surface(
                    color = PureWhite,
                    shadowElevation = 14.dp,
                    border = BorderStroke(1.dp, BorderLight),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 20.dp, vertical = 14.dp)
                    ) {
                        // Subtotal
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text("Subtotal", style = MaterialTheme.typography.bodyMedium)
                            Text("$${"%.2f".format(subtotal)}", style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.SemiBold, color = TextPrimary))
                        }

                        // Discount
                        if (discount > 0.0) {
                            Spacer(modifier = Modifier.height(4.dp))
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text("Promotional Discount", style = MaterialTheme.typography.bodyMedium.copy(color = SuccessGreen))
                                Text("-$${"%.2f".format(discount)}", style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold, color = SuccessGreen))
                            }
                        }

                        // Shipping
                        Spacer(modifier = Modifier.height(4.dp))
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text("Carbon-Neutral Shipping", style = MaterialTheme.typography.bodyMedium)
                            Text(
                                text = if (shippingFee == 0.0) "FREE" else "$${"%.2f".format(shippingFee)}",
                                style = MaterialTheme.typography.bodyMedium.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = if (shippingFee == 0.0) SuccessGreen else TextPrimary
                                )
                            )
                        }

                        Spacer(modifier = Modifier.height(8.dp))
                        Divider(color = BorderLight, thickness = 0.8.dp)
                        Spacer(modifier = Modifier.height(8.dp))

                        // Total
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column {
                                Text("Estimated Total", style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold, color = DarkForestGreen))
                                Text("Including all taxes", style = MaterialTheme.typography.labelSmall.copy(color = TextMuted, fontSize = 9.5.sp))
                            }
                            Text(
                                text = "$${"%.2f".format(total)}",
                                style = MaterialTheme.typography.headlineLarge.copy(
                                    fontWeight = FontWeight.Black,
                                    color = DarkForestGreen
                                )
                            )
                        }

                        Spacer(modifier = Modifier.height(14.dp))

                        // Checkout CTA
                        Button(
                            onClick = onStartCheckout,
                            colors = ButtonDefaults.buttonColors(
                                containerColor = BotanicalGreen,
                                contentColor = PureWhite
                            ),
                            shape = RoundedCornerShape(8.dp),
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(50.dp)
                                .testTag("cart_proceed_checkout_button")
                        ) {
                            Icon(
                                imageVector = Icons.Default.Lock,
                                contentDescription = null,
                                modifier = Modifier.size(16.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "CHECKOUT • $${"%.2f".format(total)}",
                                style = MaterialTheme.typography.labelLarge.copy(
                                    letterSpacing = 0.8.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            )
                        }
                    }
                }
            }
        }
    }
}
