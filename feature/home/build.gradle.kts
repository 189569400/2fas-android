@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    alias(libs.plugins.twofasAndroidLibrary)
    alias(libs.plugins.twofasCompose)
}

android {
    namespace = "com.twofasapp.feature.home"
}

dependencies {
    implementation(project(":core:di"))
    implementation(project(":data:notifications"))
    implementation(project(":data:services"))
    implementation(project(":data:session"))
    implementation(project(":core:common"))
    implementation(project(":core:locale"))
    implementation(project(":core:designsystem"))
    implementation(project(":backup:domain"))

    implementation(libs.bundles.compose)
    implementation(libs.bundles.viewModel)
    implementation(libs.bundles.accompanist)
}