package bortman.co

import bortman.co.data.*
import bortman.co.utils.KafkaTestUtils
import io.quarkus.test.junit.QuarkusTest
import io.restassured.RestAssured.given
import jakarta.inject.Inject
import jakarta.ws.rs.core.MediaType
import org.junit.jupiter.api.Test
import org.eclipse.microprofile.config.Config
import org.assertj.core.api.Assertions.assertThat



@QuarkusTest
@io.quarkus.test.common.QuarkusTestResource(KafkaTestResource::class, restrictToAnnotatedClass = true)
class WebHookResourceTest {

    @Inject
    lateinit var config: Config

    @Test
    fun testWebHookDocsEvent() {
        val jiraEvent = createTestJiraEvent()
        given()
            .contentType(MediaType.APPLICATION_JSON)
            .body(jiraEvent)
            .`when`().post("/docs/webhook/event")
            .then()
            .statusCode(204)

        val bootstrap = kafkaBootstrapServers()
        val messages = KafkaTestUtils.createConsumer("jira-events", bootstrap)

        assertThat(messages).isNotEmpty()
        assertThat(messages.first()).contains("Test description")
    }

    private fun createTestJiraEvent(): JiraEvent {

        val jiraFields = createJiraFields()
        val jiraIssue = JiraIssue(key = null, self = null, fields = jiraFields)
        return JiraEvent(webhookEvent = null, issue = jiraIssue, changelog = null)
    }

    private fun createJiraFields(jiraProjectKey: String = "Test description", changeDescription: String = "Test change description"): JiraFields {
        return JiraFields(
            creator = JiraCreator(displayName = "Test Creator"),
            project = JiraProject(jiraProjectKey),
            documentationLink = "http://test.link",
            changeDescription = changeDescription
        )
    }

    private fun kafkaBootstrapServers() =
        config.getValue("mp.messaging.outgoing.jira-events.bootstrap.servers", String::class.java)
}
