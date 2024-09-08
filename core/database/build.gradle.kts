plugins {
    alias(libs.plugins.runningTracker.android.library)
}

android {
    namespace = "com.abi.core.database"
}

dependencies {

    implementation(libs.org.mongodb.bson)
    implementation(project(":core:domain"))
}