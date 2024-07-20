package com.project.controller

import com.project.controller.request.ProductRequest
import com.project.domain.PublisherStrategy
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("publisher")
class PublisherResource(
    private val rabbitTemplate: RabbitTemplate
) {

    companion object {
        private const val EXCHANGE = "test.v1.send-message"
    }

    @PostMapping("/send/{strategy}")
    fun sendMessage(@PathVariable strategy: PublisherStrategy, @RequestBody productRequest: ProductRequest) {
        when (strategy) {
            PublisherStrategy.KAFKA_PUBLISHER -> TODO()
            PublisherStrategy.RABBIT_PUBLISHER -> rabbitTemplate.convertAndSend(EXCHANGE, "", productRequest)
            PublisherStrategy.BOTH -> {
                rabbitTemplate.convertAndSend(EXCHANGE, "", "productRequest")
            }
        }
    }

}