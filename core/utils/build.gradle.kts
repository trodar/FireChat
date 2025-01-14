plugins {
    alias(libs.plugins.firechat.android.library)
    alias(libs.plugins.firechat.android.library.jacoco)
    alias(libs.plugins.firechat.hilt)
}

android {
    namespace = "com.trodar.utils"
    defaultConfig {
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
}

dependencies {

}