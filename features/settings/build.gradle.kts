plugins {
    alias(libs.plugins.firechat.android.feature)
    alias(libs.plugins.firechat.android.library.compose)
    alias(libs.plugins.firechat.android.library.jacoco)
}

android {
    namespace = "com.trodar.settings"
}

dependencies {

    implementation(projects.core.data)
    implementation(projects.core.domain)

    implementation(libs.androidx.appcompat)
    implementation(libs.google.oss.licenses)
    implementation(libs.firebase.messaging.ktx)
    implementation(libs.zxing.android.embedded)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}