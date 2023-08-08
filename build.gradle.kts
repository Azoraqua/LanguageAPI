import java.io.FilenameFilter

plugins {
    id("com.github.johnrengelman.shadow") version "8.1.1"
    kotlin("jvm") version "1.9.0"

    `java`
    `signing`
    `maven-publish`
}

group = "com.azoraqua"
version = "1.0.0"

repositories {
    mavenCentral()
}

dependencies {
    implementation("com.google.code.gson", "gson", "2.10.1")
    implementation("com.google.guava", "guava", "32.1.2-jre")
    testImplementation(kotlin("test"))
}

java {
    withJavadocJar()
    withSourcesJar()
}

tasks.test {
    useJUnitPlatform()
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
    relocate("com.google.guava", "libs.guava")
}

tasks.publish {
    dependsOn("build")
}

publishing {
    publications.create<MavenPublication>("mavenJava") {
        groupId = rootProject.group.toString()
        artifactId = rootProject.name
        version = rootProject.version.toString()

        pom {
            name.set("LanguageAPI")
            description.set("A simple library for introducing multiple languages into projects.")
            url.set("https://github.com/Azoraqua/LanguageAPI")

            licenses {
                license {
                    name.set("MIT License")
                    url.set("https://github.com/Azoraqua/LanguageAPI/blob/main/LICENSE.md")
                }
            }

            developers {
                developer {
                    id.set("Azoraqua")
                    name.set("Ronald Bunk")
                    email.set("info@azoraqua.com")
                }
            }

            scm {
                url.set("https://github.com/Azoraqua/LanguageAPI")
                connection.set("scm:git:git://github.com/Azoraqua/LanguageAPI.git")
                developerConnection.set("scm:git:ssh://github.com/Azoraqua/LanguageAPI.git")
            }
        }

        from(components["java"])
    }
}

signing {
    sign(publishing.publications["mavenJava"])
}