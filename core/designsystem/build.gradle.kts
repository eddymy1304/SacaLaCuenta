plugins {
    alias(libs.plugins.sacalacuenta.android.library)
    alias(libs.plugins.sacalacuenta.android.library.compose)
}

android {
    namespace = "${Configuration.NAME_SPACE}.core.designsystem"
}

dependencies {
    api(libs.androidx.ui)
    api(libs.androidx.ui.unit)
    api(libs.androidx.ui.graphics)
    api(libs.androidx.ui.tooling.preview)
    api(libs.androidx.compose.ui.fonts)
    api(libs.androidx.material3)
    api(libs.androidx.icons.extended)
}