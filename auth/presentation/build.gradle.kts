plugins {
    alias(libs.plugins.runningTracker.android.feature.ui)
}

android {
    namespace = "com.abi.auth.presentation"
}

dependencies {
    implementation(project(":core:domain"))
    implementation(project(":auth:domain"))
}