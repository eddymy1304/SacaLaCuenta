plugins {
    alias(libs.plugins.sacalacuenta.android.library)
    alias(libs.plugins.sacalacuenta.android.library.compose)
}

android {
    namespace = "${Configuration.NAME_SPACE}.core.ui"
}

dependencies {
    api(projects.core.designsystem)
    api(projects.core.model)
}