buildscript {
    repositories {
        google()
        mavenCentral()
    }
    dependencies {
        classpath(libs.android.gradle.plugin)
        classpath(libs.kotlin.gradle.plugin)
        classpath(libs.dagger.hilt.gradle.plugin)
    }
}

// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.devtools.ksp) apply false
    alias(libs.plugins.room) apply false

    kotlin("jvm") version "2.0.0"
    kotlin("plugin.serialization") version "2.0.0"


    alias(libs.plugins.sacalacuenta.android.application) apply false
    alias(libs.plugins.sacalacuenta.android.library) apply false
    alias(libs.plugins.sacalacuenta.android.test) apply false
    alias(libs.plugins.sacalacuenta.android.application.compose) apply false

    alias(libs.plugins.kotlin.compose.compiler) apply false

    alias(libs.plugins.sacalacuenta.android.hilt) apply false
}