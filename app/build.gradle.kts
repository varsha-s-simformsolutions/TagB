plugins {
    id(Plugins.ANDROID_APPLICATION)
    id(Plugins.KOTLIN_ANDROID)
    id(Plugins.KOTLIN_ANDROID_EXTENSION)
    id(Plugins.KOTLIN_KAPT)
    id(Plugins.DAGGER_HILT)
}

android {
    compileSdkVersion(Versions.COMPILE_SDK_VERSION)
    buildToolsVersion(Versions.BUILD_TOOL_SDK)

    buildFeatures {
        viewBinding = true
        dataBinding = true
    }

    defaultConfig {
        applicationId (App.APPLICATION_ID)
        minSdkVersion(Versions.MIN_SDK_VERSION)
        targetSdkVersion(Versions.TARGET_SDK_VERSION)
        versionCode = App.VERSION_CODE
        versionName = App.VERSION_NAME
        javaCompileOptions.annotationProcessorOptions.arguments["dagger.hilt.disableModulesHaveInstallInCheck"] = "true"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_1_8.toString()
    }
}

dependencies {
    // JetBrain
    implementation (Libs.JET_BRAINS)
    implementation (Libs.CORE_KTX)
    implementation (Libs.APP_COMPACT)

    // Material Design
    implementation (Libs.ANDROID_MATERIAL)

    // Constraint layout
    implementation (Libs.CONSTRAINTLAYOUT)

    // Junit
    testImplementation (Libs.JUNITS)
    androidTestImplementation (Libs.JUNIT_TEST)
    androidTestImplementation (Libs.ESPRESSOS)

    // SSP & SDP
    implementation(Libs.MATERIAL_SDP)
    implementation(Libs.MATERIAL_SSP)

    // Architectural Components
    implementation(Libs.LIFECYCLE_VIEWMODELS)
    implementation(Libs.LIFECYCLE_RUNTIME)

    // Koin
    implementation(Libs.KOINS)
    implementation(Libs.KOIN_VIEWMODELS)
    implementation(Libs.KOIN_CORES)

    // Retrofit
    implementation(Libs.RETROFIT)
    implementation(Libs.SQUARE_OKHTTP)
    implementation(Libs.CODE_GSON)
    implementation(Libs.RETROFIT_COVERTER_GSON)
    implementation(Libs.OKHTTP_LOGGING)

    // Dagger Hilt
    implementation(Libs.HILT)
    kapt(Libs.HILT_COMPILER)

    implementation(Libs.PREFERENCE)

    // Coroutines
    implementation(Libs.COROUTINES_CORE)
    implementation(Libs.COROUTINES_ANDROID)
    testImplementation(Libs.COROUTINES_TEST)
    implementation("com.jakewharton.retrofit:retrofit2-kotlin-coroutines-adapter:0.9.2")
}
