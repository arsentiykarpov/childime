object Versions {
  const val kotlin = "1.9.20"
  const val lifecycle = "2.6.2"
  const val ktx = "1.12.0"
  const val nav = "2.8.6"
  const val hilt = "2.48"
  const val retrofit = "2.9.0"
  const val compose = "1.6.1"
}

object Dependencies {
  const val kotlinStdLib = "org.jetbrains.kotlin:kotlin-stdlib:${Versions.kotlin}"

  // AndroidX
  const val lifecycleViewModel = "androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.lifecycle}"
  const val androidxViewModelCompose = "androidx.lifecycle:lifecycle-viewmodel-compose:${Versions.lifecycle}"

  const val androidCoreKtx = "androidx.core:core-ktx:${Versions.ktx}"

  const val navRuntime = "androidx.navigation:navigation-runtime-ktx:${Versions.nav}"
  const val navFragment = "androidx.navigation:navigation-fragment-ktx:${Versions.nav}"
  const val navUi = "androidx.navigation:navigation-ui-ktx:${Versions.nav}"
  const val navCompose =  "androidx.navigation:navigation-compose:${Versions.nav}"

  const val androidxUi = "androidx.compose.ui:ui:${Versions.compose}"
  const val androidxMaterial = "androidx.compose.material:material:${Versions.compose}"
  
  // Hilt for Compose
  const val androidxHiltCompose = "androidx.hilt:hilt-navigation-compose:1.2.0"
  // Dependency Injection Hilt
  const val hiltAndroid = "com.google.dagger:hilt-android:${Versions.hilt}"
  const val hiltCompiler = "com.google.dagger:hilt-compiler:${Versions.hilt}"

  // Networking
  const val retrofit = "com.squareup.retrofit2:retrofit:${Versions.retrofit}"
}

object Plugins {
  const val androidApplication = "com.android.application"
  const val androidLibrary = "com.android.library"
  const val kotlinAndroid = "org.jetbrains.kotlin.android"
  const val hilt = "com.google.dagger.hilt.android"
}
