package garstka.jakub.allegro_repo.services

import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.treeToValue
import garstka.jakub.allegro_repo.api.v1.GithubRepoDTO
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate

@Service
class GithubRepoServiceImpl(val restTemplate:  RestTemplate = RestTemplate()): GithubRepoService {

    companion object{
        const val GITHUB_GRAPHQL = "https://api.github.com/graphql"
    }

    override fun getNewestGithubRepo_apiv3(URL: String): GithubRepoDTO {
        return restTemplate.getForObject(URL, Array<GithubRepoDTO>::class.java)!!.first()
    }


    override fun getNewestGithubRepo_apiv4(token: String, QUERY: String): GithubRepoDTO {
        val headers = HttpHeaders()
        headers.setBearerAuth(token)
        val entity = HttpEntity(mapOf(Pair("query", QUERY)), headers)
        val jsonNode = restTemplate.postForObject(GITHUB_GRAPHQL, entity, JsonNode::class.java)
        return jacksonObjectMapper().treeToValue(jsonNode!!.get("data").get("organization").get("repositories").get("edges").get(0).get("node"))
    }
}