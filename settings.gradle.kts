pluginManagement {
    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
    }
}

rootProject.name = "technical-test"
include(":app")
include(":core")
include(":core-storage")
include(":service:github")
include(":navigation")
include(":core-ui")
include(":feature:github_user")
