plugins {
    alias(libs.plugins.firechat.android.library)
    alias(libs.plugins.firechat.android.library.jacoco)
    alias(libs.plugins.firechat.hilt)
    id("kotlinx-serialization")
}

android {
    namespace = "com.trodar.firebase"
    buildFeatures {
        buildConfig = true
    }
}

dependencies {

    api(projects.core.model)
    api(projects.core.utils)
    implementation(projects.core.datastore)
    implementation(libs.google.play.services.gcm)


    api(libs.kotlinx.datetime)
    api(platform(libs.firebase.bom))
    api(libs.firebase.auth)

    api(libs.facebook)
    implementation(libs.coil3.kt)
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.okhttp.logging)
    implementation(libs.retrofit.core)
    implementation(libs.retrofit.kotlin.serialization)
    implementation(libs.firebase.database.ktx)
    implementation(libs.firebase.storage.ktx)
}