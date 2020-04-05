import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
	id("org.springframework.boot") version "2.2.6.RELEASE"
	id("io.spring.dependency-management") version "1.0.9.RELEASE"
	kotlin("jvm") version "1.3.71"
	kotlin("plugin.spring") version "1.3.71"
}

group = "com.example"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_11

repositories {
	mavenCentral()
}

dependencies {
	implementation("org.apache.camel.springboot:camel-spring-boot-starter:3.1.0")
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
	implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.3.4")

	runtimeOnly("com.h2database:h2")
	testImplementation("org.springframework.boot:spring-boot-starter-test") {
		exclude(group = "org.junit.vintage", module = "junit-vintage-engine")
	}

	implementation("org.apache.camel:camel-test:3.1.0")
	implementation("org.apache.camel.springboot:camel-aws-sqs-starter:3.1.0")
	implementation("org.apache.camel:camel-aws-sqs")

	implementation("org.apache.camel.springboot:camel-aws-ddb-starter:3.1.0")
	implementation("org.apache.camel:camel-aws-ddb")

	implementation("org.apache.camel:camel-jackson:3.1.0")
	implementation("org.apache.camel:camel-jsonpath:3.1.0")

	testImplementation("io.kotest:kotest-runner-junit5-jvm:4.0.2")
	testImplementation("io.kotest:kotest-extensions-spring:4.0.2")
	testImplementation("io.kotest:kotest-assertions-core-jvm:4.0.2")
}

tasks.withType<Test> {
	useJUnitPlatform()
}

tasks.withType<KotlinCompile> {
	kotlinOptions {
		freeCompilerArgs = listOf("-Xjsr305=strict")
		jvmTarget = "1.8"
	}
}
