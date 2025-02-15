package com.example.feebee_android_project_app.data

import java.util.Calendar

class DateData {
    companion object {
        private val  currentYear = Calendar.getInstance().get(Calendar.YEAR)
        val yearOptions = listOf("All") + (currentYear - 10..currentYear + 10).map { it.toString() }

        val monthOptions = listOf("All") + listOf(
            "January", "February", "March", "April", "May", "June",
            "July", "August", "September", "October", "November", "December"
        )
    }
}
