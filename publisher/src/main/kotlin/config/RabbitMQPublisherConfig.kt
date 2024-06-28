package com.project.config

import com.fasterxml.jackson.databind.ObjectMapper
import com.rabbitmq.client.AMQP.Queue.Bind
import jakarta.annotation.PostConstruct
import org.springframework.amqp.core.Binding
import org.springframework.amqp.core.Exchange
import org.springframework.amqp.core.ExchangeBuilder
import org.springframework.amqp.core.Queue
import org.springframework.amqp.core.QueueBuilder
import org.springframework.amqp.rabbit.connection.ConnectionFactory
import org.springframework.amqp.rabbit.core.RabbitAdmin
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter
import org.springframework.amqp.support.converter.MessageConverter
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import kotlin.math.truncate

@Configuration
class RabbitMQPublisherConfig(
    private val connectionFactory: ConnectionFactory
) {

    companion object {
        private const val EXCHANGE = "test.v1.send-message"
        private const val FIRST_QUEUE = "test.v1.send-first-message-queue"
        private const val SECOND_QUEUE = "test.v1.send-second-message-queue"
        private const val THIRTY_QUEUE = "test.v1.send-thirty-message-queue"
    }

    @Bean
    fun messageConverter(objectMapper: ObjectMapper): MessageConverter =
        Jackson2JsonMessageConverter(objectMapper)

    @PostConstruct
    fun createRabbitElements() {
        val rabbitAdmin = RabbitAdmin(connectionFactory)
        createExchange(rabbitAdmin)
        createQueue(rabbitAdmin, FIRST_QUEUE)
        createQueue(rabbitAdmin, SECOND_QUEUE)
        createQueue(rabbitAdmin, THIRTY_QUEUE)
    }

    private fun createExchange(rabbitAdmin: RabbitAdmin) {
        ExchangeBuilder
            .fanoutExchange(EXCHANGE)
            .durable(true)
            .build<Exchange>()
            .let(rabbitAdmin::declareExchange)
    }

    private fun createQueue(rabbitAdmin: RabbitAdmin, queue: String) {
        QueueBuilder.durable(queue).build()
            .let(rabbitAdmin::declareQueue)
        Binding(queue, Binding.DestinationType.QUEUE, EXCHANGE, "", null)
            .let(rabbitAdmin::declareBinding)
    }
}
