package bortman.co

import io.quarkus.test.common.QuarkusTestResource
import io.quarkus.test.common.QuarkusTestResourceLifecycleManager
import java.util.*
import org.apache.kafka.clients.admin.AdminClient
import org.apache.kafka.clients.admin.NewTopic
import org.testcontainers.kafka.ConfluentKafkaContainer
import org.testcontainers.utility.DockerImageName

@QuarkusTestResource(KafkaTestResource::class)
class KafkaTestResource : QuarkusTestResourceLifecycleManager {
    private lateinit var kafka: ConfluentKafkaContainer

    override fun start(): Map<String, String> {
        val imageName = DockerImageName.parse("confluentinc/cp-kafka:7.5.1")
            .asCompatibleSubstituteFor("confluentinc/cp-kafka")

        kafka = ConfluentKafkaContainer(imageName)


        kafka.start()
        val props = Properties()
        props["bootstrap.servers"] = kafka.bootstrapServers
        AdminClient.create(props).use { adminClient ->
            val topic = NewTopic("jira-events", 1, 1.toShort())
            adminClient.createTopics(listOf(topic)).all().get()

            return mapOf(
                "mp.messaging.outgoing.jira-events.bootstrap.servers" to kafka.bootstrapServers
            )
        }
    }

    override fun stop() {
        kafka.stop()
    }
}
