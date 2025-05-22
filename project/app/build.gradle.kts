import java.util.Properties
import java.io.FileInputStream

plugins {
    alias(libs.plugins.android.application)
}

// Create a Properties object for local properties
val keystoreProperties = Properties()
// Define the path to your local.properties file
val keystorePropertiesFile = rootProject.file("keystore.properties") // Reference from root

// Check if the local.properties file exists and load it
if (keystorePropertiesFile.exists()) {
    FileInputStream(keystorePropertiesFile).use { input ->
        keystoreProperties.load(input)
    }
} else {
    println("Warning: local.properties not found. Some configurations might be missing.")
}

android {

    namespace = "com.rawrick.flicklist"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.rawrick.flicklist"
        minSdk = 30
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        val myApiKey = keystoreProperties.getProperty("apiKey", "DEFAULT_API_KEY_IF_NOT_FOUND")
        buildConfigField("String", "ApiKey", "\"${myApiKey}\"")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    buildFeatures {
        buildConfig = true
        dataBinding = true
        viewBinding = true
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

dependencies {

    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)

    implementation ("androidx.appcompat:appcompat:1.3.1")
    implementation ("com.google.android.material:material:1.4.0")
    implementation ("androidx.constraintlayout:constraintlayout:2.1.1")
    implementation ("androidx.lifecycle:lifecycle-livedata-ktx:2.3.1")
    implementation ("androidx.lifecycle:lifecycle-viewmodel-ktx:2.3.1")
    implementation ("androidx.navigation:navigation-fragment:2.3.5")
    implementation ("androidx.navigation:navigation-ui:2.3.5")
    implementation ("androidx.preference:preference:1.1.1")
    implementation ("androidx.swiperefreshlayout:swiperefreshlayout:1.1.0")
    implementation ("androidx.legacy:legacy-support-v4:1.0.0")
    implementation ("androidx.room:room-common:2.3.0")
    implementation ("androidx.room:room-runtime:2.3.0")
    testImplementation ("junit:junit:4.13.2")
    androidTestImplementation ("androidx.test.ext:junit:1.1.3")
    androidTestImplementation ("androidx.test.espresso:espresso-core:3.4.0")
    implementation ("com.android.volley:volley:1.2.1")
    implementation ("com.google.code.gson:gson:2.8.7")
    implementation ("com.github.bumptech.glide:glide:4.12.0")
    annotationProcessor ("com.github.bumptech.glide:compiler:4.12.0")
    annotationProcessor ("androidx.room:room-compiler:2.3.0")
}
