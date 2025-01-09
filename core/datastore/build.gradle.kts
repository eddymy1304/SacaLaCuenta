plugins {
    alias(libs.plugins.sacalacuenta.android.library)
    alias(libs.plugins.sacalacuenta.android.hilt)
}

android {
    namespace = Configuration.NAME_SPACE
}

dependencies {
    implementation(libs.androidx.datastore.preferences)
    implementation(libs.androidx.datastore.datastore)
}