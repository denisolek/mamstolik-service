package pl.denisolek.unit.panel

import com.nhaarman.mockito_kotlin.mock
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.runners.MockitoJUnitRunner
import org.springframework.security.crypto.password.PasswordEncoder
import pl.denisolek.core.user.UserService
import pl.denisolek.panel.employee.DTO.EmployeeDTO
import pl.denisolek.panel.employee.PanelEmployeeService
import pl.denisolek.stubs.UserStub

@RunWith(MockitoJUnitRunner::class)
class PanelEmployeeServiceTests {
    @InjectMocks
    lateinit var panelEmployeeService: PanelEmployeeService

    @Mock
    private val userServiceMock = mock<UserService>()

    @Mock
    private val passwordEncoderMock = mock<PasswordEncoder>()

    @Test
    fun `getEmployees_ `() {
        val restaurant = UserStub.getUserRestaurant().restaurant
        val employees = panelEmployeeService.getEmployees(restaurant!!)
        assertEmployeeDTO(
                createEmployee = employees[0],
                id = 10,
                email = "emailStub@test.pl",
                firstName = "Stub",
                lastName = "One",
                phoneNumber = "111000000")

        assertEmployeeDTO(
                createEmployee = employees[1],
                id = 20,
                email = "emailStub@test.pl",
                firstName = "Stub",
                lastName = "Two",
                phoneNumber = "222000000")

        assertEmployeeDTO(
                createEmployee = employees[2],
                id = 30,
                email = "emailStub@test.pl",
                firstName = "Stub",
                lastName = "Three",
                phoneNumber = "333000000")

        assertEmployeeDTO(
                createEmployee = employees[3],
                id = 40,
                email = "emailStub@test.pl",
                firstName = "Stub",
                lastName = "Four",
                phoneNumber = "444000000")
    }

    private fun assertEmployeeDTO(createEmployee: EmployeeDTO, id: Int, email: String, firstName: String, lastName: String, phoneNumber: String) {
        Assert.assertEquals(id, createEmployee.id)
        Assert.assertEquals(email, createEmployee.email)
        Assert.assertEquals(firstName, createEmployee.firstName)
        Assert.assertEquals(lastName, createEmployee.lastName)
        Assert.assertEquals(phoneNumber, createEmployee.phoneNumber)
    }
}