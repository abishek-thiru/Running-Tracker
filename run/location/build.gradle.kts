plugins {
    alias(libs.plugins.runningTracker.android.library)
}

android {
    namespace = "com.abi.run.location"
}

dependencies {

    implementation(libs.androidx.core.ktx)

    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.compose.material3)
    debugImplementation(libs.androidx.compose.ui.tooling)

    implementation(libs.kotlinx.coroutines.core)
    implementation(libs.google.android.gms.play.services.location)

    implementation(project(":core:domain"))
    implementation(project(":run:domain"))
}