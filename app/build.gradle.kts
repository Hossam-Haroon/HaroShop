plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    id("com.google.gms.google-services")
    alias(libs.plugins.google.firebase.crashlytics)
    id("com.google.dagger.hilt.android")
    id ("com.google.devtools.ksp")
}

android {
    namespace = "com.example.e_commerceapp"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.e_commerceapp"
        minSdk = 31
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

val room_version = "2.6.1"
dependencies {
    // Firebase (managed by BOM)
    implementation (platform("com.google.firebase:firebase-bom:33.1.2"))
    implementation ("com.google.firebase:firebase-auth")
    implementation ("com.google.firebase:firebase-firestore")
    implementation ("com.google.firebase:firebase-analytics")
    implementation ("com.google.firebase:firebase-crashlytics")
    // hilt
    implementation("com.google.dagger:hilt-android:2.51.1")
    ksp("com.google.dagger:hilt-compiler:2.51.1")
    implementation("androidx.hilt:hilt-navigation-compose:1.2.0")
    implementation(platform("androidx.compose:compose-bom:2024.04.01"))

    //
    implementation("androidx.compose.material3:material3:1.2.1")

// Credentials & Google ID
    implementation(libs.androidx.credentials)
    implementation(libs.androidx.credentials.play.services.auth)
    implementation(libs.googleid)

    //datastore
    implementation("androidx.datastore:datastore-preferences:1.1.1")

    // image search
    implementation ("com.google.mlkit:image-labeling:17.0.7")

// AndroidX + Compose
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)

// Coroutines
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.9.0")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.9.0")

// Lifecycle & Flow
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.7.0")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.7.0")

// Splash screen
    implementation("androidx.core:core-splashscreen:1.0.1")
    //back4app
    implementation("com.github.parse-community.Parse-SDK-Android:parse:4.3.0")
    //glide
    implementation ("com.github.skydoves:landscapist-glide:2.4.0")
    //coil
    implementation("io.coil-kt:coil-compose:2.7.0")

    // For networking
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")

    // stripe for payment methods
    implementation ("com.stripe:stripe-android:20.40.1")

    // --- Room ---
    implementation ("androidx.room:room-runtime:$room_version")
    // Use "ksp" for the compiler
    ksp ("androidx.room:room-compiler:$room_version")
    // Kotlin Coroutines & Flow support
    implementation ("androidx.room:room-ktx:$room_version")

    // --- Optional: Paging 3 support ---
    // (If you decide to use Paging later)
    //implementation(libs.androidx.room.paging)

    //workmanager
    val work_version = "2.9.0"
    implementation("androidx.hilt:hilt-work:1.2.0")
    ksp("androidx.hilt:hilt-compiler:1.2.0")
    implementation("androidx.work:work-runtime-ktx:$work_version")

// Testing
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
    testImplementation ("com.google.truth:truth:1.4.5")
    androidTestImplementation ("com.google.truth:truth:1.4.5")
    testImplementation ("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.7.3")
    androidTestImplementation ("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.7.3")
    testImplementation("app.cash.turbine:turbine:1.0.0")
    androidTestImplementation("app.cash.turbine:turbine:1.0.0")


}