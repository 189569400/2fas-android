@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    alias(libs.plugins.twofasAndroidLibrary)
    alias(libs.plugins.kotlinSerialization)
}

android {
    namespace = "com.twofasapp.data.session"
}

dependencies {
    implementation(project(":core:di"))
    implementation(project(":core:storage"))
    implementation(project(":core:common"))
    implementation(project(":prefs"))

    implementation(libs.kotlinCoroutines)
    implementation(libs.kotlinSerialization)
    implementation(libs.workManager)
    implementation(libs.timber)
}