package com.example.camelsample

import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication


@SpringBootApplication
class CamelSampleApplication : CommandLineRunner {
	override fun run(vararg args: String?) {
		runBlocking {
			while (true) {
				delay(1000)
			}
		}
	}
}

fun main(args: Array<String>) {
	run {
		runApplication<CamelSampleApplication>(*args)
	}
}
