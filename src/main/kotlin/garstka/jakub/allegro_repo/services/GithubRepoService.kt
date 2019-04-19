package garstka.jakub.allegro_repo.services

import garstka.jakub.allegro_repo.api.v1.GithubRepoDTO

interface GithubRepoService {
    fun getNewestGithubRepo_apiv3(URL: String) : GithubRepoDTO
    fun getNewestGithubRepo_apiv4(token : String, QUERY: String) : GithubRepoDTO

}