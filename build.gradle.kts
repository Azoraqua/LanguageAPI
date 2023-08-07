plugins {
    id("com.github.johnrengelman.shadow") version "8.1.1"
    kotlin("jvm") version "1.9.0"

    `java`
    `signing`
    `maven-publish`
}

group = "com.azoraqua"
version = "1.3.0-SNAPSHOT"

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
}

signing {
    useGpgCmd()

    sign(configurations.runtimeElements.get())
}

publishing {
    publications.create("gpr", MavenPublication::class) {
        from(components["java"])
    }

    publications.withType<MavenPublication>().forEach {
        with(it.pom) {
            withXml {
                val root = asNode()
                root.appendNode("name", "LanguageAPI")
                root.appendNode("description", "A simple library for introducing multiple languages into projects.")
                root.appendNode("url", "https://github.com/Azoraqua/LanguageAPI")
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
            }
        }
    }
}