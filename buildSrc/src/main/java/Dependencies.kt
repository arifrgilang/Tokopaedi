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

    object Moshi {
        const val moshi = "com.squareup.moshi:moshi:${Version.moshi}"
        const val kaptMoshi = "com.squareup.moshi:moshi-kotlin-codegen:${Version.moshi}"
    }

    object Room {
        const val runtime = "androidx.room:room-runtime:${Version.room}"
        const val compiler = "androidx.room:room-compiler:${Version.room}"
        const val ktxCoroutines = "androidx.room:room-ktx:${Version.room}"
        const val roomCoroutines = "androidx.room:room-coroutines:${Version.room}"
    }

    object Retrofit2 {
        const val converterGson = "com.squareup.retrofit2:converter-gson:${Version.retrofit}"
        const val retrofit = "com.squareup.retrofit2:retrofit:${Version.retrofit}"
    }

    object Firebase {
        const val firebaseUI = "com.firebaseui:firebase-ui-auth:${Version.firebaseUI}"
        const val bom = "com.google.firebase:firebase-bom:${Version.bom}"
        const val analytics = "com.google.firebase:firebase-analytics-ktx"
        const val tagManager = "com.google.android.gms:play-services-tagmanager:${Version.tagManager}"
    }

    object Ui {
        const val circularImage = "com.mikhaellopez:circularimageview:${Version.circularImage}"
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
        const val androidx_test_core = "androidx.test:core:${Version.androidXTestCore}"
        const val test_junit = "junit:junit:${Version.junit}"
        const val android_test_espresso_core =
            "androidx.test.espresso:espresso-core:${Version.espresso}"
        const val testing_core_testing = "androidx.arch.core:core-testing:${Version.coreTesting}"
        const val android_test_rules = "androidx.test:rules:${Version.rules}"
        const val android_test_runner = "androidx.test:runner:${Version.runner}"
        const val mockk = "io.mockk:mockk:${Version.mockk}"
        const val coroutine = "org.jetbrains.kotlinx:kotlinx-coroutines-test:${Version.coroutine}"
        const val assert_j = "org.assertj:assertj-core:${Version.assertJVersion}"
        const val androidx_junit = "androidx.test.ext:junit-ktx:${Version.androidXJUnit}"
        const val junit_engine = "org.junit.jupiter:junit-jupiter-engine:${Version.junitEngine}"
        const val robolectric = "org.robolectric:robolectric:${Version.robolectric}"
    }
}