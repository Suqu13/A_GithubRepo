package garstka.jakub.allegro_repo.tools

class QueryServiceImpl : QueryService {
    companion object {
        fun createQuery(login: String, field: String, direction: String) : String{
            return "query {" +
                        "organization(login:\"$login\") {" +
                            "repositories(first: 1, orderBy: {field: $field, direction: $direction}) {" +
                                "edges{" +
                                    "node{" +
                                        "name" +
                                        "}" +
                                    "}" +
                                "}" +
                            "}" +
                        "}"
        }
    }
}