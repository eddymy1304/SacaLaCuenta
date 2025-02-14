import com.eddymy1304.sacalacuenta.implementation
import com.eddymy1304.sacalacuenta.ksp
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType

class AndroidHiltConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {

            with(pluginManager) {
                apply("dagger.hilt.android.plugin")
                apply("com.google.devtools.ksp")
            }

            val libs = extensions.getByType<VersionCatalogsExtension>().named("libs")

            dependencies {
                listOf(
                    libs.findLibrary("dagger.hilt").get(),
                    libs.findLibrary("androidx.hilt.navigation").get()
                ).forEach {
                    implementation(it)
                }

                ksp(libs.findLibrary("dagger.hilt.compiler").get())
            }
        }
    }
}