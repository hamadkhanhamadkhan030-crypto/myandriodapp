package com.example.ui.screens

import androidx.compose.foundation.BorderStroke
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
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.Address
import com.example.data.model.UserOrder
import com.example.ui.CommerceViewModel
import com.example.ui.theme.*

@Composable
fun AccountProfileScreen(
    viewModel: CommerceViewModel,
    userAddress: Address,
    orderHistory: List<UserOrder>,
    modifier: Modifier = Modifier
) {
    var isEditingAddress by remember { mutableStateOf(false) }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(WarmCream)
            .padding(horizontal = 16.dp, vertical = 14.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        // Profile Hero Header
        item {
            Card(
                shape = RoundedCornerShape(14.dp),
                colors = CardDefaults.cardColors(containerColor = PureWhite),
                border = BorderStroke(1.dp, BorderLight),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(18.dp)) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(14.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(56.dp)
                                .clip(CircleShape)
                                .background(BotanicalGreen),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "AM",
                                style = MaterialTheme.typography.titleLarge.copy(
                                    color = WarmCream,
                                    fontWeight = FontWeight.Bold
                                )
                            )
                        }

                        Column {
                            Text(
                                text = userAddress.fullName,
                                style = MaterialTheme.typography.titleLarge.copy(
                                    fontWeight = FontWeight.Black,
                                    color = DarkForestGreen
                                )
                            )
                            Text(
                                text = "alexandra.m@wellness.io",
                                style = MaterialTheme.typography.bodySmall.copy(
                                    color = TextSecondary
                                )
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Surface(
                                color = LightBotanicalGreen,
                                shape = RoundedCornerShape(4.dp)
                            ) {
                                Text(
                                    text = "PLATINUM VITALITY MEMBER • 1,850 PTS",
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
                }
            }
        }

        // Section: Order History
        item {
            Text(
                text = "ORDER HISTORY & SHIPMENTS",
                style = MaterialTheme.typography.labelSmall.copy(
                    color = SecondaryGreen,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 0.8.sp
                )
            )
        }

        if (orderHistory.isEmpty()) {
            item {
                Card(
                    shape = RoundedCornerShape(10.dp),
                    colors = CardDefaults.cardColors(containerColor = PureWhite),
                    border = BorderStroke(1.dp, BorderLight),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = "No previous orders yet. Your order receipts will appear here.",
                        style = MaterialTheme.typography.bodyMedium.copy(color = TextSecondary),
                        modifier = Modifier.padding(16.dp)
                    )
                }
            }
        } else {
            items(orderHistory, key = { it.orderId }) { order ->
                Card(
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = PureWhite),
                    border = BorderStroke(1.dp, BorderLight),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column {
                                Text(
                                    text = "Order #${order.orderId}",
                                    style = MaterialTheme.typography.titleMedium.copy(
                                        fontWeight = FontWeight.Bold,
                                        color = DarkForestGreen
                                    )
                                )
                                Text(
                                    text = "Placed on ${order.date}",
                                    style = MaterialTheme.typography.labelSmall.copy(
                                        color = TextMuted
                                    )
                                )
                            }

                            Surface(
                                color = LightBotanicalGreen,
                                shape = RoundedCornerShape(4.dp)
                            ) {
                                Text(
                                    text = order.status.uppercase(),
                                    style = MaterialTheme.typography.labelSmall.copy(
                                        color = SuccessGreen,
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 9.5.sp
                                    ),
                                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(10.dp))
                        Divider(color = BorderLight, thickness = 0.8.dp)
                        Spacer(modifier = Modifier.height(10.dp))

                        order.items.forEach { item ->
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 3.dp),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(
                                    text = "${item.quantity}x ${item.product.title}",
                                    style = MaterialTheme.typography.bodySmall.copy(
                                        fontWeight = FontWeight.Medium,
                                        color = TextPrimary
                                    ),
                                    modifier = Modifier.weight(1f)
                                )
                                Text(
                                    text = "$${"%.2f".format(item.totalPrice)}",
                                    style = MaterialTheme.typography.bodySmall.copy(
                                        fontWeight = FontWeight.Bold,
                                        color = DarkForestGreen
                                    )
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(8.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "Total: $${"%.2f".format(order.total)}",
                                style = MaterialTheme.typography.titleMedium.copy(
                                    fontWeight = FontWeight.Black,
                                    color = BotanicalGreen
                                )
                            )

                            OutlinedButton(
                                onClick = {
                                    order.items.forEach { viewModel.addToCart(it.product, it.selectedSize, it.quantity) }
                                },
                                shape = RoundedCornerShape(6.dp),
                                border = BorderStroke(1.dp, BotanicalGreen),
                                contentPadding = PaddingValues(horizontal = 10.dp, vertical = 4.dp),
                                modifier = Modifier.height(32.dp)
                            ) {
                                Text("Reorder All", fontSize = 11.sp, color = BotanicalGreen, fontWeight = FontWeight.Bold)
                            }
                        }
                    }
                }
            }
        }

        // Section: Saved Delivery Address
        item {
            Text(
                text = "DEFAULT DELIVERY ADDRESS",
                style = MaterialTheme.typography.labelSmall.copy(
                    color = SecondaryGreen,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 0.8.sp
                )
            )
        }

        item {
            Card(
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = PureWhite),
                border = BorderStroke(1.dp, BorderLight),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Outlined.Home,
                                contentDescription = null,
                                tint = BotanicalGreen,
                                modifier = Modifier.size(20.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "Primary Residence",
                                style = MaterialTheme.typography.titleMedium.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = DarkForestGreen
                                )
                            )
                        }

                        Surface(
                            color = SoftBeige,
                            shape = RoundedCornerShape(4.dp)
                        ) {
                            Text(
                                text = "DEFAULT",
                                style = MaterialTheme.typography.labelSmall.copy(
                                    color = DarkForestGreen,
                                    fontSize = 8.5.sp,
                                    fontWeight = FontWeight.Bold
                                ),
                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(text = userAddress.fullName, style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.SemiBold))
                    Text(text = userAddress.street, style = MaterialTheme.typography.bodyMedium)
                    Text(text = "${userAddress.city}, ${userAddress.state} ${userAddress.zipCode}", style = MaterialTheme.typography.bodyMedium)
                    Text(text = userAddress.phone, style = MaterialTheme.typography.bodySmall.copy(color = TextMuted))
                }
            }
        }

        // Section: Personal Wellness Goals
        item {
            Text(
                text = "PERSONALIZED WELLNESS FOCUS",
                style = MaterialTheme.typography.labelSmall.copy(
                    color = SecondaryGreen,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 0.8.sp
                )
            )
        }

        item {
            Card(
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = PureWhite),
                border = BorderStroke(1.dp, BorderLight),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    val goals = listOf(
                        Pair(Icons.Default.Bolt, "Cellular Energy & Anti-Fatigue"),
                        Pair(Icons.Default.Bedtime, "Deep REM Sleep & Rest"),
                        Pair(Icons.Default.Spa, "Gut Microbiome Balance")
                    )

                    goals.forEach { (icon, goal) ->
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.padding(vertical = 6.dp)
                        ) {
                            Icon(imageVector = icon, contentDescription = null, tint = BotanicalGreen, modifier = Modifier.size(18.dp))
                            Spacer(modifier = Modifier.width(10.dp))
                            Text(text = goal, style = MaterialTheme.typography.bodyMedium.copy(color = TextPrimary))
                        }
                    }
                }
            }
        }

        // Section: Support & Guarantee
        item {
            Card(
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = LightBotanicalGreen),
                border = BorderStroke(1.dp, SageGreen.copy(alpha = 0.4f)),
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier.padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.HeadsetMic,
                        contentDescription = null,
                        tint = DarkForestGreen,
                        modifier = Modifier.size(28.dp)
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    Column {
                        Text(
                            text = "24/7 Botanical Concierge Care",
                            style = MaterialTheme.typography.titleMedium.copy(
                                fontWeight = FontWeight.Bold,
                                color = DarkForestGreen
                            )
                        )
                        Text(
                            text = "Have questions regarding dosing, ingredients, or subscriptions? Our clinical herbalists are here for you.",
                            style = MaterialTheme.typography.bodySmall.copy(
                                color = DarkForestGreen,
                                lineHeight = 17.sp
                            )
                        )
                    }
                }
            }
        }

        item {
            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}
