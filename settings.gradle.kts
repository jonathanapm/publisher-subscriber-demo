plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.5.0"
}
rootProject.name = "publisher-subscriber-demo"
include("publisher")
include("subscriber")
