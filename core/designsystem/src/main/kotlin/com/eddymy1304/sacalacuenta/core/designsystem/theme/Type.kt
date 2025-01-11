package com.eddymy1304.sacalacuenta.core.designsystem.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.googlefonts.Font
import androidx.compose.ui.text.googlefonts.GoogleFont
import com.eddymy1304.sacalacuenta.core.designsystem.R

private val provider = GoogleFont.Provider(
        providerAuthority = "com.google.android.gms.fonts",
        providerPackage = "com.google.android.gms",
        certificates = R.array.com_google_android_gms_fonts_certs
)

private val fontName = GoogleFont("Sofadi One")

private val fontFamily = FontFamily(
        Font(googleFont = fontName, fontProvider = provider)
)

private val typographyDefault = Typography()
val myTypography = Typography(
        displayLarge = typographyDefault.displayLarge.copy(fontFamily = fontFamily),
        displayMedium = typographyDefault.displayMedium.copy(fontFamily = fontFamily),
        displaySmall = typographyDefault.displaySmall.copy(fontFamily = fontFamily),

        headlineLarge = typographyDefault.headlineLarge.copy(fontFamily = fontFamily),
        headlineMedium = typographyDefault.headlineMedium.copy(fontFamily = fontFamily),
        headlineSmall = typographyDefault.headlineSmall.copy(fontFamily = fontFamily),

        titleLarge = typographyDefault.titleLarge.copy(fontFamily = fontFamily),
        titleMedium = typographyDefault.titleMedium.copy(fontFamily = fontFamily),
        titleSmall = typographyDefault.titleSmall.copy(fontFamily = fontFamily),

        bodyLarge = typographyDefault.bodyLarge.copy(fontFamily = fontFamily),
        bodyMedium = typographyDefault.bodyMedium.copy(fontFamily = fontFamily),
        bodySmall = typographyDefault.bodySmall.copy(fontFamily = fontFamily),

        labelLarge = typographyDefault.labelLarge.copy(fontFamily = fontFamily),
        labelMedium = typographyDefault.labelMedium.copy(fontFamily = fontFamily),
        labelSmall = typographyDefault.labelSmall.copy(fontFamily = fontFamily),
)

// Set of Material typography styles to start with
/*
val Typography = Typography(
        bodyLarge = TextStyle(
                fontFamily = FontFamily.Default,
                fontWeight = FontWeight.Normal,
                fontSize = 16.sp,
                lineHeight = 24.sp,
                letterSpacing = 0.5.sp
        )
         Other default text styles to override
    titleLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 22.sp,
        lineHeight = 28.sp,
        letterSpacing = 0.sp
    ),
    labelSmall = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Medium,
        fontSize = 11.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.5.sp
    )

)*/