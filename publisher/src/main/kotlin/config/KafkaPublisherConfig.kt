package com.project.config

import org.springframework.amqp.core.Message
import org.springframework.boot.autoconfigure.kafka.KafkaProperties
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.core.KafkaTemplate

@Configuration
class KafkaPublisherConfig(
    private val kafkaProperties: KafkaProperties
) {

//    fun kafkaTemplate(): KafkaTemplate<String, Message<Any>> {
//        return KafkaTemplate(kafkaProducerConfiguration())
//    }

//    private fun kafkaProducerConfiguration(): Any {
//        kafkaProperties.producer
//
//
//    }

}