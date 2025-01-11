plugins {
    alias(libs.plugins.sacalacuenta.android.library)
}

android {
    namespace = "${Configuration.NAME_SPACE}.core.viewmodel"
}

dependencies {
    api(projects.core.common)
    api(libs.androidx.lifecycle.viewmodel)
}