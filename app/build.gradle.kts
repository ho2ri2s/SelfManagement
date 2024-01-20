import dependencies.Dep

plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("com.google.dagger.hilt.android")
    id("com.google.gms.google-services")
    kotlin("kapt")
}

android {
    namespace = "com.ho2ri2s.selfmanagement"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.ho2ri2s.selfmanagement"
        minSdk = 26
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro",
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = Dep.Versions.composeCompiler
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

kapt {
    correctErrorTypes = true
}

dependencies {

    implementation(Dep.Kotlin.kotlin)

    implementation(Dep.AndroidX.material)
    implementation(Dep.AndroidX.core)
    implementation(Dep.AndroidX.appCompat)
    implementation(Dep.AndroidX.constraintLayout)

    implementation(Dep.AndroidX.activityKtx)

    implementation(Dep.AndroidX.LifeCycle.liveData)
    implementation(Dep.AndroidX.LifeCycle.viewModel)
    implementation(Dep.AndroidX.LifeCycle.viewModelCompose)
    implementation(Dep.AndroidX.LifeCycle.lifecycleCompose)
    implementation(Dep.AndroidX.LifeCycle.lifecycleKtx)
    implementation("com.google.firebase:firebase-firestore-ktx:24.10.0")
    kapt(Dep.AndroidX.LifeCycle.processor)

    implementation(Dep.Hilt.core)
    implementation(Dep.Hilt.navigationCompose)
    kapt(Dep.Hilt.compiler)

    implementation(Dep.Arrow.core)
    implementation(Dep.Arrow.fxCoroutines)
    implementation(Dep.Arrow.fxStm)

    // Jetpack Compose
    implementation(platform(Dep.AndroidX.Compose.bom))
    implementation(Dep.AndroidX.Compose.ui)
    implementation(Dep.AndroidX.Compose.material)
    implementation(Dep.AndroidX.Compose.extendMaterialIcon)
    implementation(Dep.AndroidX.Compose.uiTooling)
    implementation(Dep.AndroidX.Compose.activity)
    implementation(Dep.AndroidX.Compose.constraintLayout)
    implementation(Dep.AndroidX.Compose.viewModel)
    implementation(Dep.AndroidX.Compose.navigation)
    implementation(Dep.AndroidX.Compose.coil)
    implementation(Dep.navigationCompose)

    implementation(platform(Dep.Firebase.bom))
    implementation(Dep.Firebase.dynamicLinksKtx)
    implementation(Dep.Firebase.analyticsKtx)
    implementation(Dep.Firebase.auth)
    implementation(Dep.Kotlin.playServices)

    implementation(Dep.Kotlin.serialization)
    implementation(Dep.Network.retrofit)
    implementation(Dep.Network.moshiConverter)
    implementation(Dep.Network.moshi)
    implementation(Dep.Network.okHttp)
    implementation(Dep.Network.okHttpLogging)
    implementation(Dep.Network.okHttpMockServer)
    implementation(Dep.Network.serializationConverter)

    implementation(Dep.window)
    implementation(Dep.timber)
    implementation(Dep.chart)

    // Test
    testImplementation(Dep.Test.junit)
    testImplementation(Dep.Test.runner)
    testImplementation(Dep.Test.rule)
    testImplementation(Dep.Test.coreKtx)
    testImplementation(Dep.Test.liveDataTestingKtx)
    testImplementation(Dep.Test.truth)
    testImplementation(Dep.Test.mockk)
    testImplementation(Dep.Test.coroutinesTest)
    testImplementation(Dep.Test.archCore)
    testImplementation(Dep.Kotlin.reflect)
}

val ktlint by configurations.creating

dependencies {
    ktlint("com.pinterest:ktlint:0.43.1")
}

val ktlintCheck by tasks.registering(JavaExec::class) {
    group = LifecycleBasePlugin.VERIFICATION_GROUP
    description = "Check Kotlin code style"
    classpath = ktlint
    isIgnoreExitValue = true
    mainClass.set("com.pinterest.ktlint.Main")
    // see https://pinterest.github.io/ktlint/install/cli/#command-line-usage for more information
    args(
        "**/src/**/*.kt",
        "**.kts",
        "!**/build/**",
    )
}

tasks.check {
    dependsOn(ktlintCheck)
}

tasks.register<JavaExec>("ktlintFormat") {
    group = LifecycleBasePlugin.VERIFICATION_GROUP
    description = "Check Kotlin code style and format"
    classpath = ktlint
    isIgnoreExitValue = true
    mainClass.set("com.pinterest.ktlint.Main")
    jvmArgs("--add-opens=java.base/java.lang=ALL-UNNAMED")
    // see https://pinterest.github.io/ktlint/install/cli/#command-line-usage for more information
    args(
        "-F",
        "**/src/**/*.kt",
        "**.kts",
        "!**/build/**",
    )
}
