package bortman.co.data

data class JiraEvent(
    val webhookEvent: String?,
    val issue: JiraIssue?,
    val changelog: JiraChangelog?
)