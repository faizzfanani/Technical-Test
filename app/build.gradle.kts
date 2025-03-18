plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.ksp)
    alias(libs.plugins.hilt)
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

    // Internal module(s)
    implementation(project(Config.coreModule))
    implementation(project(Config.navigationModule))
    implementation(project(Config.featureGithubModule))

    implementation(libs.material)
    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)

    // Unit Testing
    testImplementation(libs.junit)

    // Android Instrumentation Testing
    androidTestImplementation(libs.test.junit)
    androidTestImplementation(libs.espresso)

    // multidex
    implementation(libs.multidex)

    // dagger-hilt
    implementation(libs.dagger)
    ksp(libs.dagger.compiler)
    kapt(libs.hilt.compiler)

    //leak canary
    debugImplementation(libs.leak.canary)

}