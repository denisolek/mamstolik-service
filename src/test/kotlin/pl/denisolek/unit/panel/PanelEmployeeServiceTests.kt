package pl.denisolek.unit.panel

import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.InjectMocks
import org.mockito.runners.MockitoJUnitRunner
import pl.denisolek.panel.employee.DTO.EmployeeDTO
import pl.denisolek.panel.employee.PanelEmployeeService
import pl.denisolek.stubs.UserStub

@RunWith(MockitoJUnitRunner::class)
class PanelEmployeeServiceTests {
    @InjectMocks
    lateinit var panelEmployeeService: PanelEmployeeService

    @Test
    fun `getEmployees_ `(){
        val restaurant = UserStub.getUserRestaurant().restaurant
        val employees = panelEmployeeService.getEmployees(restaurant!!)
        assertEmployeeDTO(
                employee = employees[0],
                id = 10,
                email = "emailStub@test.pl",
                firstName = "Stub",
                lastName = "One",
                phoneNumber = "111000000",
                pin = null)

        assertEmployeeDTO(
                employee = employees[1],
                id = 20,
                email = "emailStub@test.pl",
                firstName = "Stub",
                lastName = "Two",
                phoneNumber = "222000000",
                pin = null)

        assertEmployeeDTO(
                employee = employees[2],
                id = 30,
                email = "emailStub@test.pl",
                firstName = "Stub",
                lastName = "Three",
                phoneNumber = "333000000",
                pin = null)

        assertEmployeeDTO(
                employee = employees[3],
                id = 40,
                email = "emailStub@test.pl",
                firstName = "Stub",
                lastName = "Four",
                phoneNumber = "444000000",
                pin = null)
    }

    private fun assertEmployeeDTO(employee: EmployeeDTO, id: Int, email: String, firstName: String, lastName: String, phoneNumber: String, pin: String?) {
        Assert.assertEquals(id, employee.id)
        Assert.assertEquals(email, employee.email)
        Assert.assertEquals(firstName, employee.firstName)
        Assert.assertEquals(lastName, employee.lastName)
        Assert.assertEquals(phoneNumber, employee.phoneNumber)
        Assert.assertEquals(pin, employee.pin)
    }
}