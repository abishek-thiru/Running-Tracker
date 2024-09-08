plugins {
    alias(libs.plugins.runningTracker.jvm.library)
}

dependencies {
    implementation(project(":core:domain"))
}
