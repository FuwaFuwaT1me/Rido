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
include(":core_domain")
include(":core")
include(":util")
include(":common_compose")
include(":common")
include(":feature_my_library:impl")
include(":feature_my_library:api")
include(":feature_viewer:api")
include(":feature_viewer:impl")
