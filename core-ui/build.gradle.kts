plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("com.google.devtools.ksp")
    id("com.google.dagger.hilt.android")
    kotlin("kapt")
}

android {
    namespace = Config.coreUINamespace
    compileSdk = Config.targetSdk
    flavorDimensions.add("version")

    defaultConfig {
        minSdk = Config.minSdk

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
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
    productFlavors {
        create("staging"){
            dimension = "version"
            resValue("string", "app_name", "Staging Technical Test")
        }
        create("production"){
            dimension = "version"
            resValue("string", "app_name", "Technical Test")
        }
    }
    kapt {
        correctErrorTypes = true
    }
    buildFeatures {
        viewBinding = true
    }
}

dependencies {

    api (project(":core"))
    api (project(":navigation"))

    api (Libraries.appCompat)
    api (Libraries.activity)
    api (Libraries.material)

    testImplementation (Libraries.junit)
    androidTestImplementation (Libraries.testJunit)
    androidTestImplementation (Libraries.espresso)

    // shimmer animation
    api (Libraries.shimmer)

    // glide
    api (Libraries.glide)
    ksp (Libraries.glideKsp)

    // swipe refresh layout
    api (Libraries.swipeRefresh)

    // livedata & viewModel
    api (Libraries.lifecycleViewModel)
    api (Libraries.lifecycleLivedata)
    api (Libraries.lifecycleCommon)
    api (Libraries.lifecycleProcess)

    // dagger-hilt
    implementation (Libraries.daggerHilt)
    kapt (Libraries.daggerCompiler)
    kapt (Libraries.hiltCompiler)

}