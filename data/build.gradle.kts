plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    kotlin("kapt")
}

android {
    namespace = "com.example.data"
    compileSdk = 35

    defaultConfig {
        minSdk = 21

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
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
}

dependencies {
    implementation(project(":domain"))

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.play.services.ads.identifier)
    implementation(libs.androidx.media3.common.ktx)
    implementation(libs.androidx.room.common.jvm)
    implementation(libs.androidx.room.runtime.android)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    // Retrofit
    implementation(libs.retrofit)
    implementation(libs.converter.gson)

    // Coroutines
    implementation(libs.kotlinx.coroutines.core)
    implementation(libs.kotlinx.coroutines.android)

    // (Optional) OkHttp logging interceptor for debugging network calls
    implementation(libs.logging.interceptor)

    // Scalars
    implementation(libs.converter.scalars.v2110)

    implementation(libs.play.services.ads.identifier.v1801)

    implementation(libs.androidx.room.runtime)
    implementation(libs.androidx.room.ktx)
    kapt(libs.androidx.room.compiler)
    implementation(libs.hilt.core) // or libs.hilt.core
    kapt(libs.hilt.compiler)

    implementation(libs.hilt.android)
    kapt(libs.hilt.compiler)

    implementation(libs.androidx.hilt.common)

    // Optional (but common)
    implementation(libs.androidx.hilt.compiler)

    implementation(libs.androidx.datastore.preferences)

}