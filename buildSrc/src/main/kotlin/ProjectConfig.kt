import org.gradle.api.JavaVersion

object ProjectConfig {

	// App ID
	const val appId = "com.synexo.weatherapp"

	// Versions
	const val minSdkVersion = 26
	const val targetSdkVersion = 34
	const val compileSdkVersion = 34

	const val applicationVersionCode = 1
	const val applicationVersionName = "1.0"

	val sourceCompatibility =  JavaVersion.VERSION_17
	val targetCompatibility = JavaVersion.VERSION_17

	const val jvmTarget = "17"

	const val kotlinCompilerExtensionVersion = "1.5.13"

	// SDK Name space
	const val addCityDomainNameSpace = "com.synexo.weatherapp.addCity.domain"
	const val addCityPresentationNameSpace = "com.synexo.weatherapp.addCity.presentation"
	const val coreDataNameSpace = "com.synexo.weatherapp.core.data"
	const val coreDomainNameSpace = "com.synexo.weatherapp.core.domain"
	const val coreModelNameSpace = "com.synexo.weatherapp.core.model"
	const val coreNetworkNameSpace = "com.synexo.weatherapp.core.network"
	const val coreUiNameSpace = "com.synexo.weatherapp.core.ui"
	const val coreDatabaseNameSpace = "com.synexo.weatherapp.core.database"
	const val coreDatastoreNameSpace = "com.synexo.weatherapp.core.datastore"
	const val futureDailyForecastPresentationNameSpace = "com.synexo.weatherapp.futureDailyForecast.presentation"
	const val futureHourlyForecastPresentationNameSpace = "com.synexo.weatherapp.futureHourlyForecast.presentation"
	const val myLocationsPresentationNameSpace = "com.synexo.weatherapp.myLocations.presentation"
	const val myLocationsDataNameSpace = "com.synexo.weatherapp.myLocations.data"
	const val myLocationsDomainNameSpace = "com.synexo.weatherapp.myLocations.domain"
	const val searchPresentationNameSpace = "com.synexo.weatherapp.search.presentation"
	const val searchDataNameSpace = "com.synexo.weatherapp.search.data"
	const val searchDomainNameSpace = "com.synexo.weatherapp.search.domain"
	const val settingsDataNameSpace = "com.synexo.weatherapp.settings.data"
	const val settingsDomainNameSpace = "com.synexo.weatherapp.settings.domain"
	const val settingsPresentationNameSpace = "com.synexo.weatherapp.settings.presentation"
	const val weatherDataNameSpace = "com.synexo.weatherapp.weather.data"
	const val weatherDomainNameSpace = "com.synexo.weatherapp.weather.domain"
	const val weatherPresentationNameSpace = "com.synexo.weatherapp.weather.presentation"

	// App name space
	const val appNameSpace = "com.synexo.weatherapp"


}