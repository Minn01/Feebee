plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)

    // ksp Plugin for Room (module)
    id("com.google.devtools.ksp")

    // KTOR Plugin (Serialization)
    id("org.jetbrains.kotlin.plugin.serialization") version "1.9.10"

    // kapt and Dagger Hilt Plugin
    id("kotlin-kapt")
    id("com.google.dagger.hilt.android")

    alias(libs.plugins.google.gms.google.services)
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
        isCoreLibraryDesugaringEnabled = true
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

val vicoVersion = "2.0.2"

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)

    // Dagger Hilt Dependency
    implementation(libs.hilt.android.v2511)
    implementation(libs.androidx.hilt.navigation.compose)
    implementation(libs.androidx.storage)
    implementation(libs.androidx.lifecycle.process)
    implementation(libs.firebase.messaging)
    implementation(libs.core.ktx)
    kapt(libs.hilt.android.compiler)

    // Firebase Authentication Dependency
    implementation(libs.firebase.auth)

    // Fire Store Dependency
    implementation(libs.firebase.firestore)

    // Datastore Preferences Dependency
    implementation(libs.androidx.datastore.preferences)

    // ViewModel Dependency
    implementation(libs.androidx.lifecycle.viewmodel.compose)

    // Room Dependency
    implementation(libs.androidx.room.runtime)
    ksp(libs.androidx.room.compiler)
    implementation(libs.androidx.room.ktx)

    // Live Data (used for authState)
    implementation(libs.androidx.runtime.livedata)

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

    // Accompanist Pager
    implementation(libs.accompanist.pager)

    // Local date work around
    coreLibraryDesugaring(libs.desugar.jdk.libs)

    // MPAndroidChart
    implementation(libs.mpandroidchart)
//    implementation(libs.ui)
//    implementation(libs.androidx.foundation)

    // Kotlin Coroutines Test Dependency
    testImplementation(libs.kotlinx.coroutines.test)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}

// Allow references to generated code
kapt {
    correctErrorTypes = true
}
