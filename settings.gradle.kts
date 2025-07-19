pluginManagement {
    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
    }
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "Karpov Cloud"

include(":app")
include(":mvi")
include(":interop")
// Include FlorisBoard modules
include(":florisboard")
include(":florisboard:app")
include(":florisboard:android")
include(":florisboard:color")
include(":florisboard:kotlin")
include(":florisboard:native")
include(":florisboard:snygg")

// Map project directories
project(":florisboard").projectDir = file("florisboard")
project(":florisboard:android").projectDir = file("florisboard/app")
project(":florisboard:android").projectDir = file("florisboard/lib/android")
project(":florisboard:color").projectDir = file("florisboard/lib/color")
project(":florisboard:kotlin").projectDir = file("florisboard/lib/kotlin")
project(":florisboard:native").projectDir = file("florisboard/lib/native")
project(":florisboard:snygg").projectDir = file("florisboard/lib/snygg")
project(":interop").projectDir = file("interop")
