import java.io.ByteArrayOutputStream

plugins {
  alias(libs.plugins.agp.application)
  alias(libs.plugins.kotlin.android)
  alias(libs.plugins.kotlin.serialization)
  alias(libs.plugins.ksp)
  alias(libs.plugins.kotlin.plugin.compose)
  alias(libs.plugins.mikepenz.aboutlibraries)
}
android {
  namespace = "cloud.karpov"
  compileSdk = 35

  defaultConfig {
    applicationId = "cloud.karpov"
    minSdk = 26
    targetSdk = 34
    versionCode = 1
    versionName = "0.0.1"
    buildConfigField("String", "BUILD_COMMIT_HASH", "\"${getGitCommitHash()}\"")
    buildConfigField("String", "FLADDONS_API_VERSION", "\"v~draft2\"")
    buildConfigField("String", "FLADDONS_STORE_URL", "\"beta.addons.florisboard.org\"")
      buildConfigField("String", "VERSION_NAME", "\"beta.addons.florisboard.org\"")
      buildConfigField("String", "APPLICATION_ID", "\"beta.addons.florisboard.org\"")
      buildConfigField("String", "VERSION_CODE", "\"beta.addons.florisboard.org\"")
  }
  compileOptions {
    sourceCompatibility = JavaVersion.VERSION_17  // Ensure this matches Kotlin
    targetCompatibility = JavaVersion.VERSION_17
  }
  buildFeatures {
    buildConfig = true
    compose = true
  }
    
  composeOptions {
    kotlinCompilerExtensionVersion = "1.5.15"
  }

  kotlinOptions {
    jvmTarget = "17"
      freeCompilerArgs = listOf(
          "-opt-in=kotlin.contracts.ExperimentalContracts",
          "-Xjvm-default=all-compatibility",
          "-Xwhen-guards",
          "-Xskip-metadata-version-check"
      )
  }
}

dependencies {
  implementation(libs.kotlin.stdlib)
  implementation(libs.androidx.lifecycle.viewmodel)
  implementation(libs.androidx.core.ktx)

  // Navigation
  implementation(libs.androidx.navigation.ui)
  implementation(libs.androidx.navigation.fragment)
  implementation(libs.androidx.navigation.runtime)
  implementation(libs.androidx.navigation.compose)

  // Compose
  implementation(platform(libs.androidx.compose.bom))
  implementation(libs.androidx.compose.ui)
  implementation(libs.androidx.lifecycle.viewmodel.compose)

  // Optional Material3 (already covered in BOM, but explicit fallback)
  implementation(libs.androidx.compose.material3)

  // Internal modules
  implementation(project(":mvi"))
  implementation(project(":florisboard:app"))
  implementation(project(":interop"))
  implementation(libs.patrickgold.jetpref.datastore.model)
  implementation(libs.patrickgold.jetpref.material.ui)
  implementation(libs.cache4k)
  implementation(libs.coil.compose)
}

fun getGitCommitHash(short: Boolean = false): String {
    if (!File(".git").exists()) {
        return "null"
    }

    val stdout = ByteArrayOutputStream()
    exec {
        if (short) {
            commandLine("git", "rev-parse", "--short", "HEAD")
        } else {
            commandLine("git", "rev-parse", "HEAD")
        }
        standardOutput = stdout
    }
    return stdout.toString().trim()
}
