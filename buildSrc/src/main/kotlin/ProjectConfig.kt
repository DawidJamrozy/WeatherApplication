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

	const val kotlinCompilerExtensionVersion = "1.5.11"

	// SDK Name space
	const val domainNameSpace = "com.synexo.weatherapp.domain"
	const val dataNameSpace ="com.synexo.weatherapp.data"
	const val coreNameSpace = "com.synexo.weatherapp.core"

	// App name space
	const val appNameSpace = "com.synexo.weatherapp"


}