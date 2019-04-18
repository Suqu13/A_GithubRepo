package garstka.jakub.allegro_repo.services

import com.fasterxml.jackson.core.type.TypeReference
import garstka.jakub.allegro_repo.api.v1.GithubRepoDTO
import org.junit.Before

import org.junit.Assert.*
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations
import org.springframework.web.client.RestTemplate
import org.springframework.web.client.exchange


class GithubRepoServiceImplTest {

    @Mock
    lateinit var restTemplate: RestTemplate

    @Throws(Exception::class)

    @Before
    fun setUp()  {
        MockitoAnnotations.initMocks(this)
    }

    private fun <T> anyObject(): T {
        Mockito.anyObject<T>()
        return uninitialized()
    }

    private fun <T> any(type: Class<T>): T = Mockito.any<T>(type)


    fun <T> eq(value: T): T {
        return Mockito.eq(value) ?: value
    }

    private fun <T> uninitialized(): T = null as T

    @Throws(Exception::class)
    @Test
    fun testGetNewestGitRepo() {
        val githubRepoService = GithubRepoServiceImpl(restTemplate)
        val githubRepoDTOArray = arrayOf(GithubRepoDTO("repo_0"),
                GithubRepoDTO("repo_1"), GithubRepoDTO("repo_2")).toList()

        `when`(restTemplate.exchange<List<GithubRepoDTO>>(anyString(),
                anyObject(),
                anyObject(),
                eq(object : TypeReference<List<GithubRepoDTO>>(){}.type)).body
        ).thenReturn(githubRepoDTOArray)

        assertEquals(githubRepoDTOArray.first(), githubRepoService.getNewestGithubRepo())
    }
}
