plugins {
    alias(libs.plugins.runningTracker.android.library)
    alias(libs.plugins.runningTracker.jvm.ktor)
}

android {
    namespace = "com.abi.auth.data"
}

dependencies {
    implementation(libs.bundles.koin)
    implementation(project(":auth:domain"))
    implementation(project(":core:domain"))
    implementation(project(":core:data"))
}