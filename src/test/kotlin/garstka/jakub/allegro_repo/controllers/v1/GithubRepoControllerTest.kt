package garstka.jakub.allegro_repo.controllers.v1

import garstka.jakub.allegro_repo.api.v1.GithubRepoDTO
import garstka.jakub.allegro_repo.controllers.RestResponseEntityExceptionHandler
import garstka.jakub.allegro_repo.services.GithubRepoService
import org.hamcrest.CoreMatchers.equalTo


import org.junit.Before
import org.junit.Test
import org.mockito.*
import org.mockito.Mockito.*
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import java.lang.Exception

class GithubRepoControllerTest {

    private val NAME = "repo_0"

    @Mock
    lateinit var githubRepoService: GithubRepoService

    @InjectMocks
    lateinit var githubRepoController: GithubRepoController

    lateinit var mockMvc: MockMvc

    @Throws(Exception::class)
    @Before
    fun setUp()  {
        MockitoAnnotations.initMocks(this)

        mockMvc = MockMvcBuilders.standaloneSetup(githubRepoController)
                .setControllerAdvice(RestResponseEntityExceptionHandler())
                .build()
    }

    @Throws(Exception::class)
    @Test
    fun testGetNewestGithubRepo() {
        `when`(githubRepoService.getNewestGithubRepo()).thenReturn(GithubRepoDTO(NAME))

        mockMvc.perform(MockMvcRequestBuilders.get("")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk)
                .andExpect(MockMvcResultMatchers.jsonPath("$.name", equalTo(NAME)))
    }
}
