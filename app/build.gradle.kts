plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("com.google.devtools.ksp")
    id("com.google.dagger.hilt.android")
    kotlin("kapt")
}

android {
    namespace = Config.baseNameSpace
    compileSdk = Config.targetSdk
    flavorDimensions.add("version")

    defaultConfig {
        applicationId = Config.applicationId
        minSdk = Config.minSdk
        targetSdk = Config.targetSdk
        versionCode = 1
        versionName = "1.0"

        multiDexEnabled = true

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            isDebuggable = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
        debug {
            isMinifyEnabled = false
            isDebuggable = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        viewBinding = true
    }
    kapt {
        correctErrorTypes = true
    }
}

dependencies {

    implementation(project(":core"))
    implementation(project(":navigation"))

    implementation(Libraries.activity)
    implementation(Libraries.material)
    implementation("androidx.appcompat:appcompat:1.7.0")
    implementation("com.google.android.material:material:1.12.0")
    implementation("androidx.activity:activity:1.9.2")

    testImplementation(Libraries.junit)
    androidTestImplementation(Libraries.testJunit)
    androidTestImplementation(Libraries.espresso)

    // multidex
    implementation(Libraries.multidex)

    // dagger-hilt
    implementation(Libraries.daggerHilt)
    kapt(Libraries.daggerCompiler)
    kapt(Libraries.hiltCompiler)

    //leak canary
    debugImplementation(Libraries.leakCanary)

    //feature modules
    implementation(project(":feature:github_user"))
}