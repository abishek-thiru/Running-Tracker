plugins {
    alias(libs.plugins.runningTracker.jvm.library)
}

dependencies {
    implementation(libs.kotlinx.coroutines.core)

    implementation(project(":core:domain"))
}