package bortman.co.utils

import java.time.Duration
import java.util.Properties
import java.util.UUID
import org.apache.kafka.clients.consumer.ConsumerConfig
import org.apache.kafka.clients.consumer.KafkaConsumer
import org.apache.kafka.common.serialization.StringDeserializer

object KafkaTestUtils {

    fun createConsumer(topic: String, bootstrapServers: String): List<String> {
        val props = Properties().apply {
            put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers)
            put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer::class.java.name)
            put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer::class.java.name)
            put(ConsumerConfig.GROUP_ID_CONFIG, UUID.randomUUID().toString()) // уникальный groupId
            put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest")
        }

        KafkaConsumer<String, String>(props).use { consumer ->
            consumer.subscribe(listOf(topic))
            val records = consumer.poll(Duration.ofSeconds(10))
            return records.map { it.value() }
        }
    }

}