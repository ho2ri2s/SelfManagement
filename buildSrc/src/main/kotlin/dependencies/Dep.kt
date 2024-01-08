package dependencies

// TODO: tomlに書き換える
object Dep {
  object Versions {
    const val kotlin = "1.9.20"
    const val coroutines = "1.7.3"
    const val serialization = "1.6.2"
    const val timber = "2.2.2"
    const val lifecycle = "2.6.2"
    const val composeCompiler = "1.5.5"
    const val composeBom = "2023.10.01"
    const val activity = "1.3.1"
    const val hilt = "2.48"
    const val androidXTest = "1.4.0"
    const val arrow = "1.0.0"
    const val firebase = "32.7.0"
    const val retrofit = "2.9.0"
    const val okHttp = "4.10.0"
    const val playServices = "4.4.0"
  }

  object Kotlin {
    const val kotlin = "org.jetbrains.kotlin:kotlin-stdlib:${Versions.kotlin}"
    const val reflect = "org.jetbrains.kotlin:kotlin-reflect:${Versions.kotlin}"
    const val serialization =
      "org.jetbrains.kotlinx:kotlinx-serialization-json:${Versions.serialization}"
    const val playServices =
      "org.jetbrains.kotlinx:kotlinx-coroutines-play-services:${Versions.coroutines}"
  }

  object AndroidX {
    const val core = "androidx.core:core-ktx:1.5.0"
    const val appCompat = "androidx.appcompat:appcompat:1.3.0"
    const val material = "com.google.android.material:material:1.3.0"
    const val constraintLayout = "androidx.constraintlayout:constraintlayout:2.0.4"

    const val activityKtx = "androidx.activity:activity-ktx:${Versions.activity}"

    object LifeCycle {
      const val viewModel = "androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.lifecycle}"
      const val viewModelCompose =
        "androidx.lifecycle:lifecycle-viewmodel-compose:${Versions.lifecycle}"
      const val liveData = "androidx.lifecycle:lifecycle-livedata-ktx:${Versions.lifecycle}"
      const val processor = "androidx.lifecycle:lifecycle-compiler:${Versions.lifecycle}"
      const val lifecycleKtx = "androidx.lifecycle:lifecycle-runtime-ktx:${Versions.lifecycle}"
      const val lifecycleCompose =
        "androidx.lifecycle:lifecycle-runtime-compose:${Versions.lifecycle}"
    }

    object Compose {
      const val bom = "androidx.compose:compose-bom:${Versions.composeBom}"
      const val ui = "androidx.compose.ui:ui"
      const val activity = "androidx.activity:activity-compose:${Versions.activity}"
      const val material = "androidx.compose.material:material"
      const val extendMaterialIcon =
        "androidx.compose.material:material-icons-extended"
      const val uiTooling = "androidx.compose.ui:ui-tooling"
      const val constraintLayout =
        "androidx.constraintlayout:constraintlayout-compose:1.0.0-beta02"
      const val viewModel = "androidx.lifecycle:lifecycle-viewmodel-compose:1.0.0-alpha07"
      const val navigation = "androidx.navigation:navigation-compose:2.4.0-alpha08"

      const val coil = "io.coil-kt:coil-compose:1.3.2"
    }
  }

  object Hilt {
    const val core = "com.google.dagger:hilt-android:${Versions.hilt}"
    const val compiler = "com.google.dagger:hilt-android-compiler:${Versions.hilt}"
    const val test = "'com.google.dagger:hilt-android-testing:${Versions.hilt}"
    const val navigationCompose = "androidx.hilt:hilt-navigation-compose:1.1.0"
  }

  object Arrow {
    const val core = "io.arrow-kt:arrow-core:${Versions.arrow}"
    const val fxCoroutines = "io.arrow-kt:arrow-fx-coroutines:${Versions.arrow}"
    const val fxStm = "io.arrow-kt:arrow-fx-stm:${Versions.arrow}"
  }

  object Network {
    const val retrofit = "com.squareup.retrofit2:retrofit:${Versions.retrofit}"
    const val moshiConverter = "com.squareup.retrofit2:converter-moshi:${Versions.retrofit}"
    const val okHttp = "com.squareup.okhttp3:okhttp:${Versions.okHttp}"
    const val okHttpLogging = "com.squareup.okhttp3:logging-interceptor:${Versions.okHttp}"
    const val okHttpMockServer = "com.squareup.okhttp3:mockwebserver:${Versions.okHttp}"
    const val serializationConverter =
      "com.jakewharton.retrofit:retrofit2-kotlinx-serialization-converter:1.0.0"
    const val moshi = "com.squareup.moshi:moshi-kotlin:1.15.0"
  }

  object Firebase {
    const val bom = "com.google.firebase:firebase-bom:${Versions.firebase}"
    const val analyticsKtx = "com.google.firebase:firebase-analytics-ktx"
    const val dynamicLinksKtx = "com.google.firebase:firebase-dynamic-links-ktx"
    const val auth = "com.google.firebase:firebase-auth-ktx"
  }

  object Test {
    const val junit = "junit:junit:4.13.2"
    const val rule = "androidx.test:rules:${Versions.androidXTest}"
    const val runner = "androidx.test:runner:${Versions.androidXTest}"
    const val coreKtx = "androidx.test:core-ktx:${Versions.androidXTest}"
    const val archCore = "androidx.arch.core:core-testing:2.1.0"
    const val liveDataTestingKtx = "com.jraska.livedata:testing-ktx:1.2.0"
    const val coroutinesTest = "org.jetbrains.kotlinx:kotlinx-coroutines-test:1.5.2"

    const val mockk = "io.mockk:mockk:1.12.0"

    const val truth = "com.google.truth:truth:1.1.3"
  }

  const val navigationCompose = "androidx.navigation:navigation-compose:2.7.5"

  const val window = "androidx.window:window:1.0.0-beta03"
  const val timber = "com.jakewharton.timber:timber:${Versions.timber}"
  const val chart = "com.github.tehras:charts:0.2.4-alpha"
}
