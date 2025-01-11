plugins {
    alias(libs.plugins.sacalacuenta.android.library)
}

android {
    namespace = "${Configuration.NAME_SPACE}.core.common"
}

dependencies {
    implementation(libs.androidx.core.ktx)
}