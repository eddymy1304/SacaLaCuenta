pluginManagement {

    includeBuild("build-logic")

    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "SacaLaCuenta"

enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")
include(":app")
include(":core:database")
include(":core:datastore")
include(":core:domain")
include(":core:data")
include(":core:model")
include(":core:designsystem")
include(":core:ui")
include(":core:viewmodel")
include(":core:common")
include(":feature:receipt")
include(":feature:history")
include(":feature:ticket")
include(":feature:settings")
