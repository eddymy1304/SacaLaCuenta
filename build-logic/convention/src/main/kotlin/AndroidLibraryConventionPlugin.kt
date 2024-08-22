import com.android.build.api.dsl.LibraryExtension
import com.eddymy1304.sacalacuenta.configureKotlinAndroid
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType

class AndroidLibraryConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {

            with(pluginManager) {
                apply("com.android.library")
                apply("org.jetbrains.kotlin.android")

                apply("dagger.hilt.android.plugin")
                apply("org.jetbrains.kotlin.kapt")
            }

            extensions.configure<LibraryExtension> {
                configureKotlinAndroid(this)
            }

            val libs = extensions.getByType<VersionCatalogsExtension>().named("libs")

            dependencies {
                add("implementation", libs.findLibrary("dagger.hilt").get())
                add("kapt", libs.findLibrary("dagger.hilt.compiler").get())
                //add("androidTestImplementation", libs.findLibrary("hilt.android.compiler").get())
                //add("kaptAndroidTest", libs.findLibrary("hilt.android.compiler").get())
            }
        }
    }
}