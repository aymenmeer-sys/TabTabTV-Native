package com.tabtab.tv.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.sp

/**
 * Two type scales per the design system: TV is read from a couch ("10-foot UI") so
 * every size is larger than the equivalent mobile style held in the hand.
 */
fun tabTabTypography(isTv: Boolean): Typography {
    val (display, headline, title, body, label) = if (isTv) {
        listOf(48, 32, 22, 18, 14)
    } else {
        listOf(28, 22, 18, 14, 12)
    }
    return Typography(
        displayLarge = TextStyle(fontSize = display.sp),
        headlineLarge = TextStyle(fontSize = headline.sp),
        titleLarge = TextStyle(fontSize = title.sp),
        bodyLarge = TextStyle(fontSize = body.sp),
        labelLarge = TextStyle(fontSize = label.sp)
    )
}
