pluginManagement {
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

rootProject.name = "rido"
include(":app")
include(":source")
include(":core_network")
include(":core_data")
include(":feature_my_library")
include(":core_domain")
include(":core")
include(":util")
include(":feature_viewer")
