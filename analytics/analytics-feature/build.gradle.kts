plugins {
    alias(libs.plugins.runningTracker.android.dynamic.feature)
}
android {
    namespace = "com.abi.analytics.analytics_feature"
}

dependencies {
    implementation(project(":app"))

    api(project(":analytics:presentation"))
    implementation(project(":analytics:domain"))
    implementation(project(":analytics:data"))
    implementation(project(":core:database"))
}