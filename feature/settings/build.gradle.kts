plugins {
    alias(libs.plugins.sacalacuenta.android.feature)
    alias(libs.plugins.sacalacuenta.android.library.compose)
}

android {
    namespace = "${Configuration.NAME_SPACE}.feature.settings"
}

dependencies {
    implementation(projects.core.domain)
}