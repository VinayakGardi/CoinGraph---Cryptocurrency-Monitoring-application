package com.vinayakgardi.coingraph.main

object Utilities {

    fun roundToTwoDecimals(number: Double): Double {
        return String.format("%.2f", number).toDouble()
    }
}