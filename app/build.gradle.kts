import com.android.build.api.dsl.AaptOptions

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlinx.serialization)
}

android {
    namespace = "md.utm.spam"
    compileSdk = 34

    defaultConfig {
        applicationId = "md.utm.spam"
        minSdk = 33
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    androidResources {
        noCompress += "tflite"
    }

    val java = libs.versions.java.get()

    compileOptions {
        sourceCompatibility = JavaVersion.toVersion(java)
        targetCompatibility = JavaVersion.toVersion(java)
    }
    kotlinOptions {
        jvmTarget = java
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = libs.versions.compose.compiler.get()
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    implementation(libs.core.ktx)
    implementation(libs.lifecycle.runtime.compose)
    implementation(libs.lifecycle.viewmodel.compose)
    implementation(libs.activity.compose)
    implementation(libs.splashscreen)
    implementation(platform(libs.compose.bom))
    implementation(libs.ui)
    implementation(libs.ui.graphics)
    implementation(libs.ui.tooling.preview)
    implementation(libs.material3)
    implementation(libs.material.icons)
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.bundles.ktor.client)
    debugImplementation(libs.ui.tooling)
    debugImplementation(libs.ui.test.manifest)

    implementation(libs.tensorflow.lite)

    implementation(libs.tensorflow.lite.task.text)
    implementation(libs.tensorflow.lite.gpu.delegate.plugin)
}