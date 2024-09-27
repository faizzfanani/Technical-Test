// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    id("com.android.application") version Versions.applicationVersion apply false
    id("org.jetbrains.kotlin.android") version Versions.jetbrainsKotlinVersion apply false
    id("com.google.dagger.hilt.android") version Versions.daggerVersion apply false
    id("com.google.devtools.ksp") version Versions.kspVersion apply false
    id("com.android.library") version Versions.applicationVersion apply false
}
buildscript {
    dependencies {
        classpath("androidx.navigation:navigation-safe-args-gradle-plugin:${Versions.navigationVersion}")
    }

    repositories {
        google()
        mavenCentral()
    }
}

allprojects {
    apply(plugin = Plugins.MavenPublish)

    repositories {
        google()
        mavenCentral()
    }
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}