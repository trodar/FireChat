plugins {
    alias(libs.plugins.firechat.android.feature)
    alias(libs.plugins.firechat.android.library.compose)
    alias(libs.plugins.firechat.android.library.jacoco)
}

android {
    namespace = "com.trodar.chat"
}

dependencies {

    implementation(projects.core.ui)
    implementation(projects.core.utils)
    implementation(projects.core.media)
    implementation(projects.features.settings)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}