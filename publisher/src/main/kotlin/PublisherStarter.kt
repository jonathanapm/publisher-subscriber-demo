package com.project

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class PublisherStarter

fun main(args: Array<String>) {
    runApplication<PublisherStarter>(*args)
}
