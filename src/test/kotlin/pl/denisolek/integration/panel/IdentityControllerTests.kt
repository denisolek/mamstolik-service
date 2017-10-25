package pl.denisolek.integration.panel

import com.nhaarman.mockito_kotlin.doReturn
import com.nhaarman.mockito_kotlin.whenever
import org.apache.commons.lang3.RandomStringUtils.randomAlphanumeric
import org.hamcrest.Matchers.`is`
import org.hamcrest.Matchers.instanceOf
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.SpyBean
import org.springframework.http.MediaType
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.test.web.servlet.setup.DefaultMockMvcBuilder
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.context.WebApplicationContext
import pl.denisolek.core.security.Authority
import pl.denisolek.core.user.User.AccountState
import pl.denisolek.core.user.UserRepository
import pl.denisolek.infrastructure.PANEL_BASE_PATH
import pl.denisolek.infrastructure.config.security.AuthorizationService
import pl.denisolek.infrastructure.util.convertObjectToJsonBytes
import pl.denisolek.panel.identity.DTO.RegisterDTO
import pl.denisolek.panel.identity.IdentityApi
import pl.denisolek.stubs.UserStub
import pl.denisolek.stubs.dto.ChangePasswordDTOStub
import pl.denisolek.stubs.dto.RegisterDTOStub
import pl.denisolek.stubs.dto.SetPasswordDTOStub
import javax.transaction.Transactional

@RunWith(SpringRunner::class)
@ActiveProfiles("test", "fakeAuthorization")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Transactional
class IdentityControllerTests {
    @SpyBean
    lateinit var authorizationService: AuthorizationService

    @Autowired
    lateinit var userRepository: UserRepository

    @Autowired
    lateinit var passwordEncoder: PasswordEncoder

    @Autowired
    lateinit var applicationContext: WebApplicationContext

    lateinit var mvc: MockMvc

    val USERS_BASE_PATH = "$PANEL_BASE_PATH${IdentityApi.USERS_BASE_PATH}"
    val USERS_PASSWORD_PATH = "$PANEL_BASE_PATH${IdentityApi.USERS_PASSWORD_PATH}"
    val RESTAURANTS_PATH = "$PANEL_BASE_PATH${IdentityApi.RESTAURANTS_BASE_PATH}"

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

    @Test
    fun `registerOwner_ email is empty`() {
        var registerDTO = RegisterDTOStub.getRegisterDTO()
        registerDTO.email = ""

        val body = convertObjectToJsonBytes(registerDTO)

        val result = mvc.perform(post(USERS_BASE_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .content(body))

        result.andExpect(status().isBadRequest)
    }

    @Test
    fun `registerOwner_ email wrong format`() {
        var registerDTO = RegisterDTOStub.getRegisterDTO()
        registerDTO.email = "test.test.pl"

        val body = convertObjectToJsonBytes(registerDTO)

        val result = mvc.perform(post(USERS_BASE_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .content(body))

        result.andExpect(status().isBadRequest)
    }

    @Test
    fun `registerOwner_ email too long`() {
        var registerDTO = RegisterDTOStub.getRegisterDTO()
        registerDTO.email = "${randomAlphanumeric(100)}@test.pl"

        val body = convertObjectToJsonBytes(registerDTO)

        val result = mvc.perform(post(USERS_BASE_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .content(body))

        result.andExpect(status().isBadRequest)
    }

    @Test
    fun `registerOwner_ firstName is empty`() {
        var registerDTO = RegisterDTOStub.getRegisterDTO()
        registerDTO.firstName = ""

        val body = convertObjectToJsonBytes(registerDTO)

        val result = mvc.perform(post(USERS_BASE_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .content(body))

        result.andExpect(status().isBadRequest)
    }

    @Test
    fun `registerOwner_ firstName is not valid`() {
        var registerDTO = RegisterDTOStub.getRegisterDTO()
        registerDTO.firstName = "123Test"

        val body = convertObjectToJsonBytes(registerDTO)

        val result = mvc.perform(post(USERS_BASE_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .content(body))

        result.andExpect(status().isBadRequest)
    }

    @Test
    fun `registerOwner_ lastName is empty`() {
        var registerDTO = RegisterDTOStub.getRegisterDTO()
        registerDTO.lastName = ""

        val body = convertObjectToJsonBytes(registerDTO)

        val result = mvc.perform(post(USERS_BASE_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .content(body))

        result.andExpect(status().isBadRequest)
    }

    @Test
    fun `registerOwner_ lastName is not valid`() {
        var registerDTO = RegisterDTOStub.getRegisterDTO()
        registerDTO.lastName = "123Test"

        val body = convertObjectToJsonBytes(registerDTO)

        val result = mvc.perform(post(USERS_BASE_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .content(body))

        result.andExpect(status().isBadRequest)
    }

    @Test
    fun `registerOwner_ companyName is empty`() {
        var registerDTO = RegisterDTOStub.getRegisterDTO()
        registerDTO.companyName = ""

        val body = convertObjectToJsonBytes(registerDTO)

        val result = mvc.perform(post(USERS_BASE_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .content(body))

        result.andExpect(status().isBadRequest)
    }

    @Test
    fun `registerOwner_ nip is empty`() {
        var registerDTO = RegisterDTOStub.getRegisterDTO()
        registerDTO.nip = ""

        val body = convertObjectToJsonBytes(registerDTO)

        val result = mvc.perform(post(USERS_BASE_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .content(body))

        result.andExpect(status().isBadRequest)
    }

    @Test
    fun `registerOwner_ phoneNumber is empty`() {
        var registerDTO = RegisterDTOStub.getRegisterDTO()
        registerDTO.phoneNumber = ""

        val body = convertObjectToJsonBytes(registerDTO)

        val result = mvc.perform(post(USERS_BASE_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .content(body))

        result.andExpect(status().isBadRequest)
    }

    @Test
    fun `registerOwner_ phoneNumber with letters`() {
        var registerDTO = RegisterDTOStub.getRegisterDTO()
        registerDTO.phoneNumber = "111222ccc"

        val body = convertObjectToJsonBytes(registerDTO)

        val result = mvc.perform(post(USERS_BASE_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .content(body))

        result.andExpect(status().isBadRequest)
    }

    @Test
    fun `registerOwner_ phoneNumber wrong length`() {
        var registerDTO = RegisterDTOStub.getRegisterDTO()
        registerDTO.phoneNumber = "111 222 33"

        val body = convertObjectToJsonBytes(registerDTO)

        val result = mvc.perform(post(USERS_BASE_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .content(body))

        result.andExpect(status().isBadRequest)
    }

    @Test
    fun `registerOwner_ phoneNumber with not allowed chars`() {
        var registerDTO = RegisterDTOStub.getRegisterDTO()
        registerDTO.phoneNumber = "+48 111*222*333"

        val body = convertObjectToJsonBytes(registerDTO)

        val result = mvc.perform(post(USERS_BASE_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .content(body))

        result.andExpect(status().isBadRequest)
    }

    @Test
    fun `registerOwner_ correct data`() {
        var registerDTO = RegisterDTO(
                email = "test2@test.pl",
                firstName = "Test",
                lastName = "Testowy",
                companyName = "TestowyCompanyName",
                nip = "5756226338",
                phoneNumber = "111222333"
        )

        val body = convertObjectToJsonBytes(registerDTO)

        val result = mvc.perform(post(USERS_BASE_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .content(body))

        result.andExpect(status().isCreated)

        val createdUser = userRepository.findByEmail("test2@test.pl")

        assertEquals(8, createdUser.username!!.length)
        assertEquals("test2@test.pl", createdUser.email)
        assertEquals("Test", createdUser.firstName)
        assertEquals("Testowy", createdUser.lastName)
        assertEquals("TestowyCompanyName", createdUser.companyName)
        assertEquals("5756226338", createdUser.nip)
        assertEquals("111222333", createdUser.phoneNumber)
        assertEquals(30, createdUser.activationKey!!.length)
        assertEquals(1, createdUser.authorities.size)
        assertTrue(createdUser.authorities.contains(Authority(Authority.Role.ROLE_OWNER)))
    }

    @Test
    fun `setPassword_ wrong username`() {
        var setPasswordDTO = SetPasswordDTOStub.getSetPasswordDTO().copy(
                username = "wrongUsername"
        )

        val body = convertObjectToJsonBytes(setPasswordDTO)

        val result = mvc.perform(post(USERS_PASSWORD_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .content(body))

        result
                .andExpect(status().isNotFound)
                .andExpect(jsonPath("$.message", `is`("User not found.")))
    }

    @Test
    fun `setPassword_ wrong activationKey`() {
        var setPasswordDTO = SetPasswordDTOStub.getSetPasswordDTO().copy(
                activationKey = "wrongActivationKey"
        )

        val body = convertObjectToJsonBytes(setPasswordDTO)

        val result = mvc.perform(post(USERS_PASSWORD_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .content(body))

        result
                .andExpect(status().isBadRequest)
                .andExpect(jsonPath("$.message", `is`("Activation key doesn't match or password is already set.")))

        print(1)
    }

    @Test
    fun `setPassword_ password too short`() {
        var setPasswordDTO = SetPasswordDTOStub.getSetPasswordDTO().copy(
                password = "aA1"
        )

        val body = convertObjectToJsonBytes(setPasswordDTO)

        val result = mvc.perform(post(USERS_PASSWORD_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .content(body))

        result
                .andExpect(status().isBadRequest)
                .andReturn()

        assertThat(result.andReturn().resolvedException, instanceOf(MethodArgumentNotValidException::class.java))
    }

    @Test
    fun `setPassword_ password too long`() {
        var setPasswordDTO = SetPasswordDTOStub.getSetPasswordDTO().copy(
                password = "aA1${randomAlphanumeric(78)}"
        )

        val body = convertObjectToJsonBytes(setPasswordDTO)

        val result = mvc.perform(post(USERS_PASSWORD_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .content(body))

        result
                .andExpect(status().isBadRequest)
                .andReturn()

        assertThat(result.andReturn().resolvedException, instanceOf(MethodArgumentNotValidException::class.java))
    }

    @Test
    fun `setPassword_ password without any a-z`() {
        var setPasswordDTO = SetPasswordDTOStub.getSetPasswordDTO().copy(
                password = "TEST123456"
        )

        val body = convertObjectToJsonBytes(setPasswordDTO)

        val result = mvc.perform(post(USERS_PASSWORD_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .content(body))

        result
                .andExpect(status().isBadRequest)
                .andReturn()

        assertThat(result.andReturn().resolvedException, instanceOf(MethodArgumentNotValidException::class.java))
    }

    @Test
    fun `setPassword_ password without any A-Z`() {
        var setPasswordDTO = SetPasswordDTOStub.getSetPasswordDTO().copy(
                password = "test123456"
        )

        val body = convertObjectToJsonBytes(setPasswordDTO)

        val result = mvc.perform(post(USERS_PASSWORD_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .content(body))

        result
                .andExpect(status().isBadRequest)
                .andReturn()

        assertThat(result.andReturn().resolvedException, instanceOf(MethodArgumentNotValidException::class.java))
    }

    @Test
    fun `setPassword_ password without any 1-9`() {
        var setPasswordDTO = SetPasswordDTOStub.getSetPasswordDTO().copy(
                password = "testTESTOWY"
        )

        val body = convertObjectToJsonBytes(setPasswordDTO)

        val result = mvc.perform(post(USERS_PASSWORD_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .content(body))

        result
                .andExpect(status().isBadRequest)
                .andReturn()

        assertThat(result.andReturn().resolvedException, instanceOf(MethodArgumentNotValidException::class.java))
    }

    @Test
    fun `setPassword_ correct data`() {
        var setPasswordDTO = SetPasswordDTOStub.getSetPasswordDTO()

        val body = convertObjectToJsonBytes(setPasswordDTO)

        val result = mvc.perform(post(USERS_PASSWORD_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .content(body))

        result
                .andExpect(status().isOk)

        val updatedUser = userRepository.findByUsername("ms800000")

        assertEquals("ms800000@test.pl", updatedUser.email)
        assertEquals("Test", updatedUser.firstName)
        assertEquals("Testowy", updatedUser.lastName)
        assertEquals(null, updatedUser.activationKey)
        assertEquals(AccountState.ACTIVE, updatedUser.accountState)
        assertNotNull(updatedUser.password)
    }

    @Test
    fun `changePassword_ password too short`() {
        val user = UserStub.getUserOwner()
        var changePasswordDTO = ChangePasswordDTOStub.getChangePasswordDTO().copy(
                newPassword = "aA1"
        )

        val body = convertObjectToJsonBytes(changePasswordDTO)

        doReturn(user).whenever(authorizationService).getCurrentUser()

        val result = mvc.perform(put(USERS_PASSWORD_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .content(body))

        result
                .andExpect(status().isBadRequest)
                .andReturn()

        assertThat(result.andReturn().resolvedException, instanceOf(MethodArgumentNotValidException::class.java))
    }

    @Test
    fun `changePassword_ password too long`() {
        val user = UserStub.getUserOwner()
        var changePasswordDTO = ChangePasswordDTOStub.getChangePasswordDTO().copy(
                newPassword = "aA1${randomAlphanumeric(78)}"
        )

        val body = convertObjectToJsonBytes(changePasswordDTO)

        doReturn(user).whenever(authorizationService).getCurrentUser()

        val result = mvc.perform(put(USERS_PASSWORD_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .content(body))

        result
                .andExpect(status().isBadRequest)
                .andReturn()

        assertThat(result.andReturn().resolvedException, instanceOf(MethodArgumentNotValidException::class.java))
    }

    @Test
    fun `changePassword_ password without any a-z`() {
        val user = UserStub.getUserOwner()
        var changePasswordDTO = ChangePasswordDTOStub.getChangePasswordDTO().copy(
                newPassword = "TEST123456"
        )

        val body = convertObjectToJsonBytes(changePasswordDTO)

        doReturn(user).whenever(authorizationService).getCurrentUser()

        val result = mvc.perform(put(USERS_PASSWORD_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .content(body))

        result
                .andExpect(status().isBadRequest)
                .andReturn()

        assertThat(result.andReturn().resolvedException, instanceOf(MethodArgumentNotValidException::class.java))
    }

    @Test
    fun `changePassword_ password without any A-Z`() {
        val user = UserStub.getUserOwner()
        var changePasswordDTO = ChangePasswordDTOStub.getChangePasswordDTO().copy(
                newPassword = "test123456"
        )

        val body = convertObjectToJsonBytes(changePasswordDTO)

        doReturn(user).whenever(authorizationService).getCurrentUser()

        val result = mvc.perform(put(USERS_PASSWORD_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .content(body))

        result
                .andExpect(status().isBadRequest)
                .andReturn()

        assertThat(result.andReturn().resolvedException, instanceOf(MethodArgumentNotValidException::class.java))
    }

    @Test
    fun `changePassword_ password without any 1-9`() {
        val user = UserStub.getUserOwner()
        var changePasswordDTO = ChangePasswordDTOStub.getChangePasswordDTO().copy(
                newPassword = "testTESTOWY"
        )

        val body = convertObjectToJsonBytes(changePasswordDTO)

        doReturn(user).whenever(authorizationService).getCurrentUser()

        val result = mvc.perform(put(USERS_PASSWORD_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .content(body))

        result
                .andExpect(status().isBadRequest)
                .andReturn()

        assertThat(result.andReturn().resolvedException, instanceOf(MethodArgumentNotValidException::class.java))
    }

    @Test
    @Transactional
    fun `changePassword_ correct data`() {
        val user = userRepository.findOne(1)
        var changePasswordDTO = ChangePasswordDTOStub.getChangePasswordDTO()

        val body = convertObjectToJsonBytes(changePasswordDTO)

        doReturn(user).whenever(authorizationService).getCurrentUser()

        val result = mvc.perform(put(USERS_PASSWORD_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .content(body))

        result
                .andExpect(status().isOk)

        val updatedUser = userRepository.findByEmail("test@test.pl")

        assertEquals("test@test.pl", updatedUser.email)
        assertTrue(passwordEncoder.matches(changePasswordDTO.newPassword, updatedUser.password))
    }

    @Test
    fun `getRestaurants_ `() {
        val user = userRepository.findOne(1)
        doReturn(user).whenever(authorizationService).getCurrentUser()
        mvc.perform(get(RESTAURANTS_PATH))
                .andExpect(status().isOk)
                .andExpect(jsonPath("$[0].id", `is`(1)))
                .andExpect(jsonPath("$[0].name", `is`("Piano Bar Restaurant & Cafe")))
                .andExpect(jsonPath("$[0].address", `is`("PÓŁWIEJSKA 42 1A, POZNAŃ")))
    }
}