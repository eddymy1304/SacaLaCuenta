plugins {
    alias(libs.plugins.sacalacuenta.android.library)
    alias(libs.plugins.sacalacuenta.android.hilt)
}

android {
    namespace = "${Configuration.NAME_SPACE}.core.domain"
}

dependencies {
    api(projects.core.data)
    api(projects.core.model)
}