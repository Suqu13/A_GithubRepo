package garstka.jakub.allegro_repo.controllers.v1

import garstka.jakub.allegro_repo.services.AuthToken
import garstka.jakub.allegro_repo.services.GithubRepoService
import garstka.jakub.allegro_repo.tools.QueryServiceImpl
import garstka.jakub.allegro_repo.tools.UrlServiceImpl
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.client.HttpClientErrorException
import java.lang.NullPointerException

@RestController
@RequestMapping("/")
class GithubRepoController(private val repoService: GithubRepoService) {

    @GetMapping("api_v3", produces = ["application/json"])
    @ResponseBody
    fun getNewestGithubRepo_apiv3(@RequestParam(name = "login", required = false, defaultValue = "Allegro") login: String,
                                  @RequestParam(name = "sort", required = false, defaultValue = "pushed") sort :String,
                                  @RequestParam(name = "direction", required = false, defaultValue = "desc") direction: String)
            = repoService.getNewestGithubRepo_apiv3(UrlServiceImpl.createUrl(login, sort, direction))

    @RequestMapping("api_v4", produces = ["application/json"], consumes = ["application/json"], method = [RequestMethod.POST])
    @ResponseBody
    fun getNewestGithubRepo_apiv4(@RequestBody token: AuthToken,
                                  @RequestParam(name = "login", required = false, defaultValue = "Allegro") login: String,
                                  @RequestParam(name = "field", required = false, defaultValue = "PUSHED_AT") field :String,
                                  @RequestParam(name = "direction", required = false, defaultValue = "DESC") direction: String)
            = repoService.getNewestGithubRepo_apiv4(token.token, QueryServiceImpl.createQuery(login, field, direction))

    @ExceptionHandler(NoSuchElementException::class)
    fun repoDoesNotExistExceptionHandler() = ResponseEntity("{\"error\": \"Repository does not exist\"}", HttpStatus.OK)

    @ExceptionHandler(NullPointerException::class)
    fun invalidParameterExceptionHandler() = ResponseEntity("{\"error\": \"Invalid parameter\"}", HttpStatus.OK)

    @ExceptionHandler(HttpClientErrorException.NotFound::class)
    fun organizationDoesNotExistExceptionHandler() = ResponseEntity("{\"error\": \"Organization does not exist\"}", HttpStatus.BAD_REQUEST)

    @ExceptionHandler(HttpClientErrorException.Unauthorized::class)
    fun unauthorisedExceptionHandler() = ResponseEntity("{\"error\": \"Invalid token\"}", HttpStatus.UNAUTHORIZED)


}