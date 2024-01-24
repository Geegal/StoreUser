package com.example.storeuser.common

import com.example.storeuser.R
import com.example.storeuser.model.AboutItem
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

fun generateDummyAbout(): List<AboutItem> {
    return listOf(
        AboutItem(
            image = R.drawable.ic_orders,
            title = "Orders"
        )
    )
}

fun generate7DayBefore(): String {
    val currentDateTime = LocalDateTime.now()
    // Lấy ngày 7 ngày trước
    val sevenDaysAgo = currentDateTime.minusDays(7)
    // Định dạng ngày thành chuỗi
    val dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
    return sevenDaysAgo.format(dateFormatter)
}

fun generate1MonthBefore(): String{
    // Lấy ngày hiện tại
    val currentDateTime = LocalDateTime.now()

    // Lấy ngày 1 tháng trước
    val oneMonthAgo = currentDateTime.minusMonths(1)

    // Định dạng ngày thành chuỗi
    val dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
    return oneMonthAgo.format(dateFormatter)
}

fun generate3MonthsBefore(): String{
    // Lấy ngày hiện tại
    val currentDateTime = LocalDateTime.now()

    // Lấy ngày 1 tháng trước
    val threeMonthAgo = currentDateTime.minusMonths(3)

    // Định dạng ngày thành chuỗi
    val dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
    return threeMonthAgo.format(dateFormatter)
}

fun generate6MonthsBefore(): String{
    // Lấy ngày hiện tại
    val currentDateTime = LocalDateTime.now()

    // Lấy ngày 1 tháng trước
    val sixMonthAgo = currentDateTime.minusMonths(6)

    // Định dạng ngày thành chuỗi
    val dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
    return sixMonthAgo.format(dateFormatter)
}


