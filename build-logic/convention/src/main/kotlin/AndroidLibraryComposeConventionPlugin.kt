import com.android.build.api.dsl.LibraryExtension
import com.eddymy1304.sacalacuenta.configureCompose
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.getByType

class AndroidLibraryComposeConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {

        with(target) {

            pluginManager.apply("com.android.library")

            val extension = extensions.getByType<LibraryExtension>()
            configureCompose(extension)
        }
    }
}