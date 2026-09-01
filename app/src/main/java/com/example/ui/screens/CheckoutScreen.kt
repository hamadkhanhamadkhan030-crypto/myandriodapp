package com.example.ui.screens

import androidx.compose.animation.*
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.Address
import com.example.data.model.CartItem
import com.example.ui.CommerceViewModel
import com.example.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CheckoutScreen(
    viewModel: CommerceViewModel,
    cartItems: List<CartItem>,
    subtotal: Double,
    discount: Double,
    shippingFee: Double,
    total: Double,
    userAddress: Address,
    onBackClick: () -> Unit,
    onOrderSuccess: () -> Unit,
    modifier: Modifier = Modifier
) {
    var step by remember { mutableIntStateOf(1) }

    // Form inputs
    var email by remember { mutableStateOf("alexandra.m@wellness.io") }
    var fullName by remember { mutableStateOf(userAddress.fullName) }
    var street by remember { mutableStateOf(userAddress.street) }
    var city by remember { mutableStateOf(userAddress.city) }
    var state by remember { mutableStateOf(userAddress.state) }
    var zipCode by remember { mutableStateOf(userAddress.zipCode) }
    var phone by remember { mutableStateOf(userAddress.phone) }

    var selectedShippingMethod by remember { mutableStateOf("Standard Carbon-Neutral") }
    var selectedPaymentMethod by remember { mutableStateOf("Credit Card") }
    var cardNumber by remember { mutableStateOf("•••• •••• •••• 4242") }
    var cardExpiry by remember { mutableStateOf("12/28") }
    var cardCvc by remember { mutableStateOf("884") }

    var isOrderCompleted by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = if (isOrderCompleted) "ORDER CONFIRMED" else "SECURE CHECKOUT",
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.Bold,
                            letterSpacing = 0.8.sp,
                            color = DarkForestGreen
                        )
                    )
                },
                navigationIcon = {
                    if (!isOrderCompleted) {
                        IconButton(
                            onClick = {
                                if (step > 1) step-- else onBackClick()
                            },
                            modifier = Modifier.testTag("checkout_back_button")
                        ) {
                            Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Back")
                        }
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = PureWhite)
            )
        },
        modifier = modifier.fillMaxSize()
    ) { innerPadding ->
        if (isOrderCompleted) {
            // Order Placed Celebration State
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(WarmCream)
                    .padding(innerPadding)
                    .padding(24.dp),
                contentAlignment = Alignment.Center
            ) {
                Card(
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = PureWhite),
                    border = BorderStroke(1.dp, BorderLight),
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(24.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Box(
                            modifier = Modifier
                                .size(64.dp)
                                .clip(CircleShape)
                                .background(LightBotanicalGreen),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.CheckCircle,
                                contentDescription = null,
                                tint = SuccessGreen,
                                modifier = Modifier.size(36.dp)
                            )
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        Text(
                            text = "Thank You For Your Order!",
                            style = MaterialTheme.typography.headlineLarge.copy(
                                fontWeight = FontWeight.Black,
                                color = DarkForestGreen,
                                textAlign = TextAlign.Center
                            )
                        )

                        Spacer(modifier = Modifier.height(6.dp))

                        Text(
                            text = "A confirmation receipt and carbon-neutral tracking link have been dispatched to $email.",
                            style = MaterialTheme.typography.bodyMedium.copy(
                                color = TextSecondary,
                                textAlign = TextAlign.Center,
                                lineHeight = 20.sp
                            )
                        )

                        Spacer(modifier = Modifier.height(16.dp))
                        Divider(color = BorderLight, thickness = 0.8.dp)
                        Spacer(modifier = Modifier.height(16.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text("Estimated Delivery", style = MaterialTheme.typography.bodySmall)
                            Text("2-3 Business Days", style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Bold, color = BotanicalGreen))
                        }

                        Spacer(modifier = Modifier.height(6.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text("Total Paid", style = MaterialTheme.typography.bodySmall)
                            Text("$${"%.2f".format(total)}", style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Black, color = DarkForestGreen))
                        }

                        Spacer(modifier = Modifier.height(24.dp))

                        Button(
                            onClick = onOrderSuccess,
                            colors = ButtonDefaults.buttonColors(containerColor = BotanicalGreen),
                            shape = RoundedCornerShape(8.dp),
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(48.dp)
                                .testTag("checkout_continue_shopping")
                        ) {
                            Text(
                                text = "CONTINUE EXPLORING",
                                style = MaterialTheme.typography.labelLarge.copy(
                                    fontWeight = FontWeight.Bold,
                                    letterSpacing = 0.6.sp
                                )
                            )
                        }
                    }
                }
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .background(WarmCream)
                    .padding(innerPadding)
                    .padding(horizontal = 16.dp, vertical = 12.dp)
            ) {
                // Step Indicator Pills
                item {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 16.dp),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        listOf("1. Contact", "2. Shipping", "3. Payment").forEachIndexed { idx, label ->
                            val currentStepNum = idx + 1
                            val isCurrent = step == currentStepNum
                            val isDone = step > currentStepNum
                            Surface(
                                color = if (isCurrent) BotanicalGreen else if (isDone) LightBotanicalGreen else SoftBeige,
                                shape = RoundedCornerShape(6.dp),
                                modifier = Modifier.weight(1f)
                            ) {
                                Text(
                                    text = label,
                                    style = MaterialTheme.typography.labelSmall.copy(
                                        color = if (isCurrent) PureWhite else if (isDone) DarkForestGreen else TextMuted,
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 11.sp,
                                        textAlign = TextAlign.Center
                                    ),
                                    modifier = Modifier.padding(vertical = 6.dp)
                                )
                            }
                        }
                    }
                }

                // Step 1: Contact & Delivery Address
                if (step == 1) {
                    item {
                        Card(
                            shape = RoundedCornerShape(12.dp),
                            colors = CardDefaults.cardColors(containerColor = PureWhite),
                            border = BorderStroke(1.dp, BorderLight),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Column(modifier = Modifier.padding(16.dp)) {
                                Text(
                                    text = "1. CONTACT INFORMATION",
                                    style = MaterialTheme.typography.labelSmall.copy(
                                        color = SecondaryGreen,
                                        fontWeight = FontWeight.Bold,
                                        letterSpacing = 0.8.sp
                                    )
                                )
                                Spacer(modifier = Modifier.height(10.dp))

                                OutlinedTextField(
                                    value = email,
                                    onValueChange = { email = it },
                                    label = { Text("Email Address") },
                                    singleLine = true,
                                    modifier = Modifier.fillMaxWidth(),
                                    shape = RoundedCornerShape(8.dp)
                                )

                                Spacer(modifier = Modifier.height(16.dp))
                                Text(
                                    text = "2. DELIVERY ADDRESS",
                                    style = MaterialTheme.typography.labelSmall.copy(
                                        color = SecondaryGreen,
                                        fontWeight = FontWeight.Bold,
                                        letterSpacing = 0.8.sp
                                    )
                                )
                                Spacer(modifier = Modifier.height(10.dp))

                                OutlinedTextField(
                                    value = fullName,
                                    onValueChange = { fullName = it },
                                    label = { Text("Full Recipient Name") },
                                    singleLine = true,
                                    modifier = Modifier.fillMaxWidth(),
                                    shape = RoundedCornerShape(8.dp)
                                )

                                Spacer(modifier = Modifier.height(10.dp))

                                OutlinedTextField(
                                    value = street,
                                    onValueChange = { street = it },
                                    label = { Text("Street Address") },
                                    singleLine = true,
                                    modifier = Modifier.fillMaxWidth(),
                                    shape = RoundedCornerShape(8.dp)
                                )

                                Spacer(modifier = Modifier.height(10.dp))

                                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                                    OutlinedTextField(
                                        value = city,
                                        onValueChange = { city = it },
                                        label = { Text("City") },
                                        singleLine = true,
                                        modifier = Modifier.weight(1.2f),
                                        shape = RoundedCornerShape(8.dp)
                                    )
                                    OutlinedTextField(
                                        value = state,
                                        onValueChange = { state = it },
                                        label = { Text("State") },
                                        singleLine = true,
                                        modifier = Modifier.weight(0.8f),
                                        shape = RoundedCornerShape(8.dp)
                                    )
                                    OutlinedTextField(
                                        value = zipCode,
                                        onValueChange = { zipCode = it },
                                        label = { Text("ZIP") },
                                        singleLine = true,
                                        modifier = Modifier.weight(1f),
                                        shape = RoundedCornerShape(8.dp)
                                    )
                                }

                                Spacer(modifier = Modifier.height(10.dp))

                                OutlinedTextField(
                                    value = phone,
                                    onValueChange = { phone = it },
                                    label = { Text("Mobile Phone (For Delivery SMS)") },
                                    singleLine = true,
                                    modifier = Modifier.fillMaxWidth(),
                                    shape = RoundedCornerShape(8.dp)
                                )

                                Spacer(modifier = Modifier.height(18.dp))

                                Button(
                                    onClick = {
                                        viewModel.updateAddress(
                                            Address(fullName, street, city, state, zipCode, phone = phone)
                                        )
                                        step = 2
                                    },
                                    colors = ButtonDefaults.buttonColors(containerColor = BotanicalGreen),
                                    shape = RoundedCornerShape(8.dp),
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(48.dp)
                                        .testTag("checkout_continue_to_shipping")
                                ) {
                                    Text("CONTINUE TO SHIPPING METHOD", fontWeight = FontWeight.Bold)
                                }
                            }
                        }
                    }
                }

                // Step 2: Shipping Method
                if (step == 2) {
                    item {
                        Card(
                            shape = RoundedCornerShape(12.dp),
                            colors = CardDefaults.cardColors(containerColor = PureWhite),
                            border = BorderStroke(1.dp, BorderLight),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Column(modifier = Modifier.padding(16.dp)) {
                                Text(
                                    text = "SELECT SHIPPING SERVICE",
                                    style = MaterialTheme.typography.labelSmall.copy(
                                        color = SecondaryGreen,
                                        fontWeight = FontWeight.Bold,
                                        letterSpacing = 0.8.sp
                                    )
                                )
                                Spacer(modifier = Modifier.height(12.dp))

                                val shippingOptions = listOf(
                                    Pair("Standard Carbon-Neutral", if (shippingFee == 0.0) "FREE (2-4 Business Days)" else "$5.95 (2-4 Business Days)"),
                                    Pair("Express Priority Courier", "$12.00 (1-2 Business Days)")
                                )

                                shippingOptions.forEach { (name, costDesc) ->
                                    val isSelected = selectedShippingMethod == name
                                    Surface(
                                        color = if (isSelected) LightBotanicalGreen else SoftBeige,
                                        shape = RoundedCornerShape(8.dp),
                                        border = BorderStroke(
                                            width = if (isSelected) 1.5.dp else 1.dp,
                                            color = if (isSelected) BotanicalGreen else BorderLight
                                        ),
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(vertical = 4.dp)
                                            .clickable { selectedShippingMethod = name }
                                    ) {
                                        Row(
                                            modifier = Modifier.padding(14.dp),
                                            verticalAlignment = Alignment.CenterVertically,
                                            horizontalArrangement = Arrangement.SpaceBetween
                                        ) {
                                            Row(verticalAlignment = Alignment.CenterVertically) {
                                                RadioButton(
                                                    selected = isSelected,
                                                    onClick = { selectedShippingMethod = name },
                                                    colors = RadioButtonDefaults.colors(selectedColor = BotanicalGreen)
                                                )
                                                Spacer(modifier = Modifier.width(6.dp))
                                                Column {
                                                    Text(
                                                        text = name,
                                                        style = MaterialTheme.typography.titleMedium.copy(
                                                            fontWeight = FontWeight.Bold,
                                                            fontSize = 14.sp,
                                                            color = DarkForestGreen
                                                        )
                                                    )
                                                    Text(
                                                        text = costDesc,
                                                        style = MaterialTheme.typography.bodySmall.copy(
                                                            color = TextSecondary
                                                        )
                                                    )
                                                }
                                            }
                                        }
                                    }
                                }

                                Spacer(modifier = Modifier.height(20.dp))

                                Button(
                                    onClick = { step = 3 },
                                    colors = ButtonDefaults.buttonColors(containerColor = BotanicalGreen),
                                    shape = RoundedCornerShape(8.dp),
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(48.dp)
                                        .testTag("checkout_continue_to_payment")
                                ) {
                                    Text("CONTINUE TO PAYMENT", fontWeight = FontWeight.Bold)
                                }
                            }
                        }
                    }
                }

                // Step 3: Payment & Final Review
                if (step == 3) {
                    item {
                        Card(
                            shape = RoundedCornerShape(12.dp),
                            colors = CardDefaults.cardColors(containerColor = PureWhite),
                            border = BorderStroke(1.dp, BorderLight),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Column(modifier = Modifier.padding(16.dp)) {
                                Text(
                                    text = "SELECT PAYMENT METHOD",
                                    style = MaterialTheme.typography.labelSmall.copy(
                                        color = SecondaryGreen,
                                        fontWeight = FontWeight.Bold,
                                        letterSpacing = 0.8.sp
                                    )
                                )
                                Spacer(modifier = Modifier.height(12.dp))

                                listOf("Credit / Debit Card", "Google Pay / Instant Pay", "Klarna (4 Interest-Free Payments)").forEach { method ->
                                    val isSelected = selectedPaymentMethod == method
                                    Surface(
                                        color = if (isSelected) LightBotanicalGreen else SoftBeige,
                                        shape = RoundedCornerShape(8.dp),
                                        border = BorderStroke(
                                            width = if (isSelected) 1.5.dp else 1.dp,
                                            color = if (isSelected) BotanicalGreen else BorderLight
                                        ),
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(vertical = 4.dp)
                                            .clickable { selectedPaymentMethod = method }
                                    ) {
                                        Row(
                                            modifier = Modifier.padding(12.dp),
                                            verticalAlignment = Alignment.CenterVertically
                                        ) {
                                            RadioButton(
                                                selected = isSelected,
                                                onClick = { selectedPaymentMethod = method },
                                                colors = RadioButtonDefaults.colors(selectedColor = BotanicalGreen)
                                            )
                                            Spacer(modifier = Modifier.width(6.dp))
                                            Text(
                                                text = method,
                                                style = MaterialTheme.typography.titleMedium.copy(
                                                    fontWeight = FontWeight.Bold,
                                                    fontSize = 14.sp,
                                                    color = DarkForestGreen
                                                )
                                            )
                                        }
                                    }
                                }

                                if (selectedPaymentMethod == "Credit / Debit Card") {
                                    Spacer(modifier = Modifier.height(12.dp))
                                    OutlinedTextField(
                                        value = cardNumber,
                                        onValueChange = { cardNumber = it },
                                        label = { Text("Card Number") },
                                        singleLine = true,
                                        modifier = Modifier.fillMaxWidth(),
                                        shape = RoundedCornerShape(8.dp)
                                    )
                                    Spacer(modifier = Modifier.height(8.dp))
                                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                                        OutlinedTextField(
                                            value = cardExpiry,
                                            onValueChange = { cardExpiry = it },
                                            label = { Text("MM/YY") },
                                            singleLine = true,
                                            modifier = Modifier.weight(1f),
                                            shape = RoundedCornerShape(8.dp)
                                        )
                                        OutlinedTextField(
                                            value = cardCvc,
                                            onValueChange = { cardCvc = it },
                                            label = { Text("CVC") },
                                            singleLine = true,
                                            modifier = Modifier.weight(1f),
                                            shape = RoundedCornerShape(8.dp)
                                        )
                                    }
                                }

                                Spacer(modifier = Modifier.height(18.dp))
                                Divider(color = BorderLight, thickness = 0.8.dp)
                                Spacer(modifier = Modifier.height(12.dp))

                                // Order Summary
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Text("Final Amount Due", style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold, color = DarkForestGreen))
                                    Text("$${"%.2f".format(total)}", style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Black, color = DarkForestGreen))
                                }

                                Spacer(modifier = Modifier.height(16.dp))

                                Button(
                                    onClick = {
                                        viewModel.completeOrder(selectedPaymentMethod)
                                        isOrderCompleted = true
                                    },
                                    colors = ButtonDefaults.buttonColors(containerColor = BotanicalGreen),
                                    shape = RoundedCornerShape(8.dp),
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(50.dp)
                                        .testTag("checkout_place_order_button")
                                ) {
                                    Icon(imageVector = Icons.Default.Lock, contentDescription = null, modifier = Modifier.size(16.dp))
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Text("PLACE ORDER • $${"%.2f".format(total)}", fontWeight = FontWeight.Bold)
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
