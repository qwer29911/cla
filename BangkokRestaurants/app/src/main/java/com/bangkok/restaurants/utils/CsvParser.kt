package com.bangkok.restaurants.utils

import android.content.Context
import com.bangkok.restaurants.data.Restaurant
import java.io.BufferedReader
import java.io.InputStreamReader

/**
 * Parser plików CSV z danymi restauracji.
 * Obsługuje pliki z separatorem ; (średnik) lub , (przecinek).
 */
object CsvParser {

    private const val CSV_FILENAME = "restauracje-all.csv"

    /**
     * Wczytuje restauracje z pliku CSV w assets.
     */
    fun loadRestaurants(context: Context): List<Restaurant> {
        val restaurants = mutableListOf<Restaurant>()

        try {
            context.assets.open(CSV_FILENAME).use { inputStream ->
                BufferedReader(InputStreamReader(inputStream, Charsets.UTF_8)).use { reader ->
                    // Pomijamy nagłówek
                    val header = reader.readLine() ?: return emptyList()

                    // Wykrywamy separator (średnik lub przecinek)
                    val separator = detectSeparator(header)

                    // Czytamy kolejne linie
                    reader.forEachLine { line ->
                        if (line.isNotBlank()) {
                            val restaurant = parseLine(line, separator)
                            if (restaurant != null) {
                                restaurants.add(restaurant)
                            }
                        }
                    }
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return restaurants
    }

    /**
     * Wykrywa separator używany w pliku CSV.
     */
    private fun detectSeparator(headerLine: String): Char {
        val semicolonCount = headerLine.count { it == ';' }
        val commaCount = headerLine.count { it == ',' }
        return if (semicolonCount >= commaCount) ';' else ','
    }

    /**
     * Parsuje pojedynczą linię CSV do obiektu Restaurant.
     * Obsługuje wartości w cudzysłowach.
     */
    private fun parseLine(line: String, separator: Char): Restaurant? {
        val values = parseCSVLine(line, separator)

        // Upewniamy się, że mamy wystarczającą liczbę kolumn
        if (values.size < 13) {
            // Dopełniamy brakujące kolumny pustymi stringami
            val paddedValues = values.toMutableList()
            while (paddedValues.size < 13) {
                paddedValues.add("")
            }
            return createRestaurant(paddedValues)
        }

        return createRestaurant(values)
    }

    /**
     * Parsuje linię CSV z obsługą cudzysłowów.
     */
    private fun parseCSVLine(line: String, separator: Char): List<String> {
        val result = mutableListOf<String>()
        val currentValue = StringBuilder()
        var inQuotes = false

        for (char in line) {
            when {
                char == '"' -> {
                    inQuotes = !inQuotes
                }
                char == separator && !inQuotes -> {
                    result.add(currentValue.toString().trim())
                    currentValue.clear()
                }
                else -> {
                    currentValue.append(char)
                }
            }
        }

        // Dodajemy ostatnią wartość
        result.add(currentValue.toString().trim())

        return result
    }

    /**
     * Tworzy obiekt Restaurant z listy wartości.
     */
    private fun createRestaurant(values: List<String>): Restaurant {
        return Restaurant(
            nazwa = values.getOrElse(0) { "" },
            adres = values.getOrElse(1) { "" },
            adresDoMapy = values.getOrElse(2) { "" },
            typKuchni = values.getOrElse(3) { "" },
            dzielnica = values.getOrElse(4) { "" },
            ceny = values.getOrElse(5) { "" },
            ocena = values.getOrElse(6) { "" },
            opinii = values.getOrElse(7) { "" },
            opis = values.getOrElse(8) { "" },
            godziny = values.getOrElse(9) { "" },
            pieszoRama9 = values.getOrElse(10) { "" },
            pieszoSukh101 = values.getOrElse(11) { "" },
            link = values.getOrElse(12) { "" }
        )
    }
}
