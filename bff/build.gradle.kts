plugins {
	java
	id("org.springframework.boot") version "3.1.5"
	id("io.spring.dependency-management") version "1.1.3"
	id("org.sonarqube") version "4.4.1.3373"
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

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-jdbc")
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("org.springframework.boot:spring-boot-starter-security")
	implementation("org.springframework.security:spring-security-config")
	implementation("org.springframework.boot:spring-boot-starter-validation")
	implementation("org.springframework.boot:spring-boot-starter-cache")
	implementation("org.springframework.boot:spring-boot-starter-data-redis")
	implementation("org.springframework.boot:spring-boot-starter-actuator")
	implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.2.0")
	implementation("org.apache.logging.log4j:log4j-spring-boot:2.20.0")
	implementation("org.apache.httpcomponents:httpclient:4.5.13")
	implementation("org.mapstruct:mapstruct:1.5.5.Final")
	implementation("io.jsonwebtoken:jjwt-api:0.12.3")
	implementation("org.apache.commons:commons-csv:1.10.0")
	implementation("com.github.librepdf:openpdf:2.0.1")

	implementation("org.springframework.cloud:spring-cloud-context:3.1.6")

	compileOnly("org.projectlombok:lombok")
	runtimeOnly("com.h2database:h2")
	runtimeOnly("io.jsonwebtoken:jjwt-impl:0.12.3")
	runtimeOnly("io.jsonwebtoken:jjwt-jackson:0.12.3")
	annotationProcessor("org.projectlombok:lombok")
	annotationProcessor("org.mapstruct:mapstruct-processor:1.5.5.Final")

	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("org.junit.jupiter:junit-jupiter-api:5.10.0")
	testImplementation("org.mockito:mockito-core:5.6.0")
	testImplementation("it.ozimov:embedded-redis:0.7.3")
	testImplementation("org.wiremock:wiremock:3.3.1")
	testImplementation("org.assertj:assertj-core:3.24.2")
}

configurations.implementation {
	exclude("org.springframework.boot", "spring-boot-starter-logging")
	exclude(group = "commons-logging", module = "commons-logging")
}

tasks.withType<Test> {
	useJUnitPlatform()
}

tasks.bootBuildImage {
	builder.set("paketobuildpacks/builder-jammy-base:latest")
}
