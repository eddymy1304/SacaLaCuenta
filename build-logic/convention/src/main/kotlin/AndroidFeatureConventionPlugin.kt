import com.eddymy1304.sacalacuenta.implementation
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType

class AndroidFeatureConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {

            with(pluginManager) {
                apply("sacalacuenta.android.library")
                apply("sacalacuenta.android.hilt")
                apply("org.jetbrains.kotlin.plugin.serialization")
            }

            val libs = extensions.getByType<VersionCatalogsExtension>().named("libs")

            dependencies {

                implementation(project(":core:ui"))
                implementation(project(":core:designsystem"))
                implementation(project(":core:viewmodel"))

                listOf(
                    libs.findLibrary("androidx.hilt.navigation").get(),
                    libs.findLibrary("androidx.lifecycle.runtime.ktx").get(),
                    libs.findLibrary("androidx.lifecycle.viewmodel").get(),
                    libs.findLibrary("androidx.navigation.compose").get(),
                    libs.findLibrary("kotlinx.serialization.json").get(),
                ).forEach {
                    implementation(it)
                }
            }

        }
    }
}