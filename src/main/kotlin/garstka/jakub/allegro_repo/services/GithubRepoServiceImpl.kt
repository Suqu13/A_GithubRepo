package garstka.jakub.allegro_repo.services

import garstka.jakub.allegro_repo.api.v1.GithubRepoDTO
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate

@Service
class GithubRepoServiceImpl: GithubRepoService {

    override fun getNewestGithubRepo(url: String): GithubRepoDTO {
        val githubRepoResponse = RestTemplate().getForObject(url, String::class.java)
        return JsonToGithubRepoServiceImpl.parseJsonToGithubRepoList(githubRepoResponse!!)[0]
    }
}