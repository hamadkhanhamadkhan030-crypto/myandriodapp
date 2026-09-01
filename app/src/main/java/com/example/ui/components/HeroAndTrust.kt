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
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.data.model.GoalCategory
import com.example.ui.theme.*

@Composable
fun HeroSection(
    onShopNowClick: () -> Unit,
    onExploreGoalsClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(NaturalBg)
            .padding(horizontal = 16.dp, vertical = 20.dp)
    ) {
        // Pill Eyebrow in Natural Tones style
        Surface(
            color = NaturalPillSage,
            shape = RoundedCornerShape(100.dp),
            modifier = Modifier.padding(bottom = 12.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(horizontal = 12.dp, vertical = 5.dp)
            ) {
                Box(
                    modifier = Modifier
                        .size(6.dp)
                        .clip(CircleShape)
                        .background(NaturalGreen)
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    text = "CLINICALLY BACKED ORGANIC NUTRITION",
                    style = MaterialTheme.typography.labelSmall.copy(
                        color = NaturalGreen,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 0.8.sp
                    )
                )
            }
        }

        // Editorial Headline
        Text(
            text = "ELEVATE YOUR\nCELLULAR VITALITY.",
            style = MaterialTheme.typography.displayLarge.copy(
                fontWeight = FontWeight.Bold,
                color = NaturalTextPrimary,
                lineHeight = 38.sp,
                letterSpacing = (-0.5).sp
            ),
            modifier = Modifier.padding(bottom = 10.dp)
        )

        // Subtitle
        Text(
            text = "Crafted with 100% bio-available raw botanicals, organic adaptogens, and living probiotic cultures. Pure nourishment designed for high-performing lives.",
            style = MaterialTheme.typography.bodyLarge.copy(
                color = NaturalTextBody,
                lineHeight = 22.sp
            ),
            modifier = Modifier.padding(bottom = 18.dp)
        )

        // Action Buttons
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 20.dp),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Button(
                onClick = onShopNowClick,
                colors = ButtonDefaults.buttonColors(
                    containerColor = NaturalGreen,
                    contentColor = NaturalBg
                ),
                shape = RoundedCornerShape(14.dp),
                elevation = ButtonDefaults.buttonElevation(defaultElevation = 0.dp),
                modifier = Modifier
                    .weight(1f)
                    .height(48.dp)
                    .testTag("hero_shop_now_button")
            ) {
                Text(
                    text = "SHOP FORMULAS",
                    style = MaterialTheme.typography.labelLarge.copy(
                        letterSpacing = 0.8.sp,
                        fontWeight = FontWeight.Bold
                    )
                )
            }

            OutlinedButton(
                onClick = onExploreGoalsClick,
                colors = ButtonDefaults.outlinedButtonColors(
                    contentColor = NaturalGreen
                ),
                border = BorderStroke(1.2.dp, NaturalBorder),
                shape = RoundedCornerShape(14.dp),
                modifier = Modifier
                    .weight(1f)
                    .height(48.dp)
                    .testTag("hero_explore_button")
            ) {
                Text(
                    text = "EXPLORE GOALS",
                    style = MaterialTheme.typography.labelLarge.copy(
                        color = NaturalGreen,
                        letterSpacing = 0.8.sp,
                        fontWeight = FontWeight.Bold
                    )
                )
            }
        }

        // Hero Campaign Image Card with Natural Meadow tone and rounded-[32px]
        Card(
            shape = RoundedCornerShape(28.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
            border = BorderStroke(1.dp, NaturalBorder),
            colors = CardDefaults.cardColors(containerColor = NaturalHeroBg),
            modifier = Modifier
                .fillMaxWidth()
                .height(210.dp)
                .clip(RoundedCornerShape(28.dp))
        ) {
            Box(modifier = Modifier.fillMaxSize()) {
                Image(
                    painter = painterResource(id = R.drawable.img_hero_banner),
                    contentDescription = "Verdant Pure Botanical Formula Line",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )
                // Gradient overlay at bottom for badge contrast
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            Brush.verticalGradient(
                                colors = listOf(Color.Transparent, Color(0xAA1B1C17)),
                                startY = 100f
                            )
                        )
                )
                Row(
                    modifier = Modifier
                        .align(Alignment.BottomStart)
                        .padding(14.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Surface(
                        color = PureWhite.copy(alpha = 0.8f),
                        shape = RoundedCornerShape(100.dp),
                        modifier = Modifier.padding(end = 6.dp)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Shield,
                                contentDescription = null,
                                tint = NaturalGreen,
                                modifier = Modifier.size(14.dp)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = "FOCUS TODAY",
                                style = MaterialTheme.typography.labelSmall.copy(
                                    color = NaturalGreen,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 10.sp,
                                    letterSpacing = 0.6.sp
                                )
                            )
                        }
                    }
                    Text(
                        text = "100% ORGANIC • ZERO FILLERS",
                        style = MaterialTheme.typography.labelSmall.copy(
                            color = PureWhite,
                            fontWeight = FontWeight.Bold,
                            letterSpacing = 0.6.sp
                        )
                    )
                }
            }
        }
    }
}

@Composable
fun TrustValueStrip(
    modifier: Modifier = Modifier
) {
    val trustItems = listOf(
        Triple(Icons.Outlined.CheckCircle, "PURE FORMULAS", "Zero artificial fillers"),
        Triple(Icons.Outlined.Science, "EXPERT FORMULATED", "Clinically dosed actives"),
        Triple(Icons.Outlined.LocalShipping, "CARBON-FREE DELIVERY", "Free on orders over $50"),
        Triple(Icons.Outlined.Autorenew, "30-DAY GUARANTEE", "100% satisfaction promise"),
        Triple(Icons.Outlined.Park, "SUSTAINABLE HARVEST", "Regeneratively farmed")
    )

    Surface(
        color = NaturalSurface,
        border = BorderStroke(1.dp, NaturalBorder),
        modifier = modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(rememberScrollState())
                .padding(vertical = 14.dp, horizontal = 12.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            trustItems.forEach { (icon, title, desc) ->
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .background(PureWhite, RoundedCornerShape(16.dp))
                        .border(1.dp, NaturalBorder, RoundedCornerShape(16.dp))
                        .padding(horizontal = 12.dp, vertical = 8.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(36.dp)
                            .clip(RoundedCornerShape(12.dp))
                            .background(NaturalPillSage),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = icon,
                            contentDescription = title,
                            tint = NaturalGreen,
                            modifier = Modifier.size(18.dp)
                        )
                    }
                    Spacer(modifier = Modifier.width(10.dp))
                    Column {
                        Text(
                            text = title,
                            style = MaterialTheme.typography.labelMedium.copy(
                                fontWeight = FontWeight.Bold,
                                color = NaturalTextPrimary,
                                letterSpacing = 0.5.sp
                            )
                        )
                        Text(
                            text = desc,
                            style = MaterialTheme.typography.labelSmall.copy(
                                color = NaturalTextSecondary,
                                fontSize = 10.sp
                            )
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun GoalCategorySection(
    categories: List<GoalCategory>,
    selectedCategory: String?,
    onSelectCategory: (String?) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(NaturalBg)
            .padding(top = 22.dp, bottom = 12.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, end = 16.dp, bottom = 14.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text(
                    text = "TARGETED WELLNESS",
                    style = MaterialTheme.typography.labelSmall.copy(
                        color = NaturalGreen,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 1.0.sp
                    )
                )
                Text(
                    text = "Shop by Health Goal",
                    style = MaterialTheme.typography.headlineMedium.copy(
                        fontWeight = FontWeight.Bold,
                        color = NaturalTextPrimary
                    )
                )
            }

            if (selectedCategory != null) {
                TextButton(onClick = { onSelectCategory(null) }) {
                    Text(
                        text = "Reset All",
                        style = MaterialTheme.typography.labelMedium.copy(
                            color = NaturalGreen,
                            fontWeight = FontWeight.Bold
                        )
                    )
                }
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(rememberScrollState())
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            categories.forEach { cat ->
                val isSelected = selectedCategory == cat.title
                val iconVector = when (cat.iconName) {
                    "Bolt" -> Icons.Default.Bolt
                    "FitnessCenter" -> Icons.Default.FitnessCenter
                    "NightsStay" -> Icons.Default.Bedtime
                    "Spa" -> Icons.Default.Spa
                    "Face" -> Icons.Default.Face
                    else -> Icons.Default.Shield
                }

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .width(96.dp)
                        .clip(RoundedCornerShape(16.dp))
                        .clickable { onSelectCategory(if (isSelected) null else cat.title) }
                        .padding(vertical = 4.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(72.dp)
                            .clip(RoundedCornerShape(24.dp))
                            .background(if (isSelected) NaturalPillSage else NaturalSurface)
                            .border(
                                width = if (isSelected) 2.dp else 1.dp,
                                color = if (isSelected) NaturalGreen else NaturalBorder,
                                shape = RoundedCornerShape(24.dp)
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = iconVector,
                            contentDescription = cat.title,
                            tint = if (isSelected) NaturalGreen else NaturalTextBody,
                            modifier = Modifier.size(30.dp)
                        )
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = cat.title,
                        style = MaterialTheme.typography.labelMedium.copy(
                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                            color = if (isSelected) NaturalGreen else NaturalTextPrimary,
                            textAlign = TextAlign.Center,
                            fontSize = 11.5.sp
                        ),
                        maxLines = 2,
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
    }
}
