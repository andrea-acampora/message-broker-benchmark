plugins {
    application
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.kotlin.qa)
}

dependencies {
    implementation(libs.rabbitmq.client)
    implementation(libs.jackson)
    implementation(libs.jackson.data.format)
    implementation(project(":kafka-driver"))
    implementation(project(":pulsar-driver"))
    implementation(project(":rabbitmq-driver"))
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

allprojects {

    repositories {
        mavenCentral()
    }

    tasks.withType<Test> {
        useJUnitPlatform()
        testLogging {
            showStandardStreams = true
            showCauses = true
            showStackTraces = true
            events(*org.gradle.api.tasks.testing.logging.TestLogEvent.values())
            exceptionFormat = org.gradle.api.tasks.testing.logging.TestExceptionFormat.FULL
        }
    }
}

tasks.create<JavaExec>("runLatencyBenchmark") {
    this.group = "benchmark-tests"
    this.description = "Run the latency benchmark"
    this.mainClass.set("RunLatencyBenchmarkKt")
    this.classpath = project.sourceSets.main.get().runtimeClasspath
}

tasks.create<JavaExec>("runThroughputBenchmark") {
    this.group = "benchmark-tests"
    this.description = "Run the throughput benchmark"
    this.mainClass.set("RunThroughputBenchmarkKt")
    this.classpath = project.sourceSets.main.get().runtimeClasspath
}

tasks.create<JavaExec>("runNodeFailureBenchmark") {
    this.group = "benchmark-tests"
    this.description = "Run the node failure benchmark"
    this.mainClass.set("RunNodeFailureBenchmarkKt")
    this.classpath = project.sourceSets.main.get().runtimeClasspath
}
