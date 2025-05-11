plugins {
    kotlin("jvm") version "2.1.20"
}

group = "me.soshin"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.10.2")
}


kotlin {
    jvmToolchain(19)
}