package dependencies
/**
 * Created by arifrgilang on 4/14/2021
 */
object Dependencies {

    private const val path = "../commonFiles/gradleScript/"
    const val dependency = "./gradleScript/dependencies.gradle"
    const val common = "${path}common.gradle"

    object Kotlin {
        const val kotlin_stdlib = "org.jetbrains.kotlin:kotlin-stdlib:${Version.kotlin}"
        const val kotlin_script = "org.jetbrains.kotlin:kotlin-script-runtime:${Version.kotlin}"
    }

    object Multidex {
        const val multidex = "com.android.support:multidex:${Version.multidex}"
    }

    object Module {
        const val data = ":data"
        const val domain = ":domain"
        const val presentation = ":presentation"
    }

    object Retrofit2 {
        const val converterGson = "com.squareup.retrofit2:converter-gson:${Version.retrofit}"
        const val retrofit = "com.squareup.retrofit2:retrofit:${Version.retrofit}"
    }

    object Coroutine {
        const val coroutine = "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Version.coroutine}"
    }

    object OkHttp3 {
        const val loggingInterceptor =
            "com.squareup.okhttp3:logging-interceptor:${Version.interceptor}"
        const val okHttp3 = "com.squareup.okhttp3:okhttp:${Version.okhttp}"
    }

    object Hawk {
        const val hawk = "com.orhanobut:hawk:${Version.hawk}"
    }

    object Koin {
        const val koin = "org.koin:koin-androidx-viewmodel:${Version.koin}"
    }

    object Glide {
        const val glide = "com.github.bumptech.glide:glide:${Version.glide}"
        const val glideLegacy = "androidx.legacy:legacy-support-v4:${Version.glideLegacy}"
        const val glideKapt = "com.github.bumptech.glide:compiler:${Version.glideKapt}"
    }

    object Gson {
        const val gson = "com.google.code.gson:gson:${Version.gson}"
    }

    object Navigation {
        const val fragment = "androidx.navigation:navigation-fragment-ktx:${Version.navigation}"
        const val ui = "androidx.navigation:navigation-ui-ktx:${Version.navigation}"
    }

    object AndroidX {
        const val fragment = "androidx.fragment:fragment:${Version.androidx}"
        const val core = "androidx.core:core:${Version.androidx}"
        const val constraintlayout =
            "androidx.constraintlayout:constraintlayout:${Version.constraintLayout}"
        const val materialDesign = "com.google.android.material:material:${Version.materialDesign}"
        const val recyclerview =
            "androidx.recyclerview:recyclerview:${Version.recyclerView}"
        const val appcompat = "androidx.appcompat:appcompat:${Version.appCompat}"
        const val legacySupport = "androidx.legacy:legacy-support-v4:${Version.legacySupport}"
        const val swipeRefresh =
            "androidx.swiperefreshlayout:swiperefreshlayout:${Version.swipeRefresh}"
    }

    object Lifecycle {
        const val viewmodel = "androidx.lifecycle:lifecycle-viewmodel-ktx:${Version.lifecycle}"
        const val livedata = "androidx.lifecycle:lifecycle-livedata-ktx:${Version.lifecycle}"
        const val extension =
            "androidx.lifecycle:lifecycle-extensions:${Version.lifecycleVersion}"
    }

    object Timber {
        const val timber = "com.jakewharton.timber:timber:${Version.timber}"
    }

    object Test {
        const val test_junit = "junit:junit:${Version.junit}"
        const val android_test_espresso_core =
            "androidx.test.espresso:espresso-core:${Version.espresso}"
        const val testing_core_testing = "android.arch.core:core-testing:${Version.coreTesting}"
        const val android_test_rules = "androidx.test:rules:${Version.rules}"
        const val android_test_runner = "androidx.test:runner:${Version.runner}"
        const val mockk = "io.mockk:mockk:${Version.mockk}"
        const val coroutine = "org.jetbrains.kotlinx:kotlinx-coroutines-test:${Version.coroutine}"
        const val assert_j = "org.assertj:assertj-core:${Version.assertJVersion}"
    }
}