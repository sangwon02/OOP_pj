// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
    id("com.google.gms.google-services") version "4.4.2" apply false
    id("androidx.navigation.safeargs") version "2.7.3" apply false // 여기서 Safe Args 플러그인 버전 설정
}

buildscript {
    dependencies {
        classpath("com.google.gms:google-services:4.4.2")
    }
}
