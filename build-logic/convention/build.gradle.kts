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
    compileOnly(libs.compose.compiler.gradle.plugin)
    compileOnly(libs.android.gradle.plugin)
    compileOnly(libs.android.tools.common)
    compileOnly(libs.kotlin.gradle.plugin)
    compileOnly(libs.ksp.gradle.plugin)
    compileOnly(libs.room.gradle.plugin)
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

        register("androidApplicationCompose") {
            id = "sacalacuenta.android.application.compose"
            implementationClass = "AndroidApplicationComposeConventionPlugin"
        }

        register("androidLibraryCompose") {
            id = "sacalacuenta.android.library.compose"
            implementationClass = "AndroidLibraryComposeConventionPlugin"
        }

        register("androidHilt") {
            id= "sacalacuenta.android.hilt"
            implementationClass = "AndroidHiltConventionPlugin"
        }

        register("androidFeature") {
            id= "sacalacuenta.android.feature"
            implementationClass = "AndroidFeatureConventionPlugin"
        }

        register("androidRoom") {
            id = "sacalacuenta.android.room"
            implementationClass = "AndroidRoomConventionPlugin"
        }
    }
}