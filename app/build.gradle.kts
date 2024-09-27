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

    productFlavors {
        create("staging"){
            dimension = "version"
            applicationIdSuffix = ".staging"
            resValue("string", "app_name", "Staging Technical Test")
        }
        create("production"){
            dimension = "version"
            resValue("string", "app_name", "Technical Test")
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

//    implementation(project(":core"))
//    implementation(project(":navigation"))

    implementation(Libraries.activity)
    implementation(Libraries.material)

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
//    implementation(project(":feature:news"))
//    implementation(project(":feature:destination"))
}