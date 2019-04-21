package garstka.jakub.allegro_repo.services

import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.ObjectMapper
import garstka.jakub.allegro_repo.api.v1.GithubRepoDTO
import org.junit.Before
import org.junit.Assert.*
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations

import org.springframework.web.client.HttpClientErrorException
import org.springframework.web.client.RestTemplate
import java.lang.NullPointerException


class GithubRepoServiceImplTest {

    companion object {
        private const val TOKEN = "TOKEN"
        private const val QUERY = "QUERY"
    }

    private lateinit var githubRepoService: GithubRepoService

    @Mock
    private lateinit var restTemplate: RestTemplate

    @Throws(Exception::class)

    @Before
    fun setUp()  {
        MockitoAnnotations.initMocks(this)
        githubRepoService = GithubRepoServiceImpl(restTemplate)
    }

    @Test
    fun testGetNewestGitRepo_apiv3() {
        val githubRepoDTOArray = arrayOf(GithubRepoDTO("repo_0"),
                GithubRepoDTO("repo_1"), GithubRepoDTO("repo_2"))
       `when`(restTemplate.getForObject(anyString(), eq(Array<GithubRepoDTO>::class.java))).thenReturn(githubRepoDTOArray)
        assertEquals(githubRepoDTOArray.first(), githubRepoService.getNewestGithubRepo_apiv3(QUERY))
    }

    @Test(expected = NoSuchElementException::class)
    fun testGetNewestGitRepo_apiv3_RepoDoesNotExist() {
        `when`(restTemplate.getForObject(anyString(), eq(Array<GithubRepoDTO>::class.java))).thenReturn(arrayOf())
        githubRepoService.getNewestGithubRepo_apiv3(QUERY)
    }

    @Test(expected = HttpClientErrorException.NotFound::class)
    fun testGetNewestGitRepo_apiv3_OrganizationDoesNotExist() {
        `when`(restTemplate.getForObject(anyString(), eq(Array<GithubRepoDTO>::class.java))).thenThrow(HttpClientErrorException.NotFound::class.java)
        githubRepoService.getNewestGithubRepo_apiv3(QUERY)
    }

    @Test
    fun testGetNewestGitRepo_apiv4() {
        val jsonNode = ObjectMapper().readTree("{ \"data\": {\"organization\": {\"repositories\": {\"edges\": [{\"node\": {\"name\": \"A_GithubRepo\"}}]}}}}")
        `when`(restTemplate.postForObject<JsonNode>(anyString(), any(), eq(JsonNode::class.java)
        )).thenReturn(jsonNode)
        assertEquals("A_GithubRepo", githubRepoService.getNewestGithubRepo_apiv4(TOKEN, QUERY).name)
    }

    @Test(expected = NullPointerException::class)
    fun testGetNewestGitRepo_apiv4_RepoDoesNotExist() {
        val jsonNode = ObjectMapper().readTree("{ \"data\": {\"organization\": {\"repositories\": {\"edges\": []}}}}")
        `when`(restTemplate.postForObject<JsonNode>(anyString(), any(), eq(JsonNode::class.java)
        )).thenReturn(jsonNode)
        githubRepoService.getNewestGithubRepo_apiv4(TOKEN, QUERY)
    }

    @Test(expected = HttpClientErrorException.NotFound::class)
    fun testGetNewestGitRepo_apiv4_OrganizationDoesNotExist() {
        `when`(restTemplate.postForObject<JsonNode>(anyString(), any(), eq(JsonNode::class.java)
        )).thenThrow(HttpClientErrorException.NotFound::class.java)
        githubRepoService.getNewestGithubRepo_apiv4(TOKEN, QUERY)
    }

    @Test(expected = HttpClientErrorException.Unauthorized::class)
    fun testGetNewestGitRepo_apiv4_WithWrongToken() {
        `when`(restTemplate.postForObject<JsonNode>(anyString(), any(), eq(JsonNode::class.java)
        )).thenThrow(HttpClientErrorException.Unauthorized::class.java)
        githubRepoService.getNewestGithubRepo_apiv4(TOKEN, QUERY)
    }
}
