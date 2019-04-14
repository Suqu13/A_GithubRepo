package garstka.jakub.allegro_repo.services

import garstka.jakub.allegro_repo.api.v1.GithubRepo

interface JsonToGithubRepoService {
     fun parseJsonToGithubRepoList(jsonInstance: String) : List<GithubRepo>
}