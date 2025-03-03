package com.example.feebee_android_project_app

import com.example.feebee_android_project_app.accountScreens.PickedState
import com.example.feebee_android_project_app.accountScreens.checkPickedState
import org.junit.Test
import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */

// Unit testing for checking pick state which determines what files to carry very important part
class PickedStateTest {
    @Test
    fun `returns DATE_RANGE when dateRangeSelected is longer than 10 characters`() {
        val result = checkPickedState(yearSelected = "All", monthSelected = "All", dateRangeSelected = "12/12/2023 - 15/12/2023")
        assertEquals(PickedState.DATE_RANGE, result)
    }

    @Test
    fun `returns DATE when dateRangeSelected is not empty but shorter than 10 characters`() {
        val result = checkPickedState(yearSelected = "All", monthSelected = "All", dateRangeSelected = "12/12/2023")
        assertEquals(PickedState.DATE, result)
    }

    @Test
    fun `returns YEAR_MONTH when both year and month are selected`() {
        val result = checkPickedState(yearSelected = "2023", monthSelected = "December", dateRangeSelected = "")
        assertEquals(PickedState.YEAR_MONTH, result)
    }

    @Test
    fun `returns MONTH when only month is selected`() {
        val result = checkPickedState(yearSelected = "All", monthSelected = "December", dateRangeSelected = "")
        assertEquals(PickedState.MONTH, result)
    }

    @Test
    fun `returns YEAR when only year is selected`() {
        val result = checkPickedState(yearSelected = "2023", monthSelected = "All", dateRangeSelected = "")
        assertEquals(PickedState.YEAR, result)
    }

    @Test
    fun `returns NONE_SELECTED when all inputs are empty or 'All'`() {
        val result = checkPickedState(yearSelected = "All", monthSelected = "All", dateRangeSelected = "")
        assertEquals(PickedState.NONE_SELECTED, result)
    }
}
