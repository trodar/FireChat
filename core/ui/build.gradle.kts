plugins {
    alias(libs.plugins.firechat.android.library)
    alias(libs.plugins.firechat.android.library.compose)
    alias(libs.plugins.firechat.hilt)
    alias(libs.plugins.firechat.android.library.jacoco)
}

android {
    namespace = "com.trodar.ui"
    defaultConfig {
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
}

dependencies {

    api(projects.core.designtemplate)
    implementation(projects.core.datastore)
    implementation(projects.core.firebase)

    api(libs.facebook)
    api(libs.facebook.login)
    api(libs.androidx.activity.compose)
    implementation(libs.androidx.credentials)
    implementation(libs.androidx.credentials.auth)
    implementation(libs.googleid)

    api(libs.coil3.kt.compose)
    api(libs.coil3.network)
    api(libs.coil3.kt.svg)
    api(libs.coil3.kt.gif)
}