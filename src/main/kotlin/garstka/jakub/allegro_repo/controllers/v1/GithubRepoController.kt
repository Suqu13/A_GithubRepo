package garstka.jakub.allegro_repo.controllers.v1

import garstka.jakub.allegro_repo.api.v1.GithubRepoDTO
import garstka.jakub.allegro_repo.services.GithubRepoServiceImpl
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping(GithubRepoController.BASE_URL)
class GithubRepoController(private val repoServiceImpl: GithubRepoServiceImpl) {
    companion object{
        const val TO_GET = "https://api.github.com/orgs/Allegro/repos?sort=updated&direction=desc"
        const val BASE_URL = ""
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    fun getNewestGitRepo() : GithubRepoDTO = GithubRepoDTO(repoServiceImpl.getNewestGithubRepo(TO_GET).name)
}