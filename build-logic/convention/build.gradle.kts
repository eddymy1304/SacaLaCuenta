import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    `kotlin-dsl`
}

group = "com.eddymy1304.sacalacuenta.buildlogic"

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

kotlin {
    compilerOptions {
        jvmTarget = JvmTarget.JVM_17
    }
}

dependencies {
    compileOnly(libs.android.gradle.plugin)
    compileOnly(libs.android.tools.common)
    compileOnly(libs.kotlin.gradle.plugin)
}

gradlePlugin {
    plugins {
        register("androidApplication") {
            id = "sacalacuenta.android.application"
            implementationClass = "AndroidApplicationConventionPlugin"
        }

        register("androidLibrary") {
            id = "sacalacuenta.android.library"
            implementationClass = "AndroidLibraryConventionPlugin"
        }

        register("androidTest") {
            id = "sacalacuenta.android.test"
            implementationClass = "AndroidTestConventionPlugin"
        }

        register("compose") {
            id = "sacalacuenta.compose"
            implementationClass = "ComposeConventionPlugin"
        }
    }
}