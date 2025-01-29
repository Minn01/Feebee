plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)

    // Room Plugin (module)
    id("com.google.devtools.ksp")

    // KTOR Plugin (Serialization)
    id("org.jetbrains.kotlin.plugin.serialization") version "1.9.10"
}

android {
    namespace = "com.example.feebee_android_project_app"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.feebee_android_project_app"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
        compose = true
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)

    // ViewModel Dependency
    implementation(libs.androidx.lifecycle.viewmodel.compose)

    // Room Dependency
    implementation(libs.androidx.room.runtime)
    ksp(libs.androidx.room.compiler)
    implementation(libs.androidx.room.ktx)


    // Navigation Component Dependency
    implementation(libs.androidx.navigation.compose)

    // Adaptive Layout Dependency
    implementation(libs.androidx.adaptive)
    implementation(libs.androidx.adaptive.layout)
    implementation(libs.androidx.adaptive.navigation)

    // Constraint Layout Dependency
    implementation(libs.androidx.constraintlayout.compose)

    // BottomSheetScaffold, DropDownMenu, FloatingActionButton -> Composable
    implementation(libs.material3)

    // API related Dependencies

    // KTOR Dependencies
    implementation(libs.ktor.client.core)
    implementation(libs.ktor.client.android)
    implementation(libs.ktor.client.logging)
    implementation(libs.ktor.client.content.negotiation)

    // KTOR Serialization
    implementation(libs.ktor.serialization.kotlinx.json)

    // Coil Dependency
    implementation(libs.coil.compose)
    implementation(libs.coil.network.okhttp)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}