package garstka.jakub.allegro_repo.api.v1

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)
data class GithubRepoDTO(@JsonProperty("name") val name: String)