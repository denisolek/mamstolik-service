package pl.denisolek.integration.panel

import com.nhaarman.mockito_kotlin.doReturn
import com.nhaarman.mockito_kotlin.whenever
import org.apache.commons.lang3.RandomStringUtils.randomAlphanumeric
import org.hamcrest.Matchers.*
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
import pl.denisolek.core.restaurant.Restaurant
import pl.denisolek.core.restaurant.Restaurant.RestaurantType.RESTAURANT
import pl.denisolek.core.restaurant.RestaurantRepository
import pl.denisolek.core.security.Authority
import pl.denisolek.core.user.User
import pl.denisolek.core.user.User.AccountState
import pl.denisolek.core.user.UserRepository
import pl.denisolek.infrastructure.PANEL_BASE_PATH
import pl.denisolek.infrastructure.config.security.AuthorizationService
import pl.denisolek.infrastructure.util.convertObjectToJsonBytes
import pl.denisolek.panel.identity.DTO.CreateRestaurantDTO
import pl.denisolek.panel.identity.DTO.RegisterDTO
import pl.denisolek.panel.identity.IdentityApi
import pl.denisolek.stubs.UserStub
import pl.denisolek.stubs.dto.*
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
    lateinit var restaurantRepository: RestaurantRepository

    @Autowired
    lateinit var passwordEncoder: PasswordEncoder

    @Autowired
    lateinit var applicationContext: WebApplicationContext

    lateinit var mvc: MockMvc

    val USERS_BASE_PATH = "$PANEL_BASE_PATH${IdentityApi.USERS_BASE_PATH}"
    val USERS_PASSWORD_PATH = "$PANEL_BASE_PATH${IdentityApi.USERS_PASSWORD_PATH}"
    val USERS_LOST_PASSWORD_PATH = "$PANEL_BASE_PATH${IdentityApi.USERS_LOST_PASSWORD_PATH}"
    val USERS_RESET_PASSWORD_PATH = "$PANEL_BASE_PATH${IdentityApi.USERS_RESET_PASSWORD_PATH}"
    val RESTAURANTS_PATH = "$PANEL_BASE_PATH${IdentityApi.RESTAURANTS_BASE_PATH}"
    val RESTAURANTS_URL_NAME_PATH = "$PANEL_BASE_PATH${IdentityApi.RESTAURANTS_URL_NAME_PATH}"
    val EMPLOYEES_PATH = "$PANEL_BASE_PATH${IdentityApi.EMPLOYEES_BASE_PATH}"

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

        val result = mvc.perform(
            post(USERS_BASE_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .content(body)
        )

        result.andExpect(status().isConflict)
    }

    @Test
    fun `registerOwner_ email is empty`() {
        var registerDTO = RegisterDTOStub.getRegisterDTO()
        registerDTO.email = ""

        val body = convertObjectToJsonBytes(registerDTO)

        val result = mvc.perform(
            post(USERS_BASE_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .content(body)
        )

        result.andExpect(status().isBadRequest)
    }

    @Test
    fun `registerOwner_ email wrong format`() {
        var registerDTO = RegisterDTOStub.getRegisterDTO()
        registerDTO.email = "test.test.pl"

        val body = convertObjectToJsonBytes(registerDTO)

        val result = mvc.perform(
            post(USERS_BASE_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .content(body)
        )

        result.andExpect(status().isBadRequest)
    }

    @Test
    fun `registerOwner_ email too long`() {
        var registerDTO = RegisterDTOStub.getRegisterDTO()
        registerDTO.email = "${randomAlphanumeric(100)}@test.pl"

        val body = convertObjectToJsonBytes(registerDTO)

        val result = mvc.perform(
            post(USERS_BASE_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .content(body)
        )

        result.andExpect(status().isBadRequest)
    }

    @Test
    fun `registerOwner_ firstName is empty`() {
        var registerDTO = RegisterDTOStub.getRegisterDTO()
        registerDTO.firstName = ""

        val body = convertObjectToJsonBytes(registerDTO)

        val result = mvc.perform(
            post(USERS_BASE_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .content(body)
        )

        result.andExpect(status().isBadRequest)
    }

    @Test
    fun `registerOwner_ firstName is not valid`() {
        var registerDTO = RegisterDTOStub.getRegisterDTO()
        registerDTO.firstName = "123Test"

        val body = convertObjectToJsonBytes(registerDTO)

        val result = mvc.perform(
            post(USERS_BASE_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .content(body)
        )

        result.andExpect(status().isBadRequest)
    }

    @Test
    fun `registerOwner_ lastName is empty`() {
        var registerDTO = RegisterDTOStub.getRegisterDTO()
        registerDTO.lastName = ""

        val body = convertObjectToJsonBytes(registerDTO)

        val result = mvc.perform(
            post(USERS_BASE_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .content(body)
        )

        result.andExpect(status().isBadRequest)
    }

    @Test
    fun `registerOwner_ lastName is not valid`() {
        var registerDTO = RegisterDTOStub.getRegisterDTO()
        registerDTO.lastName = "123Test"

        val body = convertObjectToJsonBytes(registerDTO)

        val result = mvc.perform(
            post(USERS_BASE_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .content(body)
        )

        result.andExpect(status().isBadRequest)
    }

    @Test
    fun `registerOwner_ companyName is empty`() {
        var registerDTO = RegisterDTOStub.getRegisterDTO()
        registerDTO.companyName = ""

        val body = convertObjectToJsonBytes(registerDTO)

        val result = mvc.perform(
            post(USERS_BASE_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .content(body)
        )

        result.andExpect(status().isBadRequest)
    }

    @Test
    fun `registerOwner_ nip is empty`() {
        var registerDTO = RegisterDTOStub.getRegisterDTO()
        registerDTO.nip = ""

        val body = convertObjectToJsonBytes(registerDTO)

        val result = mvc.perform(
            post(USERS_BASE_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .content(body)
        )

        result.andExpect(status().isBadRequest)
    }

    @Test
    fun `registerOwner_ phoneNumber is empty`() {
        var registerDTO = RegisterDTOStub.getRegisterDTO()
        registerDTO.phoneNumber = ""

        val body = convertObjectToJsonBytes(registerDTO)

        val result = mvc.perform(
            post(USERS_BASE_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .content(body)
        )

        result.andExpect(status().isBadRequest)
    }

    @Test
    fun `registerOwner_ phoneNumber with letters`() {
        var registerDTO = RegisterDTOStub.getRegisterDTO()
        registerDTO.phoneNumber = "111222ccc"

        val body = convertObjectToJsonBytes(registerDTO)

        val result = mvc.perform(
            post(USERS_BASE_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .content(body)
        )

        result.andExpect(status().isBadRequest)
    }

    @Test
    fun `registerOwner_ phoneNumber wrong length`() {
        var registerDTO = RegisterDTOStub.getRegisterDTO()
        registerDTO.phoneNumber = "111 222 33"

        val body = convertObjectToJsonBytes(registerDTO)

        val result = mvc.perform(
            post(USERS_BASE_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .content(body)
        )

        result.andExpect(status().isBadRequest)
    }

    @Test
    fun `registerOwner_ phoneNumber with not allowed chars`() {
        var registerDTO = RegisterDTOStub.getRegisterDTO()
        registerDTO.phoneNumber = "+48 111*222*333"

        val body = convertObjectToJsonBytes(registerDTO)

        val result = mvc.perform(
            post(USERS_BASE_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .content(body)
        )

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

        val result = mvc.perform(
            post(USERS_BASE_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .content(body)
        )

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

        val result = mvc.perform(
            post(USERS_PASSWORD_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .content(body)
        )

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

        val result = mvc.perform(
            post(USERS_PASSWORD_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .content(body)
        )

        result
            .andExpect(status().isBadRequest)
            .andExpect(jsonPath("$.message", `is`("Activation key doesn't match or password is already set.")))
    }

    @Test
    fun `setPassword_ password too short`() {
        var setPasswordDTO = SetPasswordDTOStub.getSetPasswordDTO().copy(
            password = "aA1"
        )

        val body = convertObjectToJsonBytes(setPasswordDTO)

        val result = mvc.perform(
            post(USERS_PASSWORD_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .content(body)
        )

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

        val result = mvc.perform(
            post(USERS_PASSWORD_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .content(body)
        )

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

        val result = mvc.perform(
            post(USERS_PASSWORD_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .content(body)
        )

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

        val result = mvc.perform(
            post(USERS_PASSWORD_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .content(body)
        )

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

        val result = mvc.perform(
            post(USERS_PASSWORD_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .content(body)
        )

        result
            .andExpect(status().isBadRequest)
            .andReturn()

        assertThat(result.andReturn().resolvedException, instanceOf(MethodArgumentNotValidException::class.java))
    }

    @Test
    fun `setPassword_ correct data`() {
        var setPasswordDTO = SetPasswordDTOStub.getSetPasswordDTO()

        val body = convertObjectToJsonBytes(setPasswordDTO)

        val result = mvc.perform(
            post(USERS_PASSWORD_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .content(body)
        )

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

        val result = mvc.perform(
            put(USERS_PASSWORD_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .content(body)
        )

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

        val result = mvc.perform(
            put(USERS_PASSWORD_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .content(body)
        )

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

        val result = mvc.perform(
            put(USERS_PASSWORD_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .content(body)
        )

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

        val result = mvc.perform(
            put(USERS_PASSWORD_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .content(body)
        )

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

        val result = mvc.perform(
            put(USERS_PASSWORD_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .content(body)
        )

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

        val result = mvc.perform(
            put(USERS_PASSWORD_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .content(body)
        )

        result
            .andExpect(status().isOk)

        val updatedUser = userRepository.findByEmail("test@test.pl")

        assertEquals("test@test.pl", updatedUser.email)
        assertTrue(passwordEncoder.matches(changePasswordDTO.newPassword, updatedUser.password))
    }

    @Test
    fun `lostPassword_ not existing email`() {
        var lostPasswordDTO = LostPasswordDTOStub.getLostPasswordDTO()
        lostPasswordDTO.email = "test123123@test.pl"

        val body = convertObjectToJsonBytes(lostPasswordDTO)

        val result = mvc.perform(
            post(USERS_LOST_PASSWORD_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .content(body)
        )

        result.andExpect(status().isOk)

        val updatedUser = userRepository.findByEmail(lostPasswordDTO.email)

        assertEquals(null, updatedUser)
    }

    @Test
    @Transactional
    fun `lostPassword_ not owner`() {
        var lostPasswordDTO = LostPasswordDTOStub.getLostPasswordDTO()
        lostPasswordDTO.email = "ms100002@mamstolik.pl"

        val body = convertObjectToJsonBytes(lostPasswordDTO)

        val result = mvc.perform(
            post(USERS_LOST_PASSWORD_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .content(body)
        )

        result.andExpect(status().isOk)

        val updatedUser = userRepository.findByEmail(lostPasswordDTO.email)

        assertEquals(null, updatedUser.resetPasswordKey)
    }

    @Test
    @Transactional
    fun `lostPassword_ correct data`() {
        var lostPasswordDTO = LostPasswordDTOStub.getLostPasswordDTO()
        lostPasswordDTO.email = "test@test.pl"

        val body = convertObjectToJsonBytes(lostPasswordDTO)

        val result = mvc.perform(
            post(USERS_LOST_PASSWORD_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .content(body)
        )

        result.andExpect(status().isOk)

        val updatedUser = userRepository.findByEmail(lostPasswordDTO.email)

        assertNotNull(updatedUser.resetPasswordKey)
    }

    @Test
    fun `resetPassword_ username is empty`() {
        val resetPasswordDTO = ResetPasswordDTOStub.getResetPasswordDTO().copy(
            username = ""
        )

        val body = convertObjectToJsonBytes(resetPasswordDTO)

        val result = mvc.perform(
            put(USERS_RESET_PASSWORD_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .content(body)
        )

        result
            .andExpect(status().isBadRequest)
            .andReturn()

        assertThat(result.andReturn().resolvedException, instanceOf(MethodArgumentNotValidException::class.java))
    }

    @Test
    fun `resetPassword_ password is empty`() {
        val resetPasswordDTO = ResetPasswordDTOStub.getResetPasswordDTO().copy(
            password = ""
        )

        val body = convertObjectToJsonBytes(resetPasswordDTO)

        val result = mvc.perform(
            put(USERS_RESET_PASSWORD_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .content(body)
        )

        result
            .andExpect(status().isBadRequest)
            .andReturn()

        assertThat(result.andReturn().resolvedException, instanceOf(MethodArgumentNotValidException::class.java))
    }

    @Test
    fun `resetPassword_ password too short`() {
        val resetPasswordDTO = ResetPasswordDTOStub.getResetPasswordDTO().copy(
            password = "aA1"
        )

        val body = convertObjectToJsonBytes(resetPasswordDTO)

        val result = mvc.perform(
            put(USERS_RESET_PASSWORD_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .content(body)
        )

        result
            .andExpect(status().isBadRequest)
            .andReturn()

        assertThat(result.andReturn().resolvedException, instanceOf(MethodArgumentNotValidException::class.java))
    }

    @Test
    fun `resetPassword_ password too long`() {
        val resetPasswordDTO = ResetPasswordDTOStub.getResetPasswordDTO().copy(
            password = "aA1${randomAlphanumeric(78)}"
        )

        val body = convertObjectToJsonBytes(resetPasswordDTO)

        val result = mvc.perform(
            put(USERS_RESET_PASSWORD_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .content(body)
        )

        result
            .andExpect(status().isBadRequest)
            .andReturn()

        assertThat(result.andReturn().resolvedException, instanceOf(MethodArgumentNotValidException::class.java))
    }

    @Test
    fun `resetPassword_ password without any a-z`() {
        val resetPasswordDTO = ResetPasswordDTOStub.getResetPasswordDTO().copy(
            password = "TEST123456"
        )

        val body = convertObjectToJsonBytes(resetPasswordDTO)

        val result = mvc.perform(
            put(USERS_RESET_PASSWORD_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .content(body)
        )

        result
            .andExpect(status().isBadRequest)
            .andReturn()

        assertThat(result.andReturn().resolvedException, instanceOf(MethodArgumentNotValidException::class.java))
    }

    @Test
    fun `resetPassword_ password without any A-Z`() {
        val resetPasswordDTO = ResetPasswordDTOStub.getResetPasswordDTO().copy(
            password = "test123456"
        )

        val body = convertObjectToJsonBytes(resetPasswordDTO)

        val result = mvc.perform(
            put(USERS_RESET_PASSWORD_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .content(body)
        )

        result
            .andExpect(status().isBadRequest)
            .andReturn()

        assertThat(result.andReturn().resolvedException, instanceOf(MethodArgumentNotValidException::class.java))
    }

    @Test
    fun `resetPassword_ password without any 1-9`() {
        val resetPasswordDTO = ResetPasswordDTOStub.getResetPasswordDTO().copy(
            password = "testTESTOWY"
        )

        val body = convertObjectToJsonBytes(resetPasswordDTO)

        val result = mvc.perform(
            put(USERS_RESET_PASSWORD_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .content(body)
        )

        result
            .andExpect(status().isBadRequest)
            .andReturn()

        assertThat(result.andReturn().resolvedException, instanceOf(MethodArgumentNotValidException::class.java))
    }

    @Test
    fun `resetPassword_ resetKey is empty`() {
        val resetPasswordDTO = ResetPasswordDTOStub.getResetPasswordDTO().copy(
            resetKey = ""
        )

        val body = convertObjectToJsonBytes(resetPasswordDTO)

        val result = mvc.perform(
            put(USERS_RESET_PASSWORD_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .content(body)
        )

        result
            .andExpect(status().isBadRequest)
            .andReturn()

        assertThat(result.andReturn().resolvedException, instanceOf(MethodArgumentNotValidException::class.java))
    }

    @Test
    fun `getRestaurants_ `() {
        val user = userRepository.findOne(1)
        doReturn(user).whenever(authorizationService).getCurrentUser()
        mvc.perform(get(RESTAURANTS_PATH))
            .andExpect(status().isOk)
            .andExpect(jsonPath("$[0].id", `is`(1)))
            .andExpect(jsonPath("$[0].name", `is`("Piano Bar Restaurant & Cafe")))
            .andExpect(jsonPath("$[0].streetName", `is`("Półwiejska 42")))
            .andExpect(jsonPath("$[0].buildingNumber", `is`("1A")))
            .andExpect(jsonPath("$[0].city", `is`("Poznań")))
    }

    @Test
    fun `getRestaurant_ existing`() {
        mvc.perform(get(RESTAURANTS_URL_NAME_PATH, "piano.bar.restaurant.&.cafe"))
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.name", `is`("Piano Bar Restaurant & Cafe")))
            .andExpect(jsonPath("$.username", `is`("ms100001")))
            .andExpect(jsonPath("$.avatar", isEmptyOrNullString()))
    }

    @Test
    fun `getRestaurant_ not existing`() {
        mvc.perform(get(RESTAURANTS_URL_NAME_PATH, "not.existing"))
            .andExpect(status().isNotFound)
    }

    @Test
    fun `getEmployees_ `() {
        val user = userRepository.findOne(10)
        doReturn(user).whenever(authorizationService).getCurrentUser()
        mvc.perform(get(EMPLOYEES_PATH))
            .andExpect(status().isOk)
            .andExpect(jsonPath("$[0].id", `is`(11)))
            .andExpect(jsonPath("$[0].username", `is`("ms100002")))
            .andExpect(jsonPath("$[0].firstName", `is`("Krystian")))
            .andExpect(jsonPath("$[0].lastName", `is`("Nowicki")))
            .andExpect(jsonPath("$[0].role", `is`("ROLE_MANAGER")))
            .andExpect(jsonPath("$[0].avatar", isEmptyOrNullString()))
            .andExpect(jsonPath("$[1].id", `is`(12)))
            .andExpect(jsonPath("$[1].username", `is`("ms100003")))
            .andExpect(jsonPath("$[1].firstName", `is`("Magdalena")))
            .andExpect(jsonPath("$[1].lastName", `is`("Karpińska")))
            .andExpect(jsonPath("$[1].role", `is`("ROLE_EMPLOYEE")))
            .andExpect(jsonPath("$[1].avatar", isEmptyOrNullString()))
    }

    @Test
    fun `createRestaurant_ empty name`() {
        var createRestaurantDTO = CreateRestaurantDTOStub.getCreateRestaurantDTOStub()
        createRestaurantDTO.name = ""

        val body = convertObjectToJsonBytes(createRestaurantDTO)

        val result = mvc.perform(
            post(RESTAURANTS_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .content(body)
        )

        result.andExpect(status().isBadRequest)
    }

    @Test
    fun `createRestaurant_ email is empty`() {
        var createRestaurantDTO = CreateRestaurantDTOStub.getCreateRestaurantDTOStub()
        createRestaurantDTO.email = ""

        val body = convertObjectToJsonBytes(createRestaurantDTO)

        val result = mvc.perform(
            post(RESTAURANTS_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .content(body)
        )

        result.andExpect(status().isBadRequest)
    }

    @Test
    fun `createRestaurant_ email wrong format`() {
        var createRestaurantDTO = CreateRestaurantDTOStub.getCreateRestaurantDTOStub()
        createRestaurantDTO.email = "test.test.pl"

        val body = convertObjectToJsonBytes(createRestaurantDTO)

        val result = mvc.perform(
            post(RESTAURANTS_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .content(body)
        )

        result.andExpect(status().isBadRequest)
    }

    @Test
    fun `createRestaurant_ email too long`() {
        var createRestaurantDTO = CreateRestaurantDTOStub.getCreateRestaurantDTOStub()
        createRestaurantDTO.email = "${randomAlphanumeric(100)}@test.pl"

        val body = convertObjectToJsonBytes(createRestaurantDTO)

        val result = mvc.perform(
            post(RESTAURANTS_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .content(body)
        )

        result.andExpect(status().isBadRequest)
    }

    @Test
    fun `createRestaurant_ type is empty`() {
        var createRestaurantDTO = CreateRestaurantDTOStub.getCreateRestaurantDTOStub()
        val body = convertObjectToJsonBytes(createRestaurantDTO).replace("\"type\":\"RESTAURANT\"", "\"type\":\"\"")

        val result = mvc.perform(
            post(RESTAURANTS_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .content(body)
        )

        result.andExpect(status().isBadRequest)
    }

    @Test
    fun `createRestaurant_ type is invalid`() {
        var createRestaurantDTO = CreateRestaurantDTOStub.getCreateRestaurantDTOStub()
        val body = convertObjectToJsonBytes(createRestaurantDTO).replace(
            "\"type\":\"RESTAURANT\"",
            "\"type\":\"INVALID_TYPE\""
        )

        val result = mvc.perform(
            post(RESTAURANTS_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .content(body)
        )

        result.andExpect(status().isBadRequest)
    }

    @Test
    fun `createRestaurant_ phoneNumber is empty`() {
        var createRestaurantDTO = CreateRestaurantDTOStub.getCreateRestaurantDTOStub()
        createRestaurantDTO.phoneNumber = ""

        val body = convertObjectToJsonBytes(createRestaurantDTO)

        val result = mvc.perform(
            post(RESTAURANTS_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .content(body)
        )

        result.andExpect(status().isBadRequest)
    }

    @Test
    fun `createRestaurant_ phoneNumber with letters`() {
        var createRestaurantDTO = CreateRestaurantDTOStub.getCreateRestaurantDTOStub()
        createRestaurantDTO.phoneNumber = "111222ccc"

        val body = convertObjectToJsonBytes(createRestaurantDTO)

        val result = mvc.perform(
            post(RESTAURANTS_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .content(body)
        )

        result.andExpect(status().isBadRequest)
    }

    @Test
    fun `createRestaurant_ phoneNumber wrong length`() {
        var createRestaurantDTO = CreateRestaurantDTOStub.getCreateRestaurantDTOStub()
        createRestaurantDTO.phoneNumber = "111 222 33"

        val body = convertObjectToJsonBytes(createRestaurantDTO)

        val result = mvc.perform(
            post(RESTAURANTS_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .content(body)
        )

        result.andExpect(status().isBadRequest)
    }

    @Test
    fun `createRestaurant_ phoneNumber with not allowed chars`() {
        var createRestaurantDTO = CreateRestaurantDTOStub.getCreateRestaurantDTOStub()
        createRestaurantDTO.phoneNumber = "+48 111*222*333"

        val body = convertObjectToJsonBytes(createRestaurantDTO)

        val result = mvc.perform(
            post(RESTAURANTS_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .content(body)
        )

        result.andExpect(status().isBadRequest)
    }

    @Test
    fun `createRestaurant_ password too short`() {
        var createRestaurantDTO = CreateRestaurantDTOStub.getCreateRestaurantDTOStub().copy(
            password = "aA1"
        )

        val body = convertObjectToJsonBytes(createRestaurantDTO)

        val result = mvc.perform(
            post(RESTAURANTS_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .content(body)
        )

        result
            .andExpect(status().isBadRequest)
            .andReturn()

        assertThat(result.andReturn().resolvedException, instanceOf(MethodArgumentNotValidException::class.java))
    }

    @Test
    fun `createRestaurant_ password too long`() {
        var createRestaurantDTO = CreateRestaurantDTOStub.getCreateRestaurantDTOStub().copy(
            password = "aA1${randomAlphanumeric(78)}"
        )

        val body = convertObjectToJsonBytes(createRestaurantDTO)

        val result = mvc.perform(
            post(RESTAURANTS_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .content(body)
        )

        result
            .andExpect(status().isBadRequest)
            .andReturn()

        assertThat(result.andReturn().resolvedException, instanceOf(MethodArgumentNotValidException::class.java))
    }

    @Test
    fun `createRestaurant_ password without any a-z`() {
        var createRestaurantDTO = CreateRestaurantDTOStub.getCreateRestaurantDTOStub().copy(
            password = "TEST123456"
        )

        val body = convertObjectToJsonBytes(createRestaurantDTO)

        val result = mvc.perform(
            post(RESTAURANTS_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .content(body)
        )

        result
            .andExpect(status().isBadRequest)
            .andReturn()

        assertThat(result.andReturn().resolvedException, instanceOf(MethodArgumentNotValidException::class.java))
    }

    @Test
    fun `createRestaurant_ password without any A-Z`() {
        var createRestaurantDTO = CreateRestaurantDTOStub.getCreateRestaurantDTOStub().copy(
            password = "test123456"
        )

        val body = convertObjectToJsonBytes(createRestaurantDTO)

        val result = mvc.perform(
            post(RESTAURANTS_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .content(body)
        )

        result
            .andExpect(status().isBadRequest)
            .andReturn()

        assertThat(result.andReturn().resolvedException, instanceOf(MethodArgumentNotValidException::class.java))
    }

    @Test
    fun `createRestaurant_ password without any 1-9`() {
        var createRestaurantDTO = CreateRestaurantDTOStub.getCreateRestaurantDTOStub().copy(
            password = "testTESTOWY"
        )

        val body = convertObjectToJsonBytes(createRestaurantDTO)

        val result = mvc.perform(
            post(RESTAURANTS_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .content(body)
        )

        result
            .andExpect(status().isBadRequest)
            .andReturn()

        assertThat(result.andReturn().resolvedException, instanceOf(MethodArgumentNotValidException::class.java))
    }

    @Test
    fun `createRestaurant_ correct data`() {
        var createRestaurantDTO = CreateRestaurantDTO(
            name = "New Restaurant",
            email = "newrestaurant@test.pl",
            type = RESTAURANT,
            password = "Test12345",
            phoneNumber = "111222333"
        )

        val body = convertObjectToJsonBytes(createRestaurantDTO)

        val result = mvc.perform(
            post(RESTAURANTS_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .content(body)
        )

        result
            .andExpect(status().isCreated)
            .andReturn()

        val createdUser = userRepository.findByEmail("newrestaurant@test.pl")
        val createdRestaurant = restaurantRepository.findPartlyByName("New Restaurant")[0]

        `createRestaurant assert restaurant`(createdRestaurant)
        `createRestaurant assert user`(createdUser, createdRestaurant)

    }

    private fun `createRestaurant assert restaurant`(createdRestaurant: Restaurant) {
        assertNotNull(createdRestaurant)
        assertEquals("New Restaurant", createdRestaurant.name)
        assertEquals("new.restaurant", createdRestaurant.urlName)
        assertEquals(RESTAURANT, createdRestaurant.type)
        assertNotNull(createdRestaurant.owner)
        assertEquals(1, createdRestaurant.owner?.id)
    }

    private fun `createRestaurant assert user`(createdUser: User, createdRestaurant: Restaurant) {
        assertNotNull(createdUser)
        assertEquals("newrestaurant@test.pl", createdUser.email)
        assertNotNull(createdUser.username)
        assertNotNull(createdUser.password)
        assertEquals(setOf(Authority(Authority.Role.ROLE_RESTAURANT)), createdUser.authorities)
        assertEquals(AccountState.ACTIVE, createdUser.accountState)
        assertEquals(createdRestaurant, createdUser.restaurant)
    }
}