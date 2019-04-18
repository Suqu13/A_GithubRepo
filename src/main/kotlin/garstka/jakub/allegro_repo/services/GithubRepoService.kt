package garstka.jakub.allegro_repo.services

import garstka.jakub.allegro_repo.api.v1.GithubRepoDTO

interface GithubRepoService {
    fun getNewestGithubRepo() : GithubRepoDTO
}