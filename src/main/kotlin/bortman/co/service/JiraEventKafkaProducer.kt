package bortman.co.service

import bortman.co.data.JiraEvent
import jakarta.enterprise.context.ApplicationScoped
import jakarta.inject.Inject
import org.eclipse.microprofile.reactive.messaging.Channel
import org.eclipse.microprofile.reactive.messaging.Emitter

@ApplicationScoped
class JiraEventKafkaProducer {

    @Inject
    @Channel("jira-events")
    lateinit var emitter: Emitter<JiraEvent>

    fun send(event: JiraEvent) {
        emitter.send(event)
    }
}
