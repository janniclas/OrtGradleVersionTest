import org.jetbrains.kotlin.gradle.plugin.mpp.pm20.util.libsDirectory

plugins {
    kotlin("jvm") version "1.9.21"
    application
}

group = "iem.fraunhofer.de"
version = "1.0-SNAPSHOT"

repositories {
    google()
    mavenCentral()

    exclusiveContent {
        forRepository {
            maven("https://androidx.dev/storage/compose-compiler/repository")
        }

        filter {
            includeGroup("androidx.compose.compiler")
        }
    }

    exclusiveContent {
        forRepository {
            maven("https://repo.gradle.org/gradle/libs-releases/")
        }

        filter {
            includeGroup("org.gradle")
        }
    }
}
val ortVersion = "11.0.0"

dependencies {
    implementation("org.apache.logging.log4j:log4j-api-kotlin:1.3.0")
    implementation("org.apache.logging.log4j:log4j-to-slf4j:2.21.1")
    implementation("ch.qos.logback:logback-classic:1.4.11")
    implementation("org.ossreviewtoolkit:analyzer:$ortVersion")
    implementation("org.ossreviewtoolkit:model:$ortVersion")
    implementation("org.ossreviewtoolkit:reporter:$ortVersion")
    implementation("org.ossreviewtoolkit.plugins.packagecurationproviders:package-curation-provider-api:$ortVersion")
    implementation("org.ossreviewtoolkit.plugins.packagemanagers:maven-package-manager:$ortVersion")
    implementation("org.ossreviewtoolkit.plugins.packagemanagers:gradle-package-manager:$ortVersion")
    implementation("org.ossreviewtoolkit.plugins.packagemanagers:nuget-package-manager:$ortVersion")
    implementation("org.ossreviewtoolkit.plugins.packagemanagers:cargo-package-manager:$ortVersion")
    implementation("org.ossreviewtoolkit.plugins.packagemanagers:node-package-manager:$ortVersion")
    implementation("org.ossreviewtoolkit.plugins.packagemanagers:go-package-manager:$ortVersion")
    implementation("org.ossreviewtoolkit.plugins.packagecurationproviders:ort-config-package-curation-provider:$ortVersion")
    implementation("org.ossreviewtoolkit.plugins.packagecurationproviders:clearly-defined-package-curation-provider:$ortVersion")
    implementation("org.ossreviewtoolkit.plugins.packagemanagers:python-package-manager:$ortVersion")
    runtimeOnly("org.ossreviewtoolkit.plugins.packagecurationproviders:file-package-curation-provider:$ortVersion")
    testImplementation("org.jetbrains.kotlin:kotlin-test")
    implementation("com.google.guava:guava:33.0.0-jre")
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(21)
}

application {
    mainClass.set("iem/fraunhofer/de/MainKt")
}
