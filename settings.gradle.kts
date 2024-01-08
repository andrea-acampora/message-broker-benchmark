plugins {
    id("com.gradle.enterprise") version "3.16.1"
}

include("kafka-driver")
include("pulsar-driver")
include("rabbitmq-driver")
include("benchmark")

rootProject.name = "ds-project-acampora-accursi-ay22"

gradleEnterprise {
    buildScan {
        termsOfServiceUrl = "https://gradle.com/terms-of-service"
        termsOfServiceAgree = "yes"
        publishOnFailure()
    }
}
