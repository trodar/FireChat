plugins {
    alias(libs.plugins.firechat.android.library)
    alias(libs.plugins.firechat.android.library.jacoco)
    alias(libs.plugins.firechat.hilt)
    id("kotlinx-serialization")
}

android {
    namespace = "com.trodar.data"

}

dependencies {

    api(projects.core.database)
    api(projects.core.datastore)
    implementation(projects.core.firebase)

    implementation(libs.material)
    implementation(libs.material)
    implementation(libs.material)
    implementation(libs.kotlin.stdlib)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}