package garstka.jakub.allegro_repo.controllers.v1

import garstka.jakub.allegro_repo.services.GithubRepoService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
class GithubRepoController(private val repoService: GithubRepoService) {

    @GetMapping("/", produces = ["application/json"])
    @ResponseStatus(HttpStatus.OK)
    fun getNewestGithubRepo() = repoService.getNewestGithubRepo()

}