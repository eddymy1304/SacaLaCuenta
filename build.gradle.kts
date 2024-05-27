// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.devtools.ksp) apply false
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.jetbrains.kotlin.android) apply false
    alias(libs.plugins.jetbrains.kotlin.kapt) apply false
    alias(libs.plugins.hilt) apply false
    alias(libs.plugins.room) apply false

    alias(libs.plugins.compose.compiler) apply false

    kotlin("jvm") version "2.0.0"
    kotlin("plugin.serialization") version "2.0.0"
}