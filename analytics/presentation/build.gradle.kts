plugins {
    alias(libs.plugins.runningTracker.android.feature.ui)
}

android {
    namespace = "com.abi.analytics.presentation"
}

dependencies {
    implementation(project(":analytics:domain"))
}