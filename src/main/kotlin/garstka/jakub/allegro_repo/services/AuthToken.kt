package garstka.jakub.allegro_repo.services

import com.fasterxml.jackson.annotation.JsonProperty

data class AuthToken(@JsonProperty(value = "token", required = true) val token: String = "")