pluginManagement {
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "chat-app-frontend"
include(":app")
include(":core")
include(":core:designsystem")
include(":feature")
include(":feature:auth")
include(":core:data")
include(":core:network")
include(":core:model")
include(":core:domain")
include(":feature:messages")
include(":core:common")
include(":core:security")
