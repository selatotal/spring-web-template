import org.springframework.boot.gradle.tasks.bundling.BootBuildImage;

plugins {
	id("org.springframework.boot") version "2.6.3"
	id("io.spring.dependency-management") version "1.0.11.RELEASE"
}

extra["springCloudVersion"] = "2020.0.4"

dependencyManagement {
    imports {
        mavenBom("org.springframework.cloud:spring-cloud-dependencies:${property("springCloudVersion")}")
    }
}

dependencies {
    implementation(project(":shared"))
    implementation(project(":contract"))
	implementation(project(":database"))
	implementation("org.springframework.boot:spring-boot-starter-actuator")
	implementation("org.springframework.boot:spring-boot-starter-jdbc")
	implementation("org.springframework.boot:spring-boot-starter-web"){
		exclude("org.springframework.boot", "spring-boot-starter-tomcat")
	}
	implementation("org.springframework.boot:spring-boot-starter-jetty")
	runtimeOnly("org.postgresql:postgresql:42.3.1")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
}

tasks.withType<BootBuildImage>(){
    imageName="selat/${rootProject.name}:${project.version}"
}
