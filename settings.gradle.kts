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

rootProject.name = "WeatherApp"
include(":app")
include(":settings:domain")
include(":settings:data")
include(":settings:presentation")
include(":core:database")
include(":core:network")
include(":core:datastore")
include(":core:ui")
include(":core:domain")
include(":core:model")
include(":core:data")
include(":myLocations:presentation")
include(":myLocations:domain")
include(":myLocations:data")
include(":futureHourlyForecast:presentation")
include(":futureDailyForecast:presentation")
include(":weather:presentation")
include(":weather:domain")
include(":weather:data")
include(":addCity:presentation")
include(":addCity:domain")
include(":search:presentation")
include(":search:domain")
include(":search:data")
