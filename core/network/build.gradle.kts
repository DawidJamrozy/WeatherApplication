plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.jetbrains.kotlin.android)
    id("org.jetbrains.kotlin.kapt")
    id("org.jetbrains.kotlin.plugin.serialization") version "1.5.21"
}

android {
    namespace = ProjectConfig.coreNetworkNameSpace
    compileSdk = ProjectConfig.compileSdkVersion

    defaultConfig {
        minSdk = ProjectConfig.minSdkVersion

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
        buildConfigField("String", "GOOGLE_API_URL", "\"https://maps.googleapis.com/maps/api/geocode/\"")
        buildConfigField("String", "WEATHER_API_URL", "\"https://api.openweathermap.org/data/3.0/\"")
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
        sourceCompatibility = ProjectConfig.sourceCompatibility
        targetCompatibility = ProjectConfig.targetCompatibility
    }

    kotlinOptions {
        jvmTarget = ProjectConfig.jvmTarget
    }

    buildFeatures {
        buildConfig = true
    }

    packaging {
        resources {
            excludes += "/META-INF/LICENSE.md"
            excludes += "/META-INF/LICENSE-notice.md"
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}
dependencies {

    // Networking
    implementation(libs.retrofit)
    implementation(libs.retrofit.serialization)
    implementation(libs.okhttp)
    implementation(libs.okhttp.logging)

    // Project modules
    implementation(project(":core:model"))

    // Serialization
    implementation(libs.serialization)

    // DI
    implementation(libs.di.hilt.android)
    kapt(libs.di.hilt.compiler)

    // Test
    testImplementation(libs.junit)

    // Android Test
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    androidTestImplementation(libs.mockk)

}