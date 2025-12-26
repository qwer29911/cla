package com.bangkok.restaurants.data

import java.io.Serializable

/**
 * Model danych reprezentujący restaurację w Bangkoku
 */
data class Restaurant(
    val nazwa: String,
    val adres: String,
    val adresDoMapy: String,
    val typKuchni: String,
    val dzielnica: String,
    val ceny: String,
    val ocena: String,
    val opinii: String,
    val opis: String,
    val godziny: String,
    val pieszoRama9: String,
    val pieszoSukh101: String,
    val link: String
) : Serializable {

    /**
     * Sprawdza czy restauracja pasuje do frazy wyszukiwania.
     * Przeszukuje: nazwę, opis, typ kuchni, dzielnicę.
     */
    fun matchesQuery(query: String): Boolean {
        if (query.isBlank()) return true

        val lowercaseQuery = query.lowercase().trim()

        return nazwa.lowercase().contains(lowercaseQuery) ||
               opis.lowercase().contains(lowercaseQuery) ||
               typKuchni.lowercase().contains(lowercaseQuery) ||
               dzielnica.lowercase().contains(lowercaseQuery) ||
               adres.lowercase().contains(lowercaseQuery)
    }

    /**
     * Zwraca czytelny opis ceny
     */
    fun getCenyDisplay(): String {
        return when {
            ceny.isBlank() -> "-"
            else -> ceny
        }
    }

    /**
     * Zwraca czytelną ocenę
     */
    fun getOcenaDisplay(): String {
        return when {
            ocena.isBlank() -> "-"
            else -> "$ocena"
        }
    }
}
