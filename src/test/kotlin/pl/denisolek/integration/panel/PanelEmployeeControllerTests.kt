package pl.denisolek.integration.panel

import org.apache.commons.lang3.RandomStringUtils.randomAlphanumeric
import org.hamcrest.Matchers.instanceOf
import org.junit.Assert
import org.junit.Assert.assertThat
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.SpyBean
import org.springframework.http.MediaType
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.test.web.servlet.setup.DefaultMockMvcBuilder
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.context.WebApplicationContext
import pl.denisolek.Exception.ServiceException
import pl.denisolek.core.security.Authority
import pl.denisolek.core.security.Authority.Role.ROLE_EMPLOYEE
import pl.denisolek.core.security.Authority.Role.ROLE_MANAGER
import pl.denisolek.core.user.UserRepository
import pl.denisolek.infrastructure.PANEL_BASE_PATH
import pl.denisolek.infrastructure.config.security.AuthorizationService
import pl.denisolek.infrastructure.util.convertObjectToJsonBytes
import pl.denisolek.panel.employee.DTO.CreateEmployeeDTO
import pl.denisolek.panel.employee.PanelEmployeeApi
import pl.denisolek.stubs.dto.CreateEmployeeDTOStub
import javax.transaction.Transactional

@RunWith(SpringRunner::class)
@ActiveProfiles("test", "fakeAuthorization")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Transactional
class PanelEmployeeControllerTests {
    @SpyBean
    lateinit var authorizationService: AuthorizationService

    @Autowired
    lateinit var userRepository: UserRepository

    @Autowired
    lateinit var applicationContext: WebApplicationContext

    lateinit var mvc: MockMvc

    val EMPLOYEES_PATH = "$PANEL_BASE_PATH${PanelEmployeeApi.EMPLOYEES_PATH}"
    val EMPLOYEES_ID_PATH = "$PANEL_BASE_PATH${PanelEmployeeApi.EMPLOYEES_ID_PATH}"

    @Before
    fun setup() {
        this.mvc = MockMvcBuilders
            .webAppContextSetup(applicationContext)
            .apply<DefaultMockMvcBuilder>(SecurityMockMvcConfigurers.springSecurity())
            .build()
    }

    @Test
    fun `addEmployee_ email is empty`() {
        var employeeDTO = CreateEmployeeDTOStub.getCreateEmployeeDTO()
        employeeDTO.email = ""

        val body = convertObjectToJsonBytes(employeeDTO)

        val result = mvc.perform(
            post(EMPLOYEES_PATH, 1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(body)
        )

        result.andExpect(status().isBadRequest)
    }

    @Test
    fun `addEmployee_ email wrong format`() {
        var employeeDTO = CreateEmployeeDTOStub.getCreateEmployeeDTO()
        employeeDTO.email = "test.test.pl"

        val body = convertObjectToJsonBytes(employeeDTO)

        val result = mvc.perform(
            post(EMPLOYEES_PATH, 1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(body)
        )

        result.andExpect(status().isBadRequest)
    }

    @Test
    fun `addEmployee_ email too long`() {
        var employeeDTO = CreateEmployeeDTOStub.getCreateEmployeeDTO()
        employeeDTO.email = "${randomAlphanumeric(100)}@test.pl"

        val body = convertObjectToJsonBytes(employeeDTO)

        val result = mvc.perform(
            post(EMPLOYEES_PATH, 1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(body)
        )

        result.andExpect(status().isBadRequest)
    }

    @Test
    fun `addEmployee_ firstName is empty`() {
        var employeeDTO = CreateEmployeeDTOStub.getCreateEmployeeDTO()
        employeeDTO.firstName = ""

        val body = convertObjectToJsonBytes(employeeDTO)

        val result = mvc.perform(
            post(EMPLOYEES_PATH, 1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(body)
        )

        result.andExpect(status().isBadRequest)
    }

    @Test
    fun `addEmployee_ firstName is not valid`() {
        var employeeDTO = CreateEmployeeDTOStub.getCreateEmployeeDTO()
        employeeDTO.firstName = "123Test"

        val body = convertObjectToJsonBytes(employeeDTO)

        val result = mvc.perform(
            post(EMPLOYEES_PATH, 1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(body)
        )

        result.andExpect(status().isBadRequest)
    }

    @Test
    fun `addEmployee_ lastName is empty`() {
        var employeeDTO = CreateEmployeeDTOStub.getCreateEmployeeDTO()
        employeeDTO.lastName = ""

        val body = convertObjectToJsonBytes(employeeDTO)

        val result = mvc.perform(
            post(EMPLOYEES_PATH, 1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(body)
        )

        result.andExpect(status().isBadRequest)
    }

    @Test
    fun `addEmployee_ lastName is not valid`() {
        var employeeDTO = CreateEmployeeDTOStub.getCreateEmployeeDTO()
        employeeDTO.lastName = "123Test"

        val body = convertObjectToJsonBytes(employeeDTO)

        val result = mvc.perform(
            post(EMPLOYEES_PATH, 1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(body)
        )

        result.andExpect(status().isBadRequest)
    }

    @Test
    fun `addEmployee_ phoneNumber is empty`() {
        var employeeDTO = CreateEmployeeDTOStub.getCreateEmployeeDTO()
        employeeDTO.phoneNumber = ""

        val body = convertObjectToJsonBytes(employeeDTO)

        val result = mvc.perform(
            post(EMPLOYEES_PATH, 1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(body)
        )

        result.andExpect(status().isBadRequest)
    }

    @Test
    fun `addEmployee_ phoneNumber with letters`() {
        var employeeDTO = CreateEmployeeDTOStub.getCreateEmployeeDTO()
        employeeDTO.phoneNumber = "111222ccc"

        val body = convertObjectToJsonBytes(employeeDTO)

        val result = mvc.perform(
            post(EMPLOYEES_PATH, 1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(body)
        )

        result.andExpect(status().isBadRequest)
    }

    @Test
    fun `addEmployee_ phoneNumber wrong length`() {
        var employeeDTO = CreateEmployeeDTOStub.getCreateEmployeeDTO()
        employeeDTO.phoneNumber = "111 222 33"

        val body = convertObjectToJsonBytes(employeeDTO)

        val result = mvc.perform(
            post(EMPLOYEES_PATH, 1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(body)
        )

        result.andExpect(status().isBadRequest)
    }

    @Test
    fun `addEmployee_ phoneNumber with not allowed chars`() {
        var employeeDTO = CreateEmployeeDTOStub.getCreateEmployeeDTO()
        employeeDTO.phoneNumber = "+48 111*222*333"

        val body = convertObjectToJsonBytes(employeeDTO)

        val result = mvc.perform(
            post(EMPLOYEES_PATH, 1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(body)
        )

        result.andExpect(status().isBadRequest)
    }

    @Test
    fun `addEmployee_ pin is null`() {
        var employeeDTO = CreateEmployeeDTOStub.getCreateEmployeeDTO().copy(
            pin = null
        )

        val body = convertObjectToJsonBytes(employeeDTO)

        val result = mvc.perform(
            post(EMPLOYEES_PATH, 1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(body)
        )

        result
            .andExpect(status().isBadRequest)
            .andReturn()

        assertThat(result.andReturn().resolvedException, instanceOf(ServiceException::class.java))
    }

    @Test
    fun `addEmployee_ pin too short`() {
        var employeeDTO = CreateEmployeeDTOStub.getCreateEmployeeDTO().copy(
            pin = "111"
        )

        val body = convertObjectToJsonBytes(employeeDTO)

        val result = mvc.perform(
            post(EMPLOYEES_PATH, 1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(body)
        )

        result
            .andExpect(status().isBadRequest)
            .andReturn()

        assertThat(result.andReturn().resolvedException, instanceOf(MethodArgumentNotValidException::class.java))
    }

    @Test
    fun `addEmployee_ pin too long`() {
        var employeeDTO = CreateEmployeeDTOStub.getCreateEmployeeDTO().copy(
            pin = "11111111111"
        )

        val body = convertObjectToJsonBytes(employeeDTO)

        val result = mvc.perform(
            post(EMPLOYEES_PATH, 1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(body)
        )

        result
            .andExpect(status().isBadRequest)
            .andReturn()

        assertThat(result.andReturn().resolvedException, instanceOf(MethodArgumentNotValidException::class.java))
    }

    @Test
    fun `addEmployee_ pin with a-z`() {
        var employeeDTO = CreateEmployeeDTOStub.getCreateEmployeeDTO().copy(
            pin = "1111a"
        )

        val body = convertObjectToJsonBytes(employeeDTO)

        val result = mvc.perform(
            post(EMPLOYEES_PATH, 1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(body)
        )

        result
            .andExpect(status().isBadRequest)
            .andReturn()

        assertThat(result.andReturn().resolvedException, instanceOf(MethodArgumentNotValidException::class.java))
    }

    @Test
    fun `addEmployee_ pin with A-Z`() {
        var employeeDTO = CreateEmployeeDTOStub.getCreateEmployeeDTO().copy(
            pin = "testA"
        )

        val body = convertObjectToJsonBytes(employeeDTO)

        val result = mvc.perform(
            post(EMPLOYEES_PATH, 1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(body)
        )

        result
            .andExpect(status().isBadRequest)
            .andReturn()

        assertThat(result.andReturn().resolvedException, instanceOf(MethodArgumentNotValidException::class.java))
    }

    @Test
    fun `addEmployee_ pin with special characters`() {
        var employeeDTO = CreateEmployeeDTOStub.getCreateEmployeeDTO().copy(
            pin = "11:($"
        )

        val body = convertObjectToJsonBytes(employeeDTO)

        val result = mvc.perform(
            post(EMPLOYEES_PATH, 1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(body)
        )

        result
            .andExpect(status().isBadRequest)
            .andReturn()

        assertThat(result.andReturn().resolvedException, instanceOf(MethodArgumentNotValidException::class.java))
    }

    @Test
    fun `addEmployee_ correctData employee`() {
        val employeeDTO = CreateEmployeeDTO(
            firstName = "TestName",
            lastName = "TestSurname",
            email = "testemail@test.pl",
            phoneNumber = "111222333",
            pin = "1111"
        )
        val body = convertObjectToJsonBytes(employeeDTO)

        val result = mvc.perform(
            post(EMPLOYEES_PATH, 1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(body)
        )

        result.andExpect(MockMvcResultMatchers.status().isCreated)

        val createdUser = userRepository.findAll().last()
        Assert.assertEquals("TestName", createdUser.firstName)
        Assert.assertEquals("TestSurname", createdUser.lastName)
        Assert.assertEquals("testemail@test.pl", createdUser.workEmail)
        Assert.assertEquals("111222333", createdUser.phoneNumber)
        Assert.assertEquals(setOf(Authority(ROLE_EMPLOYEE)), createdUser.authorities)
        Assert.assertNotNull(createdUser.password)
        Assert.assertNotNull(createdUser.email)
        Assert.assertNotNull(createdUser.username)
        Assert.assertNotNull(createdUser.workPlace)
    }

    @Test
    fun `addEmployee_ correctData manager`() {
        val employeeDTO = CreateEmployeeDTO(
            firstName = "TestNameMan",
            lastName = "TestSurnameMan",
            email = "testemailMan@test.pl",
            phoneNumber = "111222444",
            pin = "2222",
            isManager = true
        )
        val body = convertObjectToJsonBytes(employeeDTO)

        val result = mvc.perform(
            post(EMPLOYEES_PATH, 1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(body)
        )

        result.andExpect(MockMvcResultMatchers.status().isCreated)

        val createdUser = userRepository.findAll().last()
        Assert.assertEquals("TestNameMan", createdUser.firstName)
        Assert.assertEquals("TestSurnameMan", createdUser.lastName)
        Assert.assertEquals("testemailman@test.pl", createdUser.workEmail)
        Assert.assertEquals("111222444", createdUser.phoneNumber)
        Assert.assertEquals(setOf(Authority(ROLE_EMPLOYEE), Authority(ROLE_MANAGER)), createdUser.authorities)
        Assert.assertNotNull(createdUser.password)
        Assert.assertNotNull(createdUser.email)
        Assert.assertNotNull(createdUser.username)
        Assert.assertNotNull(createdUser.workPlace)
    }

    @Test
    fun `updateEmployee_ correctData`() {
        val employeeDTO = CreateEmployeeDTO(
            firstName = "TestNameMan",
            lastName = "TestSurnameMan",
            email = "testemailMan@test.pl",
            phoneNumber = "111222444",
            pin = "2222",
            isManager = true
        )
        val body = convertObjectToJsonBytes(employeeDTO)

        val result = mvc.perform(
            put(EMPLOYEES_ID_PATH, 1, 12)
                .contentType(MediaType.APPLICATION_JSON)
                .content(body)
        )

        result.andExpect(MockMvcResultMatchers.status().isOk)

        val createdUser = userRepository.findOne(12)
        Assert.assertEquals("TestNameMan", createdUser.firstName)
        Assert.assertEquals("TestSurnameMan", createdUser.lastName)
        Assert.assertEquals("testemailman@test.pl", createdUser.workEmail)
        Assert.assertEquals("111222444", createdUser.phoneNumber)
        Assert.assertEquals(setOf(Authority(ROLE_EMPLOYEE), Authority(ROLE_MANAGER)), createdUser.authorities)
        Assert.assertNotNull(createdUser.password)
        Assert.assertNotNull(createdUser.email)
        Assert.assertNotNull(createdUser.username)
        Assert.assertNotNull(createdUser.workPlace)
    }
}