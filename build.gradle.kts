import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("com.github.johnrengelman.shadow") version "8.1.1"
    kotlin("jvm") version "1.9.0"

    `maven-publish`
}

group = "com.azoraqua"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("com.google.code.gson", "gson", "2.10.1")
}

tasks.compileJava {
    options.release.set(11)
}

tasks.compileKotlin {
    kotlinOptions.jvmTarget = "11"
}

tasks.build {
    dependsOn("shadowJar")
}

tasks.shadowJar {
    minimize()
    relocate("com.google.code.gson", "libs.gson")
}

publishing {
    repositories {
        maven {
            name = "GitHubPackages"
            url = uri("https://maven.pkg.github.com/Azoraqua/LanguageAPI")
            credentials {
                username = project.findProperty("gpr.user") as String? ?: System.getenv("USERNAME")
                password = project.findProperty("gpr.key") as String? ?: System.getenv("TOKEN")
            }
        }
    }
    publications {
        register<MavenPublication>("gpr") {
            from(components["java"])
        }
    }
}