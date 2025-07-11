@Suppress(
        "DSL_SCOPE_VIOLATION"
) 
plugins {
  alias(libs.plugins.agp.library)
  alias(libs.plugins.kotlin.android)
  alias(libs.plugins.kotlin.serialization)
  alias(libs.plugins.ksp)
  alias(libs.plugins.mikepenz.aboutlibraries)
}

android {
  namespace = "cloud.karpov.mvi"
  compileSdk = 35

  defaultConfig {
    minSdk = 26

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
  implementation(libs.kotlin.stdlib)
  implementation(libs.androidx.lifecycle.viewmodel.compose)
  implementation(libs.androidx.core.ktx)
}
