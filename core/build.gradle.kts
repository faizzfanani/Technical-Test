plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.ksp)
    alias(libs.plugins.hilt)
    kotlin("kapt")
}

android {
    namespace = Config.coreNamespace
    compileSdk = Config.targetSdk
    flavorDimensions.add("version")
    defaultConfig {
        minSdk = Config.minSdk

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        debug {
            manifestPlaceholders.putAll(
                mapOf(
                    "GITHUB_BASE_URL" to Config.githubBaseUrl,
                    "GITHUB_API_TOKEN" to "",
                )
            )
        }
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

    kapt {
        correctErrorTypes = true
    }
}

dependencies {

    api(libs.kotlin.stdlib)
    implementation(libs.kotlin.core)
    implementation(libs.appcompat)

    // Unit Testing
    testImplementation(libs.junit)

    // Android Instrumentation Testing
    androidTestImplementation(libs.test.junit)
    androidTestImplementation(libs.espresso)

    // retrofit
    api(libs.retrofit)
    api(libs.retrofit.converter.gson)
    api(libs.okhttp)
    api(libs.okhttp.interceptor)

    // dagger-hilt
    implementation(libs.dagger)
    ksp(libs.dagger.compiler)
    kapt(libs.hilt.compiler)

    // chucker
    debugImplementation(libs.chucker)
    releaseImplementation(libs.chucker.no.op)

    // coroutines
    api (libs.coroutines.core)
    api (libs.coroutines.android)

    // timber
    api (libs.timber)
}