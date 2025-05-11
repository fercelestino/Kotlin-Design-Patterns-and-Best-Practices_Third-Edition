import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "2.1.20"
    id("com.google.devtools.ksp") version "2.1.20-2.0.1"
    application
}

group = "me.soshin"

repositories {
    mavenCentral()
}

dependencies {
    implementation("io.arrow-kt:arrow-core:2.1.0")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.10.2")
    implementation("io.arrow-kt:arrow-fx-coroutines:2.1.0")
    implementation("io.arrow-kt:arrow-resilience-jvm:2.1.0")
    implementation("io.arrow-kt:arrow-fx-stm-jvm:2.1.0")
    implementation("io.arrow-kt:arrow-optics:2.1.0")
    ksp("io.arrow-kt:arrow-optics-ksp-plugin:2.1.0")
    testImplementation(kotlin("test"))
}

kotlin {
    jvmToolchain(19)
}

tasks.withType<KotlinCompile>().configureEach {
    kotlinOptions {
        freeCompilerArgs = freeCompilerArgs + "-Xcontext-receivers"
    }
}
