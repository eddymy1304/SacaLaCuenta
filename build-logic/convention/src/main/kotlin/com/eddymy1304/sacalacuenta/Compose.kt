package com.eddymy1304.sacalacuenta

import com.android.build.api.dsl.CommonExtension
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.assign
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType
import org.jetbrains.kotlin.compose.compiler.gradle.ComposeCompilerGradlePluginExtension
import org.jetbrains.kotlin.compose.compiler.gradle.ComposeFeatureFlag

internal fun Project.configureCompose(
    commonExtension: CommonExtension<*, *, *, *, *, *>
) {
    pluginManager.apply("org.jetbrains.kotlin.plugin.compose")

    commonExtension.apply {
        buildFeatures {
            compose = true
        }

        val libs = project.extensions.getByType<VersionCatalogsExtension>().named("libs")

        dependencies {
            listOf(
                platform(libs.findLibrary("androidx.compose.bom").get()),
                libs.findLibrary("androidx.activity.compose").get(),
                libs.findLibrary("androidx.navigation.compose").get(),
                libs.findLibrary("androidx.ui.tooling").get(),
                libs.findLibrary("androidx.ui.tooling.preview").get(),
                libs.findLibrary("androidx.constraintlayout.compose").get(),
                libs.findLibrary("androidx.compose.foundation").get()
            ).forEach {
                implementation(it)
            }
        }
    }

    extensions.configure<ComposeCompilerGradlePluginExtension> {
        //enableStrongSkippingMode = true
        featureFlags = setOf(
            //ComposeFeatureFlag.IntrinsicRemember.disabled(),
            ComposeFeatureFlag.OptimizeNonSkippingGroups,
            //ComposeFeatureFlag.StrongSkipping
        )

        reportsDestination = layout.buildDirectory.dir("compose_compiler")
    }
}