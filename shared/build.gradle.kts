plugins {
    kotlin("multiplatform")
    id("com.android.library")
    id("com.squareup.sqldelight")
    id("dev.icerock.mobile.multiplatform-resources")
    kotlin("plugin.serialization") version "1.8.22"
    id("com.rickclephas.kmp.nativecoroutines") version "1.0.0-ALPHA-8"
    id("com.google.devtools.ksp") version "1.8.21-1.0.11"
    id("org.jetbrains.compose")
}

kotlin {
    android {
        compilations.all {
            kotlinOptions {
                jvmTarget = "17"
            }
        }
    }

    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach {
        it.binaries.framework {
            baseName = "shared"
            isStatic = true
            export("dev.icerock.moko:resources:0.22.3")
            export("dev.icerock.moko:graphics:0.9.0")
        }
    }

    sourceSets {
        val coroutinesVersion = "1.6.4"
        val ktorVersion = "2.3.1"
        val sqlDelightVersion = "1.5.5"
        val kmmViewModelVersion = "1.0.0-ALPHA-7"
        val lifecycleVersion = "2.5.1"

        all {
            languageSettings.optIn("kotlin.experimental.ExperimentalObjCName")
        }

        val commonMain by getting {
            dependencies {
                // coroutines
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutinesVersion")

                // ktor
                implementation("io.ktor:ktor-client-core:$ktorVersion")
                implementation("io.ktor:ktor-client-content-negotiation:$ktorVersion")
                implementation("io.ktor:ktor-serialization-kotlinx-json:$ktorVersion")

                // sqldqlight
                implementation("com.squareup.sqldelight:runtime:$sqlDelightVersion")
                implementation("com.squareup.sqldelight:coroutines-extensions:$sqlDelightVersion")

                // moko
                api("dev.icerock.moko:resources:0.22.3")
                api("dev.icerock.moko:resources-compose:0.22.3")

                // viewmodel
                api("com.rickclephas.kmm:kmm-viewmodel-core:$kmmViewModelVersion")

                // compose multiplatform
                implementation(compose.runtime)
                implementation(compose.foundation)
                implementation(compose.material)
                @OptIn(org.jetbrains.compose.ExperimentalComposeLibrary::class)
                implementation(compose.components.resources)
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test"))
            }
        }
        val androidMain by getting {
            dependencies {
                // ktor
                implementation("io.ktor:ktor-client-android:$ktorVersion")

                // sqldelight
                implementation("com.squareup.sqldelight:android-driver:$sqlDelightVersion")
            }
        }
        val androidUnitTest by getting
        val iosX64Main by getting
        val iosArm64Main by getting
        val iosSimulatorArm64Main by getting
        val iosMain by creating {
            dependencies {
                // ktor
                implementation("io.ktor:ktor-client-darwin:$ktorVersion")

                // sqldelight
                implementation("com.squareup.sqldelight:native-driver:$sqlDelightVersion")
            }
            dependsOn(commonMain)
            iosX64Main.dependsOn(this)
            iosArm64Main.dependsOn(this)
            iosSimulatorArm64Main.dependsOn(this)
        }
        val iosX64Test by getting
        val iosArm64Test by getting
        val iosSimulatorArm64Test by getting
        val iosTest by creating {
            dependsOn(commonTest)
            iosX64Test.dependsOn(this)
            iosArm64Test.dependsOn(this)
            iosSimulatorArm64Test.dependsOn(this)
        }
    }
}

android {
    namespace = "com.example.zemogachallengekmm"
    compileSdk = 33
    defaultConfig {
        minSdk = 24
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    sourceSets["main"].resources.srcDir("src/commonMain/resources")
}

dependencies {
    implementation("androidx.core:core:1.10.1")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.4.1")
}

sqldelight {
    database("PostDatabase") {
        packageName = "com.example.zemogachallengekmm.database"
        sourceFolders = listOf("sqldelight")
    }
}

multiplatformResources {
    multiplatformResourcesPackage = "com.example.zemogachallengekmm"
    multiplatformResourcesClassName = "SharedResources"
}

tasks.matching { it.name == "kspKotlinIosX64" }.configureEach {
    dependsOn(tasks.getByName("generateMRiosX64Main"))
}
tasks.matching { it.name == "kspKotlinIosArm64" }.configureEach {
    dependsOn(tasks.getByName("generateMRiosArm64Main"))
}
tasks.matching { it.name == "kspKotlinIosSimulatorArm64" }.configureEach {
    dependsOn(tasks.getByName("generateMRiosSimulatorArm64Main"))
}