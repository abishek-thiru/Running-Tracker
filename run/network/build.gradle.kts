plugins {
    alias(libs.plugins.runningTracker.android.library)
    alias(libs.plugins.runningTracker.jvm.ktor)
}

android {
    namespace = "com.abi.run.network"
}

dependencies {
    implementation(libs.bundles.koin)

    implementation(project(":core:domain"))
    implementation(project(":core:data"))
}