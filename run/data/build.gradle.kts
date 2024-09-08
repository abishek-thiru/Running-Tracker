plugins {
    alias(libs.plugins.runningTracker.android.library)
}

android {
    namespace = "com.abi.run.data"
}

dependencies {

    implementation(libs.kotlinx.coroutines.core)
    implementation(libs.google.android.gms.play.services.location)
    implementation(libs.androidx.work)
    implementation(libs.koin.android.workmanager)
    implementation(libs.kotlinx.serialization.json)

    implementation(project(":core:domain"))
    implementation(project(":run:domain"))
    implementation(project(":core:database"))
}