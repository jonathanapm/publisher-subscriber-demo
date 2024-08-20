package com.project.config

import com.fasterxml.jackson.databind.ObjectMapper
import com.rabbitmq.client.AMQP.Queue.Bind
import jakarta.annotation.PostConstruct
import org.springframework.amqp.core.Binding
import org.springframework.amqp.core.BindingBuilder
import org.springframework.amqp.core.Exchange
import org.springframework.amqp.core.ExchangeBuilder
import org.springframework.amqp.core.Queue
import org.springframework.amqp.core.QueueBuilder
import org.springframework.amqp.core.TopicExchange
import org.springframework.amqp.rabbit.connection.ConnectionFactory
import org.springframework.amqp.rabbit.core.RabbitAdmin
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter
import org.springframework.amqp.support.converter.MessageConverter
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import kotlin.math.truncate

@Configuration
class RabbitMQPublisherConfig(
    private val connectionFactory: ConnectionFactory,
) {

    private lateinit var rabbitAdmin: RabbitAdmin

    companion object {
        private const val EXCHANGE = "test.v1.send-message"
        private const val FIRST_QUEUE = "test.v1.send-first-message-queue"
        private const val SECOND_QUEUE = "test.v1.send-second-message-queue"
        private const val THIRTY_QUEUE = "test.v1.send-thirty-message-queue"
        private const val DEAD_LETTER_QUEUE = "test.v1.dead-letter-queue"
        private const val DEAD_LETTER_ROUTING_KEY = "test.v1.dead-letter-routing-key"
        private const val DEAD_LETTER_EXCHANGE = "test.v1.dead-letter-exchange"
    }

    @Bean
    fun messageConverter(objectMapper: ObjectMapper): MessageConverter =
        Jackson2JsonMessageConverter(objectMapper)

    @PostConstruct
    fun createRabbitElements() {
        rabbitAdmin = RabbitAdmin(connectionFactory)
        createDlxExchange()
        createDlxQueue()
        createExchange()
        createDlxBinding()
        createQueue(FIRST_QUEUE)
//        createQueue(SECOND_QUEUE)
//        createQueue(THIRTY_QUEUE)
    }

    private fun createExchange() {
        ExchangeBuilder
            .fanoutExchange(EXCHANGE)
            .durable(true)
            .build<Exchange>()
            .let(rabbitAdmin::declareExchange)
    }

    private fun createDlxExchange() {
        ExchangeBuilder.fanoutExchange(DEAD_LETTER_EXCHANGE).build<Exchange>()
            .let(rabbitAdmin::declareExchange)
    }

    private fun createDlxQueue() {
        rabbitAdmin.declareQueue(Queue(DEAD_LETTER_QUEUE))
    }

    private fun createDlxBinding() {
        rabbitAdmin.declareBinding(
            BindingBuilder.bind(createDlxExchange()).to(createDlxExchange()).with(DEAD_LETTER_ROUTING_KEY)
        )
    }

    private fun createQueue(queue: String) {
        QueueBuilder.durable(queue)
            .withArgument("x-dead-letter-exchange", DEAD_LETTER_QUEUE)
            .withArgument("x-dead-letter-routing-key", DEAD_LETTER_ROUTING_KEY)
            .build().let(rabbitAdmin::declareQueue)
        Binding(queue, Binding.DestinationType.QUEUE, EXCHANGE, "", null)
            .let(rabbitAdmin::declareBinding)
    }
}
