package garstka.jakub.allegro_repo.controllers.v1

import garstka.jakub.allegro_repo.api.v1.GithubRepoDTO
import garstka.jakub.allegro_repo.services.GithubRepoService
import org.hamcrest.CoreMatchers.equalTo

import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.*
import org.mockito.Mockito.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import org.springframework.web.client.HttpClientErrorException
import java.lang.NullPointerException

@RunWith(SpringRunner::class)
@WebMvcTest(GithubRepoController::class)
class GithubRepoControllerTest {
    companion object {
        private const val URL_APIV3 = "/api_v3"
        private const val URL_APIV4 = "/api_v4"
        private const val NAME = "repo_0"
        private const val TOKEN = "{\"token\": \"token\"}"
    }

    @Autowired
    private lateinit var mockMvc: MockMvc

    @MockBean
    private lateinit var githubRepoService: GithubRepoService

    @Before
    fun setUp()  {
        MockitoAnnotations.initMocks(this)
    }

    @Test
    fun testGetNewestGithubRepo_apiv3() {
        `when`(githubRepoService.getNewestGithubRepo_apiv3(ArgumentMatchers.anyString())).thenReturn(GithubRepoDTO(NAME))

        mockMvc.perform(
                MockMvcRequestBuilders.get(URL_APIV3)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk)
                .andExpect(MockMvcResultMatchers.jsonPath("$.name", equalTo(NAME)))
    }

    @Test
    fun testGetNewestGithubRepo_apiv3_RepoDoesNotExist() {
        `when`(githubRepoService.getNewestGithubRepo_apiv3(anyString()))
                .thenThrow(NoSuchElementException::class.java)

        mockMvc.perform(
                MockMvcRequestBuilders.get(URL_APIV3)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk)
                .andExpect(MockMvcResultMatchers.jsonPath("$.error", equalTo("Repository does not exist")))
    }
    @Test
    fun testGetNewestGithubRepo_apiv3_OrganizationDoesNotExist() {
        `when`(githubRepoService.getNewestGithubRepo_apiv3(anyString()))
                .thenThrow(HttpClientErrorException.NotFound::class.java)

        mockMvc.perform(
                MockMvcRequestBuilders.get(URL_APIV3)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest)
                .andExpect(MockMvcResultMatchers.jsonPath("$.error", equalTo("Organization does not exist")))
    }

    @Test
    fun testGetNewestGithubRepo_apiv4() {
        `when`(githubRepoService.getNewestGithubRepo_apiv4(anyString(), anyString())).thenReturn(GithubRepoDTO(NAME))

        mockMvc.perform(
                MockMvcRequestBuilders.post(URL_APIV4)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(TOKEN))
                .andExpect(MockMvcResultMatchers.status().isOk)
                .andExpect(MockMvcResultMatchers.jsonPath("$.name", equalTo(NAME)))
    }

    @Test
    fun testGetNewestGithubRepo_apiv4_RepoDoesNotExist() {
        `when`(githubRepoService.getNewestGithubRepo_apiv4(anyString(), anyString())).thenThrow(NoSuchElementException::class.java)

        mockMvc.perform(
                MockMvcRequestBuilders.post(URL_APIV4)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(TOKEN))
                .andExpect(MockMvcResultMatchers.status().isOk)
                .andExpect(MockMvcResultMatchers.jsonPath("$.error", equalTo("Repository does not exist")))
    }

    @Test
    fun testGetNewestGithubRepo_apiv4_OrganizationDoesNotExist() {
        `when`(githubRepoService.getNewestGithubRepo_apiv4(anyString(), anyString())).thenThrow(HttpClientErrorException.NotFound::class.java)

        mockMvc.perform(
                MockMvcRequestBuilders.post(URL_APIV4)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(TOKEN))
                .andExpect(MockMvcResultMatchers.status().isBadRequest)
                .andExpect(MockMvcResultMatchers.jsonPath("$.error", equalTo("Organization does not exist")))
    }

    @Test
    fun testGetNewestGithubRepo_apiv4_WithWrongToken() {
        `when`(githubRepoService.getNewestGithubRepo_apiv4(anyString(), anyString())).thenThrow(HttpClientErrorException.Unauthorized::class.java)

        mockMvc.perform(
                MockMvcRequestBuilders.post(URL_APIV4)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(TOKEN))
                .andExpect(MockMvcResultMatchers.status().isUnauthorized)
                .andExpect(MockMvcResultMatchers.jsonPath("$.error", equalTo("Invalid token")))
    }

    @Test
    fun testGetNewestGithubRepo_apiv4_WithoutToken() {
        mockMvc.perform(
                MockMvcRequestBuilders.post(URL_APIV4)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest)
    }

    @Test
    fun testGetNewestGithubRepo_apiv4_InvalidParameter() {
        `when`(githubRepoService.getNewestGithubRepo_apiv4(anyString(), anyString())).thenThrow(NullPointerException::class.java)

        mockMvc.perform(
                MockMvcRequestBuilders.post(URL_APIV4)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(TOKEN))
                .andExpect(MockMvcResultMatchers.status().isOk)
                .andExpect(MockMvcResultMatchers.jsonPath("$.error", equalTo("Invalid parameter")))
    }

}

