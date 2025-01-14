plugins {
    alias(libs.plugins.runningTracker.android.library)
    alias(libs.plugins.runningTracker.android.room)
}

android {
    namespace = "com.abi.analytics.data"
}

dependencies {
    implementation(libs.kotlinx.coroutines.core)
    implementation(libs.bundles.koin)

    implementation(project(":core:domain"))
    implementation(project(":analytics:domain"))
    implementation(project(":core:database"))
}