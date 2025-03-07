plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.ksp)
    alias(libs.plugins.hilt)
    kotlin("kapt")
}

android {
    namespace = Config.coreStorageNamespace
    compileSdk = Config.targetSdk
    flavorDimensions.add("version")
    defaultConfig {
        minSdk = Config.minSdk

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
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
    kapt {
        correctErrorTypes = true
    }
    ksp {
        arg("room.schemaLocation", "$projectDir/schemas")
    }
}


dependencies {
    api (project(":core"))

    // Unit Testing
    testImplementation(libs.junit)

    // Android Instrumentation Testing
    androidTestImplementation(libs.test.junit)
    androidTestImplementation(libs.espresso)

    // room
    api (libs.room)
    api (libs.room.runtime)
    annotationProcessor (libs.room.compiler)
    ksp (libs.room.compiler)

    // dagger-hilt
    implementation(libs.dagger)
    ksp(libs.dagger.compiler)
    ksp(libs.hilt.compiler)
}