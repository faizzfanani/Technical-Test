plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("com.google.devtools.ksp")
    id("com.google.dagger.hilt.android")
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

    productFlavors {
        create("staging"){
            dimension = "version"
            resValue("string", "app_name", "Staging Livin Merchant")
        }
        create("production"){
            dimension = "version"
            resValue("string", "app_name", "Livin Merchant")
        }
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

    testImplementation(Libraries.junit)
    androidTestImplementation(Libraries.testJunit)
    androidTestImplementation(Libraries.espresso)

    // room
    api (Libraries.room)
    api (Libraries.roomRuntime)
    annotationProcessor (Libraries.roomCompiler)
    ksp (Libraries.roomCompiler)

    // dagger-hilt
    implementation(Libraries.daggerHilt)
    kapt(Libraries.daggerCompiler)
    kapt (Libraries.hiltCompiler)
}