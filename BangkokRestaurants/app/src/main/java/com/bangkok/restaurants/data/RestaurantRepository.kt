package com.bangkok.restaurants.data

import android.content.Context
import com.bangkok.restaurants.utils.CsvParser

/**
 * Repozytorium do zarządzania danymi restauracji.
 */
class RestaurantRepository(private val context: Context) {

    private var allRestaurants: List<Restaurant> = emptyList()

    /**
     * Wczytuje wszystkie restauracje z CSV.
     */
    fun loadAllRestaurants(): List<Restaurant> {
        if (allRestaurants.isEmpty()) {
            allRestaurants = CsvParser.loadRestaurants(context)
        }
        return allRestaurants
    }

    /**
     * Wyszukuje restauracje po frazie kluczowej.
     */
    fun searchRestaurants(query: String): List<Restaurant> {
        if (query.isBlank()) {
            return allRestaurants
        }
        return allRestaurants.filter { it.matchesQuery(query) }
    }

    /**
     * Zwraca liczbę wszystkich restauracji.
     */
    fun getRestaurantCount(): Int = allRestaurants.size

    /**
     * Filtruje restauracje po dzielnicy.
     */
    fun getRestaurantsByDistrict(district: String): List<Restaurant> {
        return allRestaurants.filter {
            it.dzielnica.equals(district, ignoreCase = true)
        }
    }

    /**
     * Zwraca listę unikalnych dzielnic.
     */
    fun getAllDistricts(): List<String> {
        return allRestaurants
            .map { it.dzielnica }
            .filter { it.isNotBlank() }
            .distinct()
            .sorted()
    }

    /**
     * Zwraca listę unikalnych typów kuchni.
     */
    fun getAllCuisineTypes(): List<String> {
        return allRestaurants
            .map { it.typKuchni }
            .filter { it.isNotBlank() }
            .distinct()
            .sorted()
    }
}
