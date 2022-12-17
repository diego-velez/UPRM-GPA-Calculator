import org.ajoberstar.grgit.Grgit
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.jetbrains.kotlin.utils.addToStdlib.ifFalse

plugins {
    kotlin("jvm") version "1.7.20"
    kotlin("plugin.serialization") version "1.7.20"

    id("org.ajoberstar.grgit") version "5.0.0"
}

group = "org.diego.velez"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.4.1")
    implementation(project(":Tabletery"))
}

tasks.test {
    useJUnitPlatform()
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}

tasks.register("downloadTabletery") {
    doFirst {
        rootDir.listFiles()
            ?.map { it.name }
            ?.contains("Tabletery")
            ?.ifFalse {
                // Only download the library if it has not been downloaded already
                Grgit.clone(
                    mapOf(
                        Pair("dir", rootDir.absolutePath + "/Tabletery"),
                        Pair("uri", "https://github.com/diego-velez/Tabletery")
                    )
                )
            }
    }
}