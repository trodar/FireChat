plugins {
    alias(libs.plugins.firechat.android.library)
    alias(libs.plugins.firechat.android.library.jacoco)
    alias(libs.plugins.firechat.hilt)
}

android {
    namespace = "com.trodar.domain"
    testOptions {
        unitTests {
            isIncludeAndroidResources = true
            isReturnDefaultValues = true
        }
    }
}

dependencies {

    api(projects.core.firebase)
    api(projects.core.data)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}