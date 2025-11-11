 package com.tonyxlab.lazypizza.presentation.theme

import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.tonyxlab.lazypizza.R

val InstrumentSansFamily = FontFamily(
        Font(R.font.instrument_sans_regular),
        Font(R.font.instrument_sans_medium),
        Font(R.font.instrument_sans_semi_bold),
        Font(R.font.instrument_sans_bold)
)

val Typography = Typography(
        bodyLarge = ExtendedTypography.Body1Regular,
        labelMedium = ExtendedTypography.Label2SemiBold
)

object ExtendedTypography {

    val Title1SemiBold = TextStyle(
            fontFamily = InstrumentSansFamily,
            fontWeight = FontWeight.SemiBold,
            fontSize = 24.sp,
            lineHeight = 28.sp,
            letterSpacing = 0.sp
    )

    val Title2 = TextStyle(
            fontFamily = InstrumentSansFamily,
            fontWeight = FontWeight.SemiBold,
            fontSize = 20.sp,
            lineHeight = 24.sp,
            letterSpacing = 0.sp
    )

    val Title3 = TextStyle(
            fontFamily = InstrumentSansFamily,
            fontWeight = FontWeight.SemiBold,
            fontSize = 15.sp,
            lineHeight = 22.sp,
            letterSpacing = 0.sp
    )

    val Label2SemiBold = TextStyle(
            fontFamily = InstrumentSansFamily,
            fontWeight = FontWeight.SemiBold,
            fontSize = 12.sp,
            lineHeight = 16.sp,
            letterSpacing = 0.sp
    )

    val Body1Regular = TextStyle(
            fontFamily = InstrumentSansFamily,
            fontWeight = FontWeight.Normal,
            fontSize = 16.sp,
            lineHeight = 22.sp,
            letterSpacing = 0.sp
    )

    val Body1Medium = TextStyle(
            fontFamily = InstrumentSansFamily,
            fontWeight = FontWeight.Medium,
            fontSize = 16.sp,
            lineHeight = 22.sp,
            letterSpacing = 0.sp
    )

    val Body3Regular = TextStyle(
            fontFamily = InstrumentSansFamily,
            fontWeight = FontWeight.Normal,
            fontSize = 14.sp,
            lineHeight = 18.sp,
            letterSpacing = 0.sp
    )

    val Body3Medium = TextStyle(
            fontFamily = InstrumentSansFamily,
            fontWeight = FontWeight.Medium,
            fontSize = 14.sp,
            lineHeight = 18.sp,
            letterSpacing = 0.sp
    )

    val Body4Regular = TextStyle(
            fontFamily = InstrumentSansFamily,
            fontWeight = FontWeight.Normal,
            fontSize = 12.sp,
            lineHeight = 16.sp,
            letterSpacing = 0.sp
    )
}

val Typography.Title1SemiBold
    @Composable
    get() = ExtendedTypography.Title1SemiBold

val Typography.Title2
    @Composable
    get() = ExtendedTypography.Title2

val Typography.Title3
    @Composable
    get() = ExtendedTypography.Title3

val Typography.Label2SemiBold
    @Composable
    get() = ExtendedTypography.Label2SemiBold

val Typography.Body1Regular
    @Composable
    get() = ExtendedTypography.Body1Regular

val Typography.Body1Medium
    @Composable
    get() = ExtendedTypography.Body1Medium

val Typography.Body3Regular
    @Composable
    get() = ExtendedTypography.Body3Regular

val Typography.Body3Medium
    @Composable
    get() = ExtendedTypography.Body3Medium

val Typography.Body4Regular
    @Composable
    get() = ExtendedTypography.Body4Regular
