plugins {
    id("com.gradle.enterprise") version "3.16.1"
    id("org.danilopianini.gradle-pre-commit-git-hooks") version "1.1.17"
}

include("kafka-tests")
include("pulsar-tests")
include("rabbitmq-tests")

rootProject.name = "ds-project-acampora-accursi-ay22"

gradleEnterprise {
    buildScan {
        termsOfServiceUrl = "https://gradle.com/terms-of-service"
        termsOfServiceAgree = "yes"
        publishOnFailure()
    }
}

gitHooks {
    preCommit {
        tasks("detekt")
        tasks("ktlintCheck")
    }

    commitMsg {
        conventionalCommits()
    }
    createHooks(overwriteExisting = true)
}
