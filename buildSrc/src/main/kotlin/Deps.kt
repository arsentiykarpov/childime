object Versions {
  const val kotlin = "1.9.0"
  const val lifecycle = "2.6.2"
  const val ktx = "1.12.0"
  const val nav = "2.8.6"
  const val hilt = "2.48"
  const val retrofit = "2.9.0"
}

object Dependencies {
  const val kotlinStdLib = "org.jetbrains.kotlin:kotlin-stdlib:${Versions.kotlin}"

  // AndroidX
  const val lifecycleViewModel = "androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.lifecycle}"
  const val androidCoreKtx = "androidx.core:core-ktx:${Versions.ktx}"
  const val navRuntime = "androidx.navigation:navigation-runtime-ktx:${Versions.nav}"
  const val navFragment = "androidx.navigation:navigation-fragment-ktx:${Versions.nav}"
  const val navUi = "androidx.navigation:navigation-ui-ktx:${Versions.nav}"

  // Dependency Injection
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
