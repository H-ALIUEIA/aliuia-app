plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
}

android {
    namespace = "io.github.h_aliueia"
    compileSdk = 35

    defaultConfig {
        applicationId = "io.github.h_aliueia"
        minSdk = 26
        targetSdk = 35
        versionCode = 2
        versionName = "2.0"

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

    buildFeatures{
        viewBinding = true
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {
    implementation("com.android.support:recyclerview-v7:28.0.0")
    implementation("com.github.bumptech.glide:glide:4.9.0")
    implementation("androidx.core:core-ktx:1.16.0")
    implementation("androidx.navigation:navigation-fragment:2.6.0")
    implementation("androidx.navigation:navigation-ui:2.6.0")
    implementation("com.android.support:cardview-v7:28.0.0")
    implementation("androidx.activity:activity:1.8.0")
    implementation("androidx.gridlayout:gridlayout:1.1.0")
    annotationProcessor("com.github.bumptech.glide:compiler:4.9.0")
    implementation("androidx.appcompat:appcompat:1.7.0")
    implementation("com.google.android.material:material:1.12.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.2.1")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.6.1")
    implementation("androidx.cardview:cardview:1.0.0")
    implementation("androidx.media3:media3-exoplayer:1.5.1")
    implementation("androidx.media3:media3-ui:1.5.1")
    implementation("androidx.media3:media3-exoplayer-hls:1.5.1")
    implementation("com.google.code.gson:gson:2.13.1")
    implementation("org.bitbucket.cowwoc:diff-match-patch:1.2")
    implementation("com.nex3z:flow-layout:1.3.3")
    implementation("net.lingala.zip4j:zip4j:2.11.5")
    implementation("androidx.work:work-runtime:2.11.0")
    implementation("me.relex:circleindicator:2.1.6")
    implementation("org.imaginativeworld.whynotimagecarousel:whynotimagecarousel:2.1.1")
    implementation("androidx.work:work-runtime-ktx:2.11.0")
    implementation("androidx.lifecycle:lifecycle-extensions:2.2.0")
    implementation("com.orhanobut:hawk:2.0.1")
    implementation("com.github.tonyofrancis.Fetch:fetch2:3.4.1")
    implementation("com.github.tashilapathum:PleaseWait:0.6.5")
}
