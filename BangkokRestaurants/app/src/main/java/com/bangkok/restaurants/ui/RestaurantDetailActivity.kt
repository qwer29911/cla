package com.bangkok.restaurants.ui

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bangkok.restaurants.R
import com.bangkok.restaurants.data.Restaurant
import com.bangkok.restaurants.databinding.ActivityRestaurantDetailBinding

class RestaurantDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRestaurantDetailBinding

    companion object {
        const val EXTRA_RESTAURANT = "extra_restaurant"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRestaurantDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val restaurant = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getSerializableExtra(EXTRA_RESTAURANT, Restaurant::class.java)
        } else {
            @Suppress("DEPRECATION")
            intent.getSerializableExtra(EXTRA_RESTAURANT) as? Restaurant
        }

        restaurant?.let { displayRestaurant(it) }
    }

    private fun displayRestaurant(restaurant: Restaurant) {
        binding.apply {
            detailName.text = restaurant.nazwa
            detailRating.text = restaurant.getOcenaDisplay()
            detailReviews.text = getString(R.string.reviews_count, restaurant.opinii.ifBlank { "0" })
            detailPrice.text = restaurant.getCenyDisplay()
            detailCuisine.text = restaurant.typKuchni.ifBlank { "-" }
            detailDistrict.text = restaurant.dzielnica.ifBlank { "-" }
            detailDescription.text = restaurant.opis.ifBlank { "Brak opisu" }
            detailAddress.text = restaurant.adres.ifBlank { "-" }

            // Formatowanie godzin otwarcia
            val formattedHours = formatOpeningHours(restaurant.godziny)
            detailHours.text = formattedHours.ifBlank { "-" }

            // Dystanse
            detailDistanceRama9.text = getString(
                R.string.distance_rama9,
                restaurant.pieszoRama9.ifBlank { "-" }
            )
            detailDistanceSukh101.text = getString(
                R.string.distance_sukh101,
                restaurant.pieszoSukh101.ifBlank { "-" }
            )

            // Przycisk Google Maps
            openMapsButton.setOnClickListener {
                openGoogleMaps(restaurant)
            }
        }
    }

    private fun formatOpeningHours(hours: String): String {
        return hours.replace(" | ", "\n")
    }

    private fun openGoogleMaps(restaurant: Restaurant) {
        val url = restaurant.link.ifBlank {
            // Fallback: szukaj po adresie
            "https://www.google.com/maps/search/?api=1&query=${Uri.encode(restaurant.adresDoMapy)}"
        }

        try {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
            startActivity(intent)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}
