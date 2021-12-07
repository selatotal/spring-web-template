plugins {
    id("io.spring.dependency-management") version "1.0.11.RELEASE"
    id("org.springframework.boot") version "2.6.0"
    id("org.flywaydb.flyway") version "8.0.4"
}

dependencies {
    implementation(project(":contract"))
    implementation("org.springframework.boot:spring-boot-starter-jdbc")
    runtimeOnly("org.postgresql:postgresql:42.3.1")
}
