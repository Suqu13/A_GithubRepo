package garstka.jakub.allegro_repo.services

import garstka.jakub.allegro_repo.api.v1.GithubRepoDTO

interface JsonToGithubRepoService {
     fun parseJsonToGithubRepoList(jsonInstance: String) : List<GithubRepoDTO>
}