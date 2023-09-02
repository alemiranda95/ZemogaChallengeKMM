# Zemoga Challenge with KMM

![android-1](/images/android-1.png)
![android-2](/images/android-2.png)
![ios-1](/images/ios-1.png)
![ios-2](/images/ios-2.png)

## About
This app is the Zemoga employment selection challenge developed using Kotlin Multiplatform Mobile.

It consists in an app that retrieves and displays posts and their information from a public REST api.

## Proposed Architecture
The architecture used was MVVM with Clean Architecture.

The entire business logic is encapsulated in the shared module, which is used by both platforms.

The corresponding UI for each platform was implemented natively.

![architecture](/images/architecture.png)

## Libraries
- [Coroutine (official)](https://github.com/Kotlin/kotlinx.coroutines) - Support for Kotlin coroutine.
- [KMP-NativeCoroutines](https://github.com/rickclephas/KMP-NativeCoroutines) - Library to use Kotlin Coroutines from Swift code in KMP apps.
- [Ktor](https://github.com/ktorio/ktor) - Framework for quickly creating connected applications in Kotlin with minimal effort.
- [SQLDelight](https://github.com/square/sqldelight) - Generates typesafe Kotlin APIs from SQL.
- [Moko-Resources](https://github.com/icerockdev/moko-resources) - Provides access to the resources on macOS, iOS, Android the JVM and JS/Browser with the support of the default system localization.
- [KMM-ViewModel](https://github.com/rickclephas/KMM-ViewModel) - A library that allows you to share ViewModels between Android and iOS.
- [Compose-Multiplatform](https://github.com/JetBrains/compose-multiplatform) - Is a declarative framework for sharing UIs across multiple platforms with Kotlin. It is based on Jetpack Compose and developed by JetBrains and open-source contributors.

## How to install (MacOS)
- Install Homebrew:
```
/bin/bash -c "$(curl -fsSL https://raw.githubusercontent.com/Homebrew/install/HEAD/install.sh)"
```
- Install doctor:
```
brew install kdoctor
```
- Run kdoctor:
```
kdoctor
```
- Install the missing dependencies, if any.
- Import the project on Android Studio and the iosApp on Xcode.
