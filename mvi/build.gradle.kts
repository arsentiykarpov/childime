@Suppress(
        "DSL_SCOPE_VIOLATION"
) 
plugins {
  kotlin("android")
  kotlin("kapt")
  id("com.android.library")
  id("com.google.dagger.hilt.android")
}

android {
  namespace = "cloud.karpov.mvi"
  compileSdk = 35

  defaultConfig {
    minSdk = 21

  }

  buildFeatures {
    compose = false
    aidl = false
    buildConfig = false
    renderScript = false
    shaders = false
  }

  compileOptions {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
  }

  kotlinOptions { jvmTarget = "17" }
}

dependencies {
  implementation(Dependencies.kotlinStdLib)
  implementation(Dependencies.lifecycleViewModel)
  implementation(Dependencies.androidCoreKtx)
  implementation(Dependencies.hiltAndroid)
  implementation("androidx.compose.material3:material3:1.3.1")
  kapt(Dependencies.hiltCompiler)
}
