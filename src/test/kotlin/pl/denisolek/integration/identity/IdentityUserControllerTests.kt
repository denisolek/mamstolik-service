package pl.denisolek.integration.identity

import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.test.web.servlet.setup.DefaultMockMvcBuilder
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.web.context.WebApplicationContext
import pl.denisolek.identity.user.IdentityUserApi
import pl.denisolek.infrastructure.IDENTITY_BASE_PATH
import pl.denisolek.infrastructure.util.convertObjectToJsonBytes
import pl.denisolek.stubs.dto.RegisterDTOStub
import javax.transaction.Transactional

@RunWith(SpringRunner::class)
@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Transactional
class IdentityUserControllerTests {


    @Autowired
    lateinit var applicationContext: WebApplicationContext

    lateinit var mvc: MockMvc

    val USERS_BASE_PATH = "$IDENTITY_BASE_PATH${IdentityUserApi.USERS_BASE_PATH}"

    @Before
    fun setup() {
        this.mvc = MockMvcBuilders
                .webAppContextSetup(applicationContext)
                .apply<DefaultMockMvcBuilder>(springSecurity())
                .build()
    }

    @Test
    fun `registerOwner_ email already exists`() {
        var registerDTO = RegisterDTOStub.getRegisterDTO()
        registerDTO.email = "test@test.pl"

        val body = convertObjectToJsonBytes(registerDTO)

        val result = mvc.perform(post(USERS_BASE_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .content(body))

        result.andExpect(status().isConflict)
    }
}