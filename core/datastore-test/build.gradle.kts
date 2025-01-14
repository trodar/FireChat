plugins {
    alias(libs.plugins.firechat.android.library)
    alias(libs.plugins.firechat.hilt)
}

android {
    namespace = "com.trodar.firechat.core.datastore.test"
}

dependencies {
    implementation(libs.hilt.android.testing)
    implementation(projects.core.datastore)
    implementation(projects.core.utils)
}
