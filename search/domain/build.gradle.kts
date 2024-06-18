plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.jetbrains.kotlin.android)
    id("org.jetbrains.kotlin.kapt")
    id("org.jetbrains.kotlin.plugin.serialization") version "1.5.21"
}

android {
    namespace = ProjectConfig.searchDomainNameSpace
    compileSdk = ProjectConfig.compileSdkVersion

    defaultConfig {
        minSdk = ProjectConfig.minSdkVersion

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
        sourceCompatibility = ProjectConfig.sourceCompatibility
        targetCompatibility = ProjectConfig.targetCompatibility
    }

    kotlinOptions {
        jvmTarget = ProjectConfig.jvmTarget
    }
}

dependencies {

    // Serialization
    implementation(libs.serialization)

    //
    api(project(":core:domain"))
    api(project(":core:model"))

    // DI
    implementation(libs.di.hilt.android)
    kapt(libs.di.hilt.compiler)
}