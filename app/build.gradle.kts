plugins {
    alias(libs.plugins.firechat.android.application)
    alias(libs.plugins.firechat.android.application.compose)
    alias(libs.plugins.firechat.android.application.flavors)
    alias(libs.plugins.firechat.android.application.jacoco)
    alias(libs.plugins.firechat.android.application.firebase)
    alias(libs.plugins.firechat.hilt)
    id("com.google.android.gms.oss-licenses-plugin")
    alias(libs.plugins.baselineprofile)
    alias(libs.plugins.roborazzi)
    alias(libs.plugins.kotlin.serialization)
}

android {
    namespace = "com.trodar.firechat"
    defaultConfig {
        applicationId = "com.trodar.firechat"
        versionCode = 1
        versionName = "0.1.2" // X.Y.Z; X = Major, Y = minor, Z = Patch level

        testInstrumentationRunner = "com.trodar.firechat.core.testing.FireChatTestRunner"
    }

    buildTypes {
        debug {
            applicationIdSuffix = ".debug"
        }
        release {
            isMinifyEnabled = true
            applicationIdSuffix = ""
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"))
            signingConfig = signingConfigs.named("debug").get()

        }
    }

    packaging {
        resources {
            excludes.add("/META-INF/{AL2.0,LGPL2.1}")
        }
    }
    testOptions {
        unitTests {
            isIncludeAndroidResources = true
        }
    }
}

dependencies {
    implementation(projects.features.authentication)
    implementation(projects.features.chat)
    implementation(projects.features.settings)

    implementation(projects.core.ui)
    implementation(projects.core.firebase)
    implementation(projects.core.data)
    implementation(projects.core.domain)
    implementation(projects.core.model)

    implementation(libs.work.runtime.ktx)
    implementation(libs.google.play.services.gcm)

    ksp(libs.hilt.compiler)

    implementation(libs.androidx.hilt.navigation.compose)
    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.common.ktx)
    implementation(libs.firebase.crashlytics)
    implementation(libs.androidx.navigation.compose)


    implementation(libs.androidx.compose.material3.adaptive)
    implementation(libs.androidx.compose.material3.adaptive.layout)
    implementation(libs.androidx.compose.material3.adaptive.navigation)
    implementation(libs.androidx.compose.material3.windowSizeClass)
    implementation(libs.androidx.core.splashscreen)

    implementation(libs.kotlinx.serialization.json)

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}
baselineProfile {
    automaticGenerationDuringBuild = false
    dexLayoutOptimization = true
}
dependencyGuard {
    configuration("prodReleaseRuntimeClasspath")
}