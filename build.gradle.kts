buildscript {
    repositories {
        google()
        mavenCentral()
    }
}
// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    id("com.android.application") version Build.androidBuildToolsVersion apply false
    id("com.android.library") version Build.androidBuildToolsVersion apply false
    id("org.jetbrains.kotlin.android") version "1.6.10" apply false
    id("org.jetbrains.kotlin.jvm") version Kotlin.version apply false
    id("com.google.dagger.hilt.android") version "2.42" apply false
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}