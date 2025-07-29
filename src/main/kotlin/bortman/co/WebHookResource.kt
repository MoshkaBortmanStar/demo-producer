package bortman.co

import bortman.co.data.JiraEvent
import bortman.co.service.JiraEventKafkaProducer
import jakarta.inject.Inject
import jakarta.ws.rs.*
import jakarta.ws.rs.core.MediaType

@Path("/docs/webhook")
class WebHookResource @Inject constructor(
    private val kafkaProducer: JiraEventKafkaProducer
) {

    @POST
    @Path("/event")
    @Consumes(MediaType.APPLICATION_JSON)
    fun webHookDocsEvent(jiraEvent: JiraEvent) {
        kafkaProducer.send(jiraEvent)
    }
}
