package garstka.jakub.allegro_repo.api.v1

import com.fasterxml.jackson.annotation.JsonIgnoreProperties

@JsonIgnoreProperties(ignoreUnknown = true)
data class GithubRepoDTO(var name: String) {
}