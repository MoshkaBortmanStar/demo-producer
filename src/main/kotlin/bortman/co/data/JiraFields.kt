package bortman.co.data

import com.fasterxml.jackson.annotation.JsonProperty

data class JiraFields(
    val creator: JiraCreator?,
    val project: JiraProject?,
    @JsonProperty("customfield_36474")
    val documentationLink: String?,
    @JsonProperty("customfield_61271")
    val changeDescription: String?
)
