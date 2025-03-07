plugins {
  kotlin("android")
  kotlin("kapt")
  id("com.android.application")
  id("com.google.dagger.hilt.android")
}
android {
  namespace = "cloud.karpov"
  compileSdk = 34

  defaultConfig {
    applicationId = "cloud.karpov"
    minSdk = 24
    targetSdk = 34
    versionCode = 1
    versionName = "0.0.1"
  }

  compileOptions {
    sourceCompatibility = JavaVersion.VERSION_17  // Ensure this matches Kotlin
    targetCompatibility = JavaVersion.VERSION_17
  }

  buildFeatures {
    compose = true
  }
    
  composeOptions {
    kotlinCompilerExtensionVersion = "1.5.4"
  }

  kotlinOptions {
    jvmTarget = "17"
  }
}

dependencies {
  implementation(Dependencies.kotlinStdLib)
  implementation(Dependencies.lifecycleViewModel)
  implementation(Dependencies.androidCoreKtx)
  implementation(Dependencies.navUi)
  implementation(Dependencies.navFragment)
  implementation(Dependencies.navRuntime)
  implementation(Dependencies.hiltAndroid)
  implementation(Dependencies.navCompose)
  implementation(Dependencies.androidxViewModelCompose)
  implementation(Dependencies.androidxUi)
  implementation(Dependencies.androidxMaterial)
  implementation(Dependencies.androidxHiltCompose)
  implementation("androidx.compose.material3:material3:1.3.1")
  kapt(Dependencies.hiltCompiler)
}
