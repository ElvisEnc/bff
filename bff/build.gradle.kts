plugins {
	java
	id("org.springframework.boot") version "3.3.7"
	id("io.spring.dependency-management") version "1.1.6"
	jacoco
}

group = "bg.com.bo"
version = "0.0.1-SNAPSHOT"

java {
	sourceCompatibility = JavaVersion.VERSION_17
}

configurations {
	compileOnly {
		extendsFrom(configurations.annotationProcessor.get())
	}
}

tasks.test {
	finalizedBy(tasks.jacocoTestReport)
}

tasks.jacocoTestReport {
	dependsOn(tasks.test)
	reports {
		xml.required = true
	}
}

repositories {
	mavenCentral()
}

dependencyManagement {
	imports {
		mavenBom("org.springframework.cloud:spring-cloud-dependencies:2023.0.1") // Usa la versión compatible con Spring Boot 3.3.x
	}
}
dependencies {
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("org.springframework.boot:spring-boot-starter-security")
	implementation("org.springframework.security:spring-security-core:6.3.6")
	implementation("org.springframework.boot:spring-boot-starter-validation")
	implementation("org.springframework.boot:spring-boot-starter-cache")
	implementation("org.springframework.boot:spring-boot-starter-data-redis")
	implementation("org.springframework.boot:spring-boot-starter-actuator")
	implementation("org.springframework.boot:spring-boot-starter-log4j2")
	implementation("org.springframework.cloud:spring-cloud-context:4.1.4")
	implementation("org.springframework.security:spring-security-config:6.3.6")
	implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.6.0")
	implementation("com.github.ben-manes.caffeine:caffeine:3.1.8")
	implementation("org.apache.httpcomponents:httpclient:4.5.13")
	implementation("org.apache.commons:commons-csv:1.11.0")
	implementation("org.mapstruct:mapstruct:1.5.5.Final")
	implementation("io.jsonwebtoken:jjwt-api:0.12.3")
	implementation("com.github.librepdf:openpdf:2.0.1")
	implementation("org.springframework.cloud:spring-cloud-starter-openfeign")

	compileOnly("org.projectlombok:lombok:1.18.34")

	annotationProcessor("org.projectlombok:lombok:1.18.34")
	annotationProcessor("org.mapstruct:mapstruct-processor:1.5.5.Final")

	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("org.junit.jupiter:junit-jupiter-api:5.10.0")
	testImplementation("org.mockito:mockito-core:5.6.0")
	testImplementation("it.ozimov:embedded-redis:0.7.3")
	testImplementation("org.wiremock:wiremock-standalone:3.9.1")
	testImplementation("org.assertj:assertj-core:3.24.2")

	testCompileOnly("org.projectlombok:lombok:1.18.34")

	testAnnotationProcessor("org.projectlombok:lombok:1.18.34")

}

configurations.implementation {
	exclude("org.springframework.boot", "spring-boot-starter-logging")
	exclude("commons-logging", "commons-logging")
	exclude("org.springframework.security:spring-security-core:6.3.1")
}

tasks.withType<Test> {
	useJUnitPlatform()
}

tasks.bootBuildImage {
	builder.set("paketobuildpacks/builder-jammy-base:latest")
}
