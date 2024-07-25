
val kotlin_version: String by project
val logback_version: String by project

plugins {
    kotlin("jvm") version "2.0.0"
    id("io.ktor.plugin") version "2.3.12"
    id("org.jetbrains.kotlin.plugin.serialization") version "2.0.0"
}

group = "example.com"
version = "0.0.1"

application {
    mainClass.set("io.ktor.server.netty.EngineMain")

    val isDevelopment: Boolean = project.ext.has("development")
    applicationDefaultJvmArgs = listOf("-Dio.ktor.development=$isDevelopment")
}

repositories {
    mavenCentral()
    maven("https://repo.hypixel.net/repository/Hypixel/")
}

dependencies {
    implementation("io.ktor:ktor-server-content-negotiation-jvm")
    implementation("io.ktor:ktor-server-core-jvm")
    implementation("io.ktor:ktor-serialization-kotlinx-json-jvm")
    implementation("io.ktor:ktor-server-host-common-jvm")
    implementation("io.ktor:ktor-server-netty-jvm")
    implementation("ch.qos.logback:logback-classic:$logback_version")
    implementation("io.ktor:ktor-server-config-yaml")
    implementation("com.google.guava:guava:33.2.1-jre")
    implementation("net.hypixel:hypixel-api-transport-reactor:4.4")
    implementation("com.konghq:unirest-java:3.11.09:standalone")
    implementation("commons-io:commons-io:2.16.1")
    implementation("io.projectreactor:reactor-core:3.6.5")
    implementation("io.projectreactor.netty:reactor-netty-core:1.1.18")
    implementation("io.projectreactor.netty:reactor-netty-http:1.1.18")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.17.+")
    implementation("org.mariadb.jdbc:mariadb-java-client:3.3.3")
    //Ktor Client
    implementation("io.ktor:ktor-client-core-jvm")
    implementation("io.ktor:ktor-client-cio-jvm")
    implementation("io.ktor:ktor-client-logging-jvm")
    implementation("io.ktor:ktor-client-content-negotiation-jvm")

    testImplementation("io.ktor:ktor-server-tests-jvm")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit:$kotlin_version")
}
