package garstka.jakub.allegro_repo.services



import garstka.jakub.allegro_repo.tools.QueryServiceImpl
import garstka.jakub.allegro_repo.tools.UrlServiceImpl
import org.junit.Assert.*
import org.junit.Test

class QueryUrlServicesImplTest {

    companion object {
        private const val EXAMPLE_LOGIN = "example_login"
        private const val EXAMPLE_FIELD = "example_field"
        private const val EXAMPLE_DIRECTION = "example_direction"
    }

    @Test
    fun createQueryTest() {
        val toVerify =  "query {organization(login:\"$EXAMPLE_LOGIN\") {repositories(first: 1, orderBy: " +
                "{field: $EXAMPLE_FIELD, direction: $EXAMPLE_DIRECTION}) {edges{node{name}}}}}"
        assertEquals(toVerify, QueryServiceImpl.createQuery(EXAMPLE_LOGIN, EXAMPLE_FIELD, EXAMPLE_DIRECTION))
    }

    @Test
    fun createUrlTest() {
        val toVerify = "https://api.github.com/orgs/example_login/repos?sort=example_field&direction=example_direction"
        assertEquals(toVerify, UrlServiceImpl.createUrl(EXAMPLE_LOGIN, EXAMPLE_FIELD, EXAMPLE_DIRECTION))
    }
}
