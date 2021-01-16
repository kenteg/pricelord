import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
	id("org.springframework.boot") version "2.4.1"
	id("io.spring.dependency-management") version "1.0.10.RELEASE"
	kotlin("jvm") version "1.4.21"
	kotlin("plugin.spring") version "1.4.21"
}

group = "ru.pricelord"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_11

repositories {
	mavenCentral()
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("org.springframework.boot:spring-boot-starter-data-mongodb")
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
	implementation("org.jsoup:jsoup:1.13.1")
	implementation("org.springframework.boot:spring-boot-starter-thymeleaf:2.4.1")
	implementation("org.seleniumhq.selenium:selenium-java:3.141.59")
	implementation("io.github.microutils:kotlin-logging:1.12.0")
	implementation("org.telegram:telegrambots:4.8.1")
	implementation("de.flapdoodle.embed:de.flapdoodle.embed.mongo:2.2.0")
	//testImplementation("de.flapdoodle.embed:de.flapdoodle.embed.mongo:2.2.0")

	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("com.github.cybuch:mongobeej:1.0.1")
	testImplementation("com.nhaarman:mockito-kotlin:1.6.0")
}

tasks.withType<KotlinCompile> {
	kotlinOptions {
		freeCompilerArgs = listOf("-Xjsr305=strict")
		jvmTarget = "11"
	}
}

tasks.withType<Test> {
	useJUnitPlatform()
}
