plugins {
    alias(libs.plugins.sacalacuenta.android.library)
    alias(libs.plugins.sacalacuenta.android.hilt)
}

android {
    namespace = "${Configuration.NAME_SPACE}.core.data"
}

dependencies {
    api(projects.core.datastore)
    api(projects.core.database)
}