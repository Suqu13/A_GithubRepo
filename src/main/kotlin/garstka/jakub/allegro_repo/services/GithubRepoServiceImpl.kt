package garstka.jakub.allegro_repo.services

import com.fasterxml.jackson.core.type.TypeReference
import garstka.jakub.allegro_repo.api.v1.GithubRepoDTO
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate
import org.springframework.web.client.exchange

@Service
class GithubRepoServiceImpl(val restTemplate:  RestTemplate = RestTemplate()): GithubRepoService {
    companion object{
        const val TO_GET_NEWEST = "https://api.github.com/orgs/Allegro/repos?sort=pushed&direction=desc"
    }

    override fun getNewestGithubRepo(): GithubRepoDTO {
        val headers = HttpHeaders()
        headers["Accept"] = "application/vnd.github.v3.full+json"
        val entity = HttpEntity("parameters", headers)
        return restTemplate.exchange<List<GithubRepoDTO>>(TO_GET_NEWEST,
                HttpMethod.GET,
                entity,
                object : TypeReference<List<GithubRepoDTO>>() {}.type)!!.body!!.first()
    }


}