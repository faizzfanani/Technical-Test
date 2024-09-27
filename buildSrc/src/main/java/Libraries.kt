object Libraries {
    //appcompat
    const val appCompat = "androidx.appcompat:appcompat:${Versions.appCompatVersion}"

    //activity
    const val activity = "androidx.activity:activity-ktx:${Versions.activityVersion}"

    //material
    const val material = "com.google.android.material:material:${Versions.materialVersion}"

    //kotlin
    const val kotlin = "androidx.core:core-ktx:${Versions.kotlinVersion}"

    //test
    const val junit = "junit:junit:${Versions.junitVersion}"
    const val testJunit = "androidx.test.ext:junit:${Versions.testJunitVersion}"
    const val espresso = "androidx.test.espresso:espresso-core:${Versions.espressoVersion}"

    //dagger-hilt
    const val daggerHilt = "com.google.dagger:hilt-android:${Versions.daggerVersion}"
    const val daggerCompiler = "com.google.dagger:hilt-android-compiler:${Versions.daggerVersion}"
    const val hiltCompiler = "androidx.hilt:hilt-compiler:${Versions.hiltVersion}"

    //okhttp3
    const val okhttp = "com.squareup.okhttp3:okhttp:${Versions.okhttpVersion}"
    const val okhttpInterceptor = "com.squareup.okhttp3:logging-interceptor:${Versions.okhttpVersion}"

    //coroutines
    const val coroutinesCore = "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.coroutinesVersion}"
    const val coroutinesAndroid = "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.coroutinesVersion}"

    //retrofit2
    const val retrofit = "com.squareup.retrofit2:retrofit:${Versions.retrofitVersion}"
    const val retrofitConverterGson = "com.squareup.retrofit2:converter-gson:${Versions.retrofitVersion}"

    //chucker
    const val chucker = "com.github.chuckerteam.chucker:library:${Versions.chuckerVersion}"

    //room
    const val room = "androidx.room:room-ktx:${Versions.roomVersion}"
    const val roomRuntime = "androidx.room:room-runtime:${Versions.roomVersion}"
    const val roomCompiler = "androidx.room:room-compiler:${Versions.roomVersion}"

    //livedata & viewModel
    const val lifecycleViewModel = "androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.lifecycleVersion}"
    const val lifecycleLivedata = "androidx.lifecycle:lifecycle-livedata-ktx:${Versions.lifecycleVersion}"
    const val lifecycleCommon = "androidx.lifecycle:lifecycle-common-java8:${Versions.lifecycleVersion}"
    const val lifecycleProcess = "androidx.lifecycle:lifecycle-process:${Versions.lifecycleVersion}"

    //swipe refresh layout
    const val swipeRefresh = "androidx.swiperefreshlayout:swiperefreshlayout:${Versions.swipeRefreshVersion}"

    //shimmer animation
    const val shimmer = "com.facebook.shimmer:shimmer:${Versions.shimmerVersion}"

    //glide
    const val glide = "com.github.bumptech.glide:glide:${Versions.glideVersion}"
    const val glideKsp = "com.github.bumptech.glide:ksp:${Versions.glideVersion}"

    //multidex
    const val multidex = "androidx.multidex:multidex:${Versions.multidexVersion}"

    //navigation
    const val navigationFragment = "androidx.navigation:navigation-fragment-ktx:${Versions.navigationVersion}"
    const val navigationUI = "androidx.navigation:navigation-ui-ktx:${Versions.navigationVersion}"

    //leak-canary
    const val leakCanary = "com.squareup.leakcanary:leakcanary-android:${Versions.leakCanaryVersion}"
}