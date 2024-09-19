package com.eddymy1304.sacalacuenta.utils

import androidx.compose.material3.Typography
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.googlefonts.*
import com.eddymy1304.sacalacuenta.R

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