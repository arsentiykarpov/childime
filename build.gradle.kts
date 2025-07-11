tasks.register<Delete>("clean") {
    delete(rootProject.buildDir)
}
plugins {
  alias(libs.plugins.agp.application) apply false
  alias(libs.plugins.agp.library) apply false
  alias(libs.plugins.agp.test) apply false
  alias(libs.plugins.kotlin.android) apply false
  alias(libs.plugins.kotlin.jvm) apply false
  alias(libs.plugins.kotlin.serialization) apply false
  alias(libs.plugins.kotlinx.kover) apply false
  alias(libs.plugins.kotlin.plugin.compose)
  alias(libs.plugins.ksp) apply false
  alias(libs.plugins.mikepenz.aboutlibraries) apply false
}
