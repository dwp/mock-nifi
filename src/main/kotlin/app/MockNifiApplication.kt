package app

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class MockNifiApplication

fun main(args: Array<String>) {
	runApplication<MockNifiApplication>(*args)
}
