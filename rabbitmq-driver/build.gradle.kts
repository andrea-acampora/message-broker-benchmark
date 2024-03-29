plugins {
    application
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.kotlin.qa)
}

dependencies {
    implementation(libs.rabbitmq.client)
    implementation(libs.jackson)
    implementation(libs.jackson.data.format)
    implementation(project(":benchmark"))
    testImplementation(libs.testcontainer.rabbitmq)
    testImplementation(libs.bundles.kotlin.testing)
}

kotlin {
    target {
        compilations.all {
            kotlinOptions {
                allWarningsAsErrors = true
            }
        }
    }
}

application {
    mainClass.set("BasicCommunicationKt")
}
