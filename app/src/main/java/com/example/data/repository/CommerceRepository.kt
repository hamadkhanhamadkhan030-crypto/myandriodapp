package com.example.data.repository

import com.example.R
import com.example.data.model.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class CommerceRepository {

    val sampleAnnouncements = listOf(
        Announcement("1", "FREE CARBON-NEUTRAL DELIVERY OVER $50", "FREE DELIVERY"),
        Announcement("2", "NEW CUSTOMERS ENJOY 15% OFF WITH CODE VITAL15", "SAVE 15%"),
        Announcement("3", "30-DAY SATISFACTION MONEY-BACK GUARANTEE", "RISK-FREE"),
        Announcement("4", "100% CERTIFIED ORGANIC & THIRD-PARTY LAB VERIFIED", "PURE QUALITY")
    )

    val goalCategories = listOf(
        GoalCategory("1", "Daily Vitality", "Energy & Focus", "Cellular nutrition & adaptogens for sustainable all-day energy", "Bolt"),
        GoalCategory("2", "Strength & Muscle", "Performance", "Clean plant protein and essential BCAAs for lean recovery", "FitnessCenter"),
        GoalCategory("3", "Deep Sleep & Calm", "Rest & Relaxation", "Chelated magnesium, L-theanine & botanical herbs", "NightsStay"),
        GoalCategory("4", "Gut & Microbiome", "Digestion", "50 Billion CFU probiotics with fermented prebiotic fiber", "Spa"),
        GoalCategory("5", "Radiant Skin & Hair", "Youth & Glow", "Plant-based collagen peptides & bio-available ceramides", "Face"),
        GoalCategory("6", "Immune Resilience", "Defense", "High-potency elderberry, zinc glycinate & vitamin D3/K2", "Shield")
    )

    val products = listOf(
        Product(
            id = "prod-1",
            title = "Organic Daily Supergreens + Probiotics",
            subtitle = "35 Organic Raw Superfoods & 5B CFU Probiotics",
            category = "Daily Vitality",
            goal = "Daily Vitality",
            price = 48.00,
            compareAtPrice = 58.00,
            rating = 4.92,
            reviewCount = 1482,
            badge = "BEST SELLER",
            imageRes = R.drawable.img_product_supergreens,
            description = "Our flagship wholefood green elixir formulated with 35 nutrient-dense raw greens, adaptogenic roots, and clinically studied probiotics to energize cellular function, enhance gut flora, and support vibrant clarity.",
            benefits = listOf(
                "Promotes continuous natural energy without stimulants or crashes",
                "Alkalizing wholefood blend balances natural internal pH",
                "Includes organic spirulina, wheatgrass, chlorella & matcha",
                "Supports comfortable digestion with multi-strain digestive enzymes"
            ),
            ingredients = "Organic Spirulina, Organic Barley Grass, Organic Wheatgrass, Organic Chlorella, Organic Spinach, Organic Kale, Organic Ashwagandha Root, 5 Billion CFU Lactobacillus Gasseri, Digestive Enzyme Complex (Amylase, Protease, Lipase).",
            howToUse = "Mix 1 rounded scoop (10g) with 10-12 oz of chilled water, coconut water, or blend into your morning smoothie. Enjoy daily in the morning.",
            sizes = listOf("30 Servings (300g)", "60 Servings (600g)", "90 Servings (900g)")
        ),
        Product(
            id = "prod-2",
            title = "Botanical Collagen & Ceramide Youth Elixir",
            subtitle = "Liposomal Ceramide Phyto-Peptides & Tremella Mushroom",
            category = "Skin & Hair",
            goal = "Radiant Skin & Hair",
            price = 54.00,
            compareAtPrice = 65.00,
            rating = 4.95,
            reviewCount = 894,
            badge = "TOP RATED",
            imageRes = R.drawable.img_product_collagen,
            description = "A luxurious 100% plant-based bioactive collagen boosting tincture and powder. Infused with organic Tremella mushroom, liposomal bamboo silica, and French sea pine bark to rebuild skin elasticity and deep moisture barrier.",
            benefits = listOf(
                "Stimulates natural pro-collagen synthesis at the cellular level",
                "Hydrates skin from within with natural hyaluronic ceramides",
                "Fortifies hair follicles and strengthens brittle nails",
                "Delivers powerful polyphenol antioxidants to combat oxidative stress"
            ),
            ingredients = "Organic Tremella Fuciformis (Snow Mushroom Extract), Bamboo Silica (70% extract), French Maritime Pine Bark Extract, Organic Sea Buckthorn Berry, Liposomal Ceramide Complex, Organic Hyaluronic Acid.",
            howToUse = "Take 1 full dropper directly under the tongue or stir into 8 oz of warm herbal tea or water. Use consistently morning and night.",
            sizes = listOf("30-Day Supply (50ml)", "60-Day Supply (100ml)")
        ),
        Product(
            id = "prod-3",
            title = "Cellular Longevity & NAD+ Active Matrix",
            subtitle = "Micro-Enriched NMN, Trans-Resveratrol & Quercetin",
            category = "Cellular Longevity",
            goal = "Daily Vitality",
            price = 62.00,
            compareAtPrice = 75.00,
            rating = 4.88,
            reviewCount = 642,
            badge = "NEW FORMULA",
            imageRes = R.drawable.img_hero_banner,
            description = "Advanced cellular rejuvenation formula formulated to elevate NAD+ coenzyme levels, support mitochondrial ATP energy generation, and activate longevity sirtuins for peak physical and cognitive vitality.",
            benefits = listOf(
                "Boosts cellular NAD+ synthesis for robust mitochondrial health",
                "Contains 99% pure micronized trans-resveratrol and bio-quercetin",
                "Enhances physical stamina and rapid exercise recovery",
                "Promotes DNA repair pathways and healthy aging markers"
            ),
            ingredients = "Nicotinamide Mononucleotide (NMN 300mg), Micronized Trans-Resveratrol (250mg), Quercetin Phytosome (150mg), Organic Broccoli Sprout Extract (Sulforaphane 50mg), Piperine Extract (Bioperine).",
            howToUse = "Take 2 vegetarian capsules daily with your first meal of the day containing healthy fats.",
            sizes = listOf("60 Capsules (30 Days)", "120 Capsules (60 Days)")
        ),
        Product(
            id = "prod-4",
            title = "Deep Rest & Botanical Magnesium Calm",
            subtitle = "Chelated Magnesium Glycinate, L-Theanine & Tart Cherry",
            category = "Deep Rest & Sleep",
            goal = "Deep Sleep & Calm",
            price = 42.00,
            compareAtPrice = 50.00,
            rating = 4.93,
            reviewCount = 1128,
            badge = "BEST SELLER",
            imageRes = R.drawable.img_promo_lifestyle,
            description = "Gentle nightly restorative elixir designed to calm an overactive nervous system, relax tight muscles, and promote deep REM sleep without grogginess upon waking.",
            benefits = listOf(
                "Soothes tension and calms evening stress hormones",
                "Supports restorative deep-wave sleep stages and sleep onset",
                "Highly bioavailable chelated magnesium causes zero digestive upset",
                "Natural melatonin support from organic Montmorency tart cherries"
            ),
            ingredients = "Magnesium Bisglycinate Chelate (200mg elemental Mg), Suntheanine® L-Theanine (200mg), Organic Tart Cherry Extract (500mg), Organic Chamomile Flower, Passionflower Extract, Lemon Balm.",
            howToUse = "Mix 1 scoop in 6-8 oz of warm water 30-45 minutes before sleep.",
            sizes = listOf("30 Servings", "60 Servings")
        ),
        Product(
            id = "prod-5",
            title = "Organic Clean Plant Protein + Adaptogens",
            subtitle = "25g Multi-Source Sprouted Protein & Cordyceps",
            category = "Strength & Muscle",
            goal = "Strength & Muscle",
            price = 46.00,
            compareAtPrice = 55.00,
            rating = 4.87,
            reviewCount = 780,
            badge = "CLEAN NUTRITION",
            imageRes = R.drawable.img_product_supergreens,
            description = "Silky, delicious plant protein crafted with organic sprouted pea, organic pumpkin seed, and organic chia seed combined with organic Cordyceps mushroom for muscle repair and endurance.",
            benefits = listOf(
                "25g complete amino acid profile protein per serving",
                "Ultra-smooth texture with zero chalkiness or artificial stevia aftertaste",
                "Enhanced with organic Cordyceps to aid oxygen utilization",
                "Free from dairy, soy, gums, fillers, and artificial sweeteners"
            ),
            ingredients = "Organic Sprouted Fermented Pea Protein, Organic Pumpkin Seed Protein, Organic Chia Seed Powder, Organic Sacha Inchi, Organic Cordyceps Militaris, Organic Madagascar Vanilla Bean, Sea Salt.",
            howToUse = "Shake or blend 1 scoop with 12 oz of almond milk, oat milk, or cold water after workouts.",
            sizes = listOf("20 Servings (700g)", "40 Servings (1.4kg)")
        ),
        Product(
            id = "prod-6",
            title = "Microbiome Balance & Prebiotic Inulin",
            subtitle = "50 Billion CFU 12-Strain Delayed-Release Probiotic",
            category = "Gut & Microbiome",
            goal = "Gut & Microbiome",
            price = 44.00,
            compareAtPrice = 52.00,
            rating = 4.90,
            reviewCount = 530,
            badge = "DOCTOR FORMULATED",
            imageRes = R.drawable.img_product_collagen,
            description = "Targeted digestive support delivering 50 billion diverse probiotic organisms past harsh stomach acids directly into the lower intestine where they colonize to restore microbiome balance.",
            benefits = listOf(
                "Relieves occasional bloating, gas, and digestive heaviness",
                "Supports nutrient absorption and immune barrier integrity",
                "Includes organic Jerusalem artichoke inulin as food for good bacteria",
                "Shelf-stable desiccant lined bottle requires zero refrigeration"
            ),
            ingredients = "12 Probiotic Strains (L. acidophilus, B. lactis, L. plantarum, B. longum, L. rhamnosus, etc. - 50 Billion CFU), Organic Jerusalem Artichoke Inulin (Prebiotic 350mg), DRcaps® Acid-Resistant Capsule.",
            howToUse = "Take 1 capsule daily on an empty stomach with a glass of water.",
            sizes = listOf("30 Capsules (1 Month)", "60 Capsules (2 Months)")
        )
    )

    val testimonials = listOf(
        Testimonial(
            id = "t-1",
            author = "Dr. Elena Vance, ND",
            role = "Naturopathic Physician",
            rating = 5,
            quote = "Verdant Pure sets the gold standard for bioavailability and transparency. The Organic Supergreens has become an essential part of my clinical practice recommendations.",
            productPurchased = "Organic Daily Supergreens"
        ),
        Testimonial(
            id = "t-2",
            author = "Marcus Thorne",
            role = "Ultra-Endurance Athlete",
            rating = 5,
            quote = "The difference in recovery time and sustained morning clarity is night and day. The Clean Plant Protein digests effortlessly without any bloating.",
            productPurchased = "Plant Protein & Superfoods"
        ),
        Testimonial(
            id = "t-3",
            author = "Sophia Lin",
            role = "Verified Customer",
            rating = 5,
            quote = "My skin hydration and glow noticeably improved in just 3 weeks with the Collagen & Ceramide Elixir. The natural botanical flavor is so soothing.",
            productPurchased = "Collagen & Ceramide Elixir"
        ),
        Testimonial(
            id = "t-4",
            author = "David K.",
            role = "Verified Customer",
            rating = 5,
            quote = "I have struggled with sleep quality for years. The Botanical Magnesium Calm lets me fall asleep smoothly and wake up refreshed without grogginess.",
            productPurchased = "Botanical Magnesium Calm"
        )
    )

    // State Management
    private val _cartItems = MutableStateFlow<List<CartItem>>(
        listOf(
            CartItem(products[0], quantity = 1, selectedSize = "30 Servings (300g)"),
            CartItem(products[1], quantity = 1, selectedSize = "30-Day Supply (50ml)")
        )
    )
    val cartItems: StateFlow<List<CartItem>> = _cartItems.asStateFlow()

    private val _wishlistProductIds = MutableStateFlow<Set<String>>(setOf("prod-1", "prod-2"))
    val wishlistProductIds: StateFlow<Set<String>> = _wishlistProductIds.asStateFlow()

    private val _appliedPromoCode = MutableStateFlow<String?>("VITAL15")
    val appliedPromoCode: StateFlow<String?> = _appliedPromoCode.asStateFlow()

    private val _recentSearches = MutableStateFlow<List<String>>(
        listOf("Supergreens", "Collagen", "Magnesium Sleep", "Plant Protein", "Gut Health")
    )
    val recentSearches: StateFlow<List<String>> = _recentSearches.asStateFlow()

    private val _userAddress = MutableStateFlow(
        Address(
            fullName = "Alexandra Montgomery",
            street = "742 Evergreen Botanical Way",
            city = "San Francisco",
            state = "CA",
            zipCode = "94107",
            phone = "(415) 890-2341",
            isDefault = true
        )
    )
    val userAddress: StateFlow<Address> = _userAddress.asStateFlow()

    private val _orderHistory = MutableStateFlow<List<UserOrder>>(
        listOf(
            UserOrder(
                orderId = "VP-98241",
                date = "Aug 24, 2026",
                status = "Delivered",
                items = listOf(
                    CartItem(products[0], 1, "30 Servings (300g)"),
                    CartItem(products[3], 1, "30 Servings")
                ),
                subtotal = 90.00,
                shipping = 0.0,
                discount = 13.50,
                total = 76.50,
                deliveryAddress = _userAddress.value
            )
        )
    )
    val orderHistory: StateFlow<List<UserOrder>> = _orderHistory.asStateFlow()

    // Cart Operations
    fun addToCart(product: Product, size: String = product.sizes.first(), quantity: Int = 1) {
        _cartItems.update { current ->
            val index = current.indexOfFirst { it.product.id == product.id && it.selectedSize == size }
            if (index >= 0) {
                current.toMutableList().apply {
                    this[index] = this[index].copy(quantity = this[index].quantity + quantity)
                }
            } else {
                current + CartItem(product, quantity, size)
            }
        }
    }

    fun updateQuantity(product: Product, size: String, newQuantity: Int) {
        if (newQuantity <= 0) {
            removeFromCart(product.id, size)
        } else {
            _cartItems.update { current ->
                current.map {
                    if (it.product.id == product.id && it.selectedSize == size) {
                        it.copy(quantity = newQuantity)
                    } else it
                }
            }
        }
    }

    fun removeFromCart(productId: String, size: String) {
        _cartItems.update { current ->
            current.filterNot { it.product.id == productId && it.selectedSize == size }
        }
    }

    fun clearCart() {
        _cartItems.value = emptyList()
    }

    // Wishlist Operations
    fun toggleWishlist(productId: String) {
        _wishlistProductIds.update { current ->
            if (current.contains(productId)) {
                current - productId
            } else {
                current + productId
            }
        }
    }

    fun isWishlisted(productId: String): Boolean {
        return _wishlistProductIds.value.contains(productId)
    }

    // Promo Code
    fun applyPromoCode(code: String): Boolean {
        val trimmed = code.trim().uppercase()
        return if (trimmed == "VITAL15" || trimmed == "VERDANT10" || trimmed == "WELCOME20") {
            _appliedPromoCode.value = trimmed
            true
        } else {
            false
        }
    }

    fun removePromoCode() {
        _appliedPromoCode.value = null
    }

    // Search Operations
    fun addRecentSearch(query: String) {
        if (query.isBlank()) return
        _recentSearches.update { current ->
            (listOf(query.trim()) + current.filterNot { it.equals(query.trim(), ignoreCase = true) }).take(8)
        }
    }

    fun removeRecentSearch(query: String) {
        _recentSearches.update { current ->
            current.filterNot { it.equals(query, ignoreCase = true) }
        }
    }

    fun updateAddress(newAddress: Address) {
        _userAddress.value = newAddress
    }

    fun placeOrder(order: UserOrder) {
        _orderHistory.update { listOf(order) + it }
        clearCart()
    }
}
