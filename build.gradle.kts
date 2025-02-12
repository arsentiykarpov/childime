plugins {
  kotlin("android") version "1.9.0" apply false
  kotlin("kapt") version "1.9.0" apply false
  id("com.android.library") version "8.7.0" apply false
  id("com.android.application") version "8.7.0" apply false
  id("com.google.dagger.hilt.android") version "2.48" apply false
}

tasks.register<Delete>("clean") {
    delete(rootProject.buildDir)
}
