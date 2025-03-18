plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.ksp)
    alias(libs.plugins.hilt)
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

    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.10"
    }

    kotlinOptions {
        jvmTarget = "1.8"
    }

    kapt {
        correctErrorTypes = true
    }
    buildFeatures {
        viewBinding = true
        compose = true
    }
}

dependencies {

    // Internal module(s)
    api (project(Config.coreModule))
    api (project(Config.navigationModule))

    api (libs.appcompat)
    api (libs.activity)
    api (libs.material)

    // Jetpack compose
    implementation(libs.compose.ui)
    implementation(libs.compose.material)
    implementation(libs.compose.tooling.preview)

    // Unit Testing
    testImplementation(libs.junit)

    // Android Instrumentation Testing
    androidTestImplementation(libs.test.junit)
    androidTestImplementation(libs.espresso)

    // shimmer animation
    api (libs.shimmer)

    // glide
    api (libs.glide)
    ksp (libs.glide.ksp)

    // swipe refresh layout
    api (libs.swipe.refresh)

    // livedata & viewModel
    api (libs.lifecycle.viewmodel)
    api (libs.lifecycle.livedata)
    api (libs.lifecycle.common)
    api (libs.lifecycle.process)

    // dagger-hilt
    implementation(libs.dagger)
    ksp(libs.dagger.compiler)
    kapt(libs.hilt.compiler)

    // jetpack compose
    api(libs.compose.ui)
    api(libs.compose.material)
    api(libs.compose.tooling.preview)
    api(libs.compose.lifecycle)
    api(libs.compose.activity)
    api(libs.androidx.material3)
    debugApi(libs.compose.ui.tooling)

    // coil image loader
    api(libs.coil.compose)

    // compose shimmer
    api(libs.accompanist.placeholder.material)
}