plugins {
    alias(libs.plugins.firechat.android.library)
    alias(libs.plugins.firechat.android.library.compose)
    alias(libs.plugins.firechat.android.library.jacoco)
}

android {
    namespace = "com.trodar.designtemplate"
    defaultConfig {
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
}

dependencies {

    api(libs.androidx.compose.foundation)
    api(libs.androidx.compose.foundation.layout)
    api(libs.androidx.compose.material.iconsExtended)
    api(libs.androidx.material3)
    api(libs.androidx.compose.runtime)
    api(libs.androidx.compose.ui.util)

//    implementation(libs.coil.kt.compose)

//    testImplementation(libs.androidx.compose.ui.test)
//    testImplementation(libs.androidx.compose.ui.testManifest)
//
    testImplementation(libs.hilt.android.testing)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}