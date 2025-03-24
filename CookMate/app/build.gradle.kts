plugins {
    alias(libs.plugins.android.application)
    id("com.google.gms.google-services")
}

android {
    namespace = "com.example.cookmate"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.cookmate"
        minSdk = 23
        targetSdk = 31
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
    implementation(libs.recyclerview)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
    implementation(platform(libs.google.firebase.bom))
    implementation(libs.firebase.analytics)
    implementation (libs.firebase.auth)

    // Firebase Firestore (si vous l'utilisez)
    implementation (libs.firebase.firestore)


    // Firebase BoM (Bill of Materials) pour gérer les versions
    //noinspection GradleDependency
    implementation (libs.recyclerview.v121dpendancerecyclerview)

}