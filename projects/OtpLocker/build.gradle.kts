plugins {
	java
	id("org.springframework.boot") version "3.5.3"
	id("io.spring.dependency-management") version "1.1.7"
}

group = "com.msnider"
version = "0.0.1-SNAPSHOT"

java {
	toolchain {
		languageVersion = JavaLanguageVersion.of(24)
	}
}

repositories {
	mavenCentral()
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-actuator")
	implementation("org.springframework.boot:spring-boot-starter-data-redis")
	implementation("org.springframework.boot:spring-boot-starter-webflux")
	
	testRuntimeOnly("org.junit.platform:junit-platform-launcher")

	// Test dependencies
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("org.mockito:mockito-core")
	testImplementation("org.mockito:mockito-junit-jupiter")
	testImplementation("org.junit.jupiter:junit-jupiter")
	
	// For MockMvc
	testImplementation("org.springframework:spring-test")
	testImplementation("org.springframework.boot:spring-boot-test-autoconfigure")
}

tasks.withType<Test> {
	useJUnitPlatform()
}
