package bortman.co.data

import com.fasterxml.jackson.annotation.JsonProperty

data class JiraChangelogItem(
    val field: String?,
    @JsonProperty("fieldtype")
    val fieldType: String?,
    val from: String?,
    @JsonProperty("fromString")
    val fromStatus: String?,
    val to: String?,
    @JsonProperty("toString")
    val toStatus: String?
)
