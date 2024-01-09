plugins {
    application
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.kotlin.qa)
}

dependencies {
    implementation(libs.kafka.client)
    implementation(libs.jackson)
    implementation(libs.jackson.data.format)
    implementation(libs.kotlin.coroutines.core)
    implementation(project(":benchmark"))
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
