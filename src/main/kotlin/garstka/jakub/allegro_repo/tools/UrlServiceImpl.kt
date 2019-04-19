package garstka.jakub.allegro_repo.tools

class UrlServiceImpl : UrlService {

    companion object {
        fun createUrl(login: String, sort: String, direction: String) : String{
            return "https://api.github.com/orgs/$login/repos?sort=$sort&direction=$direction"
        }
    }
}