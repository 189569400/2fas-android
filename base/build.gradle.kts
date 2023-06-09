@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    alias(libs.plugins.twofasAndroidLibrary)
    alias(libs.plugins.twofasCompose)
}

android {
    namespace = "com.twofasapp.base"
}

dependencies {
    implementation(project(":core:di"))
    implementation(project(":prefs"))
    implementation(project(":resources"))

    implementation(libs.bundles.appCompat)
    implementation(libs.bundles.rxJava)
    implementation(libs.bundles.compose)

    implementation(libs.kotlinCoroutines)
    implementation(libs.timber)
}