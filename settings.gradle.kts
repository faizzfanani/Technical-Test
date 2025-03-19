pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}

dependencyResolutionManagement {
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "technical-test"
include(":app")
include(":core")
include(":core-ui")
include(":core-storage")
include(":service:github")
include(":service:pcs-user")
include(":navigation")
include(":feature:github_user")
include(":feature:pcs-user")
