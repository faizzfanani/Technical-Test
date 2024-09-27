plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("com.google.devtools.ksp")
    id("com.google.dagger.hilt.android")
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

    api (Libraries.kotlin)
    implementation (Libraries.appCompat)

    testImplementation (Libraries.junit)
    androidTestImplementation (Libraries.testJunit)
    androidTestImplementation (Libraries.espresso)

    // retrofit2
    implementation (Libraries.retrofit)
    implementation (Libraries.retrofitConverterGson)

    // okhttp3
    implementation (Libraries.okhttp)
    implementation (Libraries.okhttpInterceptor)

    // dagger-hilt
    implementation (Libraries.daggerHilt)
    kapt (Libraries.daggerCompiler)
    kapt (Libraries.hiltCompiler)

    // chucker
    implementation (Libraries.chucker)

    // coroutines
    api (Libraries.coroutinesCore)
    api (Libraries.coroutinesAndroid)

}