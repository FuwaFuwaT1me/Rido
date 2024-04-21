plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
//    id("com.google.devtools.ksp")
    id("kotlin-kapt")
    id("com.google.dagger.hilt.android")
    id("kotlinx-serialization")
    id("kotlin-parcelize")
}

android {
    namespace = "com.example.impl"
    compileSdk = 34

    defaultConfig {
        minSdk = 24

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
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
}

dependencies {
    implementation(project(":feature_viewer:api"))
    implementation(project(":core_data"))
    implementation(project(":core_domain"))
    implementation(project(":core"))
    implementation(project(":util"))
    implementation(project(":common"))
    implementation(project(":common_compose"))

    implementation("com.google.dagger:hilt-android:2.50")
    kapt("com.google.dagger:hilt-compiler:2.50")
    implementation("androidx.hilt:hilt-navigation-compose:1.1.0")

    debugImplementation("androidx.compose.ui:ui-tooling:1.6.1")

    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.appcompat:appcompat:1.6.1")

    implementation("io.coil-kt:coil-compose:2.5.0")

    implementation("com.google.code.gson:gson:2.10.1")

    implementation("androidx.core:core-ktx:1.13.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.11.0")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
}