plugins {
    id("java")
    id("io.freefair.lombok") version "8.13.1"
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    implementation("org.postgresql:postgresql:42.6.0")

    // the GOAT ORM
    implementation("org.hibernate.orm:hibernate-core:6.6.13.Final")

    // Hibernate Validator
    implementation("org.hibernate.validator:hibernate-validator:8.0.0.Final")
    implementation("org.glassfish:jakarta.el:3.0.4")

    // Agroal connection pool
    implementation("org.hibernate.orm:hibernate-agroal:6.6.13.Final")
    implementation("io.agroal:agroal-pool:1.17")

    // JPA Metamodel Generator
    annotationProcessor("org.hibernate.orm:hibernate-jpamodelgen:6.6.13.Final")

    // H2 database
    runtimeOnly("com.h2database:h2:2.1.214")
}

tasks.test {
    useJUnitPlatform()
}