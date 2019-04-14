package garstka.jakub.allegro_repo.services

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import garstka.jakub.allegro_repo.api.v1.GithubRepoDTO

object JsonToGithubRepoServiceImpl : JsonToGithubRepoService {

    override fun parseJsonToGithubRepoList(jsonInstance: String): List<GithubRepoDTO> {
        class Token : TypeToken<ArrayList<GithubRepoDTO>>()
        return Gson().fromJson(jsonInstance, Token().type)
    }
}