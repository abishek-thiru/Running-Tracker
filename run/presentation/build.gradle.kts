plugins {
    alias(libs.plugins.runningTracker.android.feature.ui)
}

android {
    namespace = "com.abi.run.presentation"
}

dependencies {

    implementation(libs.coil.compose)
    implementation(libs.google.maps.android.compose)
    implementation(libs.androidx.activity.compose)
    implementation(libs.timber)

    implementation(project(":core:domain"))
    implementation(project(":run:domain"))
}