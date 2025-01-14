plugins {
    alias(libs.plugins.firechat.android.feature)
    alias(libs.plugins.firechat.android.library.compose)
    alias(libs.plugins.firechat.android.library.jacoco)
}

android {
    namespace = "com.trodar.authentication"
}

dependencies {
    implementation(projects.core.ui)
}