plugins {
    alias(libs.plugins.runningTracker.android.library)
}

android {
    namespace = "com.abi.run.network"
}

dependencies {
    implementation(project(":core:domain"))
    implementation(project(":core:data"))
}