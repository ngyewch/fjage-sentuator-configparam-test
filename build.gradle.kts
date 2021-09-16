plugins {
    java
    id("ca.cutterslade.analyze") version "1.8.1"
    id("com.asarkar.gradle.build-time-tracker") version "3.0.1"
    id("com.github.ben-manes.versions") version "0.39.0"
}

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}

dependencies {
    testImplementation("com.github.org-arl:fjage:1.9.1")
    testImplementation("com.github.org-arl:fjage-sentuator:1.2.1")
    testImplementation("junit:junit:4.13.2")
    runtimeOnly("org.slf4j:slf4j-jdk14:1.7.32")
}

repositories {
    mavenCentral()
}

tasks.named<Test>("test") {
    testLogging {
        events = mutableSetOf(
            org.gradle.api.tasks.testing.logging.TestLogEvent.FAILED,
            org.gradle.api.tasks.testing.logging.TestLogEvent.PASSED,
            org.gradle.api.tasks.testing.logging.TestLogEvent.SKIPPED,
            org.gradle.api.tasks.testing.logging.TestLogEvent.STANDARD_OUT,
            org.gradle.api.tasks.testing.logging.TestLogEvent.STANDARD_ERROR
        )
        exceptionFormat = org.gradle.api.tasks.testing.logging.TestExceptionFormat.FULL
    }
}

tasks.withType<com.github.benmanes.gradle.versions.updates.DependencyUpdatesTask> {
    gradleReleaseChannel = "current"

    fun isNonStable(version: String): Boolean {
        val stableKeyword = listOf("RELEASE", "FINAL", "GA").any { version.toUpperCase().contains(it) }
        val regex = "^[0-9,.v-]+(-r)?$".toRegex()
        val isStable = stableKeyword || regex.matches(version)
        return isStable.not()
    }

    rejectVersionIf {
        isNonStable(candidate.version) && !isNonStable(currentVersion)
    }
}
