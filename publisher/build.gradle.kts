plugins {
    kotlin("jvm")
}

group = "com.project"
version = "0.0.1-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))

    // Spring
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-amqp")
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(17)
}