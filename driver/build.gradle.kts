plugins {
    id("io.spring.dependency-management") version "1.0.11.RELEASE"
    id("org.springframework.boot") version "2.6.3"
}

dependencies {
    implementation(project(":contract"))
    implementation("org.springframework:spring-web")
}