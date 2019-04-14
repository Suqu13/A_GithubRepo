package garstka.jakub.allegro_repo.services

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import garstka.jakub.allegro_repo.api.v1.GithubRepo

object JsonToGithubRepoServiceImpl : JsonToGithubRepoService {

    override fun parseJsonToGithubRepoList(jsonInstance: String): List<GithubRepo> {
        class Token : TypeToken<ArrayList<GithubRepo>>()
        return Gson().fromJson(jsonInstance, Token().type)
    }
}