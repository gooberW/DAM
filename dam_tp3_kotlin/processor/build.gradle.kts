plugins {
    kotlin("jvm") version "1.9.23"
    kotlin("kapt")
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
    // AutoService to register the processor automatically
    implementation("com.google.auto.service:auto-service:1.1.1")
    // Required for registering the processor
    kapt("com.google.auto.service:auto-service:1.1.1")
    // KotlinPoet for generating Kotlin code
    implementation("com.squareup:kotlinpoet:1.14.2")
    // Include the annotations module
    implementation(project(":annotations"))

}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(21)
}