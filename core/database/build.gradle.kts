plugins {
    alias(libs.plugins.sacalacuenta.android.library)
    alias(libs.plugins.sacalacuenta.android.hilt)
    alias(libs.plugins.sacalacuenta.android.room)
}

android {
    namespace = "${Configuration.NAME_SPACE}.core.database"
}

dependencies {
}