package com.project.listener

import mu.KotlinLogging
import org.springframework.amqp.core.Message
import org.springframework.amqp.rabbit.annotation.RabbitListener
import org.springframework.amqp.support.converter.MessageConverter
import org.springframework.stereotype.Service

@Service
class RabbitMQListener(
    private val messageConverter: MessageConverter
) {

    private val logger = KotlinLogging.logger {  }

    companion object {
        private const val FIRST_QUEUE = "test.v1.send-first-message-queue"
        private const val SECOND_QUEUE = "test.v1.send-second-message-queue"
        private const val THIRTY_QUEUE = "test.v1.send-thirty-message-queue"
    }


    @RabbitListener(queues = [FIRST_QUEUE])
    fun receiveMessageFirstQueue(message: Message) {
        messageConverter.fromMessage(message).let {
            logger.info("Receive message: $message from queue $FIRST_QUEUE")
        }
    }

    @RabbitListener(queues = [SECOND_QUEUE])
    fun receiveMessageSecondQueue(message: Message) {
        messageConverter.fromMessage(message).let {
            logger.info("Receive message: $message from queue $SECOND_QUEUE")
        }
    }

    @RabbitListener(queues = [THIRTY_QUEUE])
    fun receiveMessageThirtyQueue(message: Message) {
        messageConverter.fromMessage(message).let {
            logger.info("Receive message: $message from queue $THIRTY_QUEUE")
        }
    }
}