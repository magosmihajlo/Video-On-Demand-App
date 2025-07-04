plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    id("kotlin-kapt")
    alias(libs.plugins.hilt)
}

val vodApiKey: String = System.getenv("VOD_API_KEY")
    ?: project.property("VOD_API_KEY") as String?
    ?: ""

android {
    namespace = "com.example.vod"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.vod"
        minSdk = 21
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

    }

    flavorDimensions += "env"

    productFlavors {
        create("staging") {
            dimension = "env"
            buildConfigField("String", "VOD_API_KEY", "\"your-staging-api-key\"")
        }
        create("production") {
            dimension = "env"
            buildConfigField("String", "VOD_API_KEY", "\"your-prod-api-key\"")
        }
    }

    buildTypes {
        debug {
            buildConfigField("String", "VOD_API_KEY", "\"$vodApiKey\"")
        }
        release {
            buildConfigField("String", "VOD_API_KEY", "\"$vodApiKey\"")
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        buildConfig = true
    }

    packaging {
        resources {
            excludes += "META-INF/gradle/incremental.annotation.processors"
            excludes += "META-INF/androidx/room/room-compiler-processing/LICENSE.txt"
        }
    }
}

dependencies {

    implementation(project(":domain"))
    implementation(project(":data"))
    api(project(":presentation"))

    implementation(libs.androidx.activity.compose)

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.media3.common.ktx)
    implementation(libs.androidx.navigation.compose)
    implementation(libs.material3.android)
    implementation(libs.androidx.room.runtime.android)

    // HILT
    implementation(libs.hilt.compiler)
    kapt(libs.hilt.compiler)
    implementation (libs.hilt.android)
    implementation(libs.androidx.hilt.navigation.compose)

    // ROOM
    implementation(libs.androidx.room.runtime.vversion)
    kapt(libs.androidx.room.compiler)

}