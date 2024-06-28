package com.project

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class SubscriberStarter

fun main(args: Array<String>) {
    runApplication<SubscriberStarter>(*args)
}

