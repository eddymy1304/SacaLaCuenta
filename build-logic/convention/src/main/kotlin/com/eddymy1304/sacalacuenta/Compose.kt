package com.eddymy1304.sacalacuenta

import com.android.build.gradle.BaseExtension
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType
import org.gradle.kotlin.dsl.project

internal fun Project.configureCompose(
    commonExtension: BaseExtension
) {
    commonExtension.apply {
        buildFeatures.apply {
            compose = true
        }

        val libs = project.extensions.getByType<VersionCatalogsExtension>().named("libs")

        @Suppress("UnstableApiUsage")
        composeOptions {
            kotlinCompilerExtensionVersion =
                libs.findVersion("compose-compiler").get().toString()
        }

        dependencies {
            add("implementation", platform(libs.findLibrary("androidx.compose.bom").get()))
            add("androidTestImplementation", libs.findLibrary("androidx.ui.test.junit4").get())
            //add("androidTestImplementation", project(":core:testing"))
        }
    }
}