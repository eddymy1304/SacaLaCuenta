plugins {
    alias(libs.plugins.sacalacuenta.android.feature)
    alias(libs.plugins.sacalacuenta.android.library.compose)
}

android {
    namespace = "${Configuration.NAME_SPACE}.feature.receipt"
}

dependencies {
    implementation(projects.core.domain)
}