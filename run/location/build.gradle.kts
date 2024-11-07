plugins {
    alias(libs.plugins.runningTracker.android.library)
}

android {
    namespace = "com.abi.run.location"
}

dependencies {

    implementation(libs.androidx.core.ktx)

    implementation(libs.bundles.koin)

    implementation(libs.kotlinx.coroutines.core)
    implementation(libs.google.android.gms.play.services.location)

    implementation(project(":core:domain"))
    implementation(project(":run:domain"))
}