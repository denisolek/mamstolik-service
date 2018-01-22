package pl.denisolek.unit.panel

import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.times
import com.nhaarman.mockito_kotlin.verify
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentCaptor
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.runners.MockitoJUnitRunner
import org.springframework.security.crypto.password.PasswordEncoder
import pl.denisolek.Exception.ServiceException
import pl.denisolek.core.address.CityService
import pl.denisolek.core.email.EmailService
import pl.denisolek.core.restaurant.RestaurantService
import pl.denisolek.core.security.Authority
import pl.denisolek.core.user.User
import pl.denisolek.core.user.UserService
import pl.denisolek.infrastructure.config.security.AuthorizationService
import pl.denisolek.panel.identity.IdentityService
import pl.denisolek.stubs.RestaurantStub
import pl.denisolek.stubs.UserStub
import pl.denisolek.stubs.dto.ChangePasswordDTOStub
import pl.denisolek.stubs.dto.LostPasswordDTOStub
import pl.denisolek.stubs.dto.ResetPasswordDTOStub
import pl.denisolek.stubs.dto.SetPasswordDTOStub

@RunWith(MockitoJUnitRunner::class)
class IdentityServiceTests {
    @InjectMocks
    lateinit var identityService: IdentityService

    @Mock
    private val userServiceMock = mock<UserService>()

    @Mock
    private val emailServiceMock = mock<EmailService>()

    @Mock
    private val passwordEncoderMock = mock<PasswordEncoder>()

    @Mock
    private val authorizationService = mock<AuthorizationService>()

    @Mock
    private val restaurantService = mock<RestaurantService>()

    @Mock
    private val cityService = mock<CityService>()

    @Test
    fun `resendActivationKey_ wrong email`() {
        Mockito.`when`(userServiceMock.findByEmail("test@test.pl")).thenReturn(null)
        identityService.resendActivationKey("test@test.pl")
        verify(emailServiceMock, times(0)).registerOwner(any())
    }

    @Test
    fun `setPassword_ correct data`() {
        val setPasswordDTO = SetPasswordDTOStub.getSetPasswordDTO().copy(
                activationKey = "activationKeyStub"
        )
        val expectedUser = UserStub.getSetPasswordUser()
        Mockito.`when`(userServiceMock.findByUsername(setPasswordDTO.username)).thenReturn(expectedUser)
        identityService.setPassword(setPasswordDTO)
        verify(passwordEncoderMock, times(1)).encode("TestPassword123")
        verify(userServiceMock, times(1)).save(any())
    }

    @Test
    fun `changePassword_ correct data`() {
        val changePasswordDTO = ChangePasswordDTOStub.getChangePasswordDTO()
        val expectedUser = UserStub.getChangePasswordUser()
        Mockito.`when`(authorizationService.getCurrentUser()).thenReturn(expectedUser)
        Mockito.`when`(passwordEncoderMock.matches(any(), any())).thenReturn(true)
        identityService.changePassword(changePasswordDTO)
        verify(passwordEncoderMock, times(1)).matches(changePasswordDTO.oldPassword, "\$2a\$10\$IlfSzDHKiu5oOmuXVLmrXO.wAeWdK2dpmcbGHZZ1mOSKkzP/QF3uG")
        verify(passwordEncoderMock, times(1)).encode(changePasswordDTO.newPassword)
        verify(userServiceMock, times(1)).save(any())
    }

    @Test
    fun `lostPassword_`() {
        val lostPasswordDTO = LostPasswordDTOStub.getLostPasswordDTO()
        val expectedUser = UserStub.getUserOwner()
        Mockito.`when`(userServiceMock.findByEmail(any())).thenReturn(expectedUser)

        identityService.lostPassword(lostPasswordDTO)
        verify(passwordEncoderMock, times(1)).encode(any())
        verify(userServiceMock, times(1)).save(any())
    }

    @Test(expected = ServiceException::class)
    fun `resetPassword_ wrong username`() {
        val resetPasswordDTO = ResetPasswordDTOStub.getResetPasswordDTO()
        Mockito.`when`(userServiceMock.findByUsername(any())).thenReturn(null)
        identityService.resetPassword(resetPasswordDTO)
    }

    @Test(expected = ServiceException::class)
    fun `resetPassword_ key doesn't match`() {
        val resetPasswordDTO = ResetPasswordDTOStub.getResetPasswordDTO()
        val expectedUser = UserStub.getUserOwner()
        Mockito.`when`(userServiceMock.findByUsername(any())).thenReturn(expectedUser)
        Mockito.`when`(passwordEncoderMock.matches(any(), any())).thenReturn(false)
        identityService.resetPassword(resetPasswordDTO)
    }

    fun `resetPassword_ user not active`() {
        val resetPasswordDTO = ResetPasswordDTOStub.getResetPasswordDTO()
        val expectedUser = UserStub.getUserOwner().copy(accountState = User.AccountState.NOT_ACTIVE)
        Mockito.`when`(userServiceMock.findByUsername(any())).thenReturn(expectedUser)
        Mockito.`when`(passwordEncoderMock.matches(any(), any())).thenReturn(true)

        identityService.resetPassword(resetPasswordDTO)

        val savedUser = ArgumentCaptor.forClass(User::class.java)

        verify(passwordEncoderMock, times(1)).encode(any())
        verify(userServiceMock, times(1)).save(savedUser.capture())

        Assert.assertEquals(User.AccountState.ACTIVE, savedUser.value.accountState)
        Assert.assertEquals(null, savedUser.value.resetPasswordKey)
        Assert.assertEquals(null, savedUser.value.activationKey)
        Assert.assertNotNull(savedUser.value.password)
    }

    fun `resetPassword_ user already active`() {
        val resetPasswordDTO = ResetPasswordDTOStub.getResetPasswordDTO()
        val expectedUser = UserStub.getUserOwner()
        Mockito.`when`(userServiceMock.findByUsername(any())).thenReturn(expectedUser)
        Mockito.`when`(passwordEncoderMock.matches(any(), any())).thenReturn(true)

        identityService.resetPassword(resetPasswordDTO)

        val savedUser = ArgumentCaptor.forClass(User::class.java)

        verify(passwordEncoderMock, times(1)).encode(any())
        verify(userServiceMock, times(1)).save(savedUser.capture())

        Assert.assertEquals(User.AccountState.ACTIVE, savedUser.value.accountState)
        Assert.assertEquals(null, savedUser.value.resetPasswordKey)
        Assert.assertEquals(null, savedUser.value.activationKey)
        Assert.assertNotNull(savedUser.value.password)
    }

    fun `resetPassword_ banned user`() {
        val resetPasswordDTO = ResetPasswordDTOStub.getResetPasswordDTO()
        val expectedUser = UserStub.getUserOwner().copy(accountState = User.AccountState.BANNED)
        Mockito.`when`(userServiceMock.findByUsername(any())).thenReturn(expectedUser)
        Mockito.`when`(passwordEncoderMock.matches(any(), any())).thenReturn(true)

        identityService.resetPassword(resetPasswordDTO)

        val savedUser = ArgumentCaptor.forClass(User::class.java)

        verify(passwordEncoderMock, times(1)).encode(any())
        verify(userServiceMock, times(1)).save(savedUser.capture())

        Assert.assertEquals(User.AccountState.BANNED, savedUser.value.accountState)
        Assert.assertEquals(null, savedUser.value.resetPasswordKey)
        Assert.assertEquals(null, savedUser.value.activationKey)
        Assert.assertNotNull(savedUser.value.password)
    }

    @Test
    fun `getRestaurants_ no restaurants`() {
        val expectedUser = UserStub.getUserOwner()
        Mockito.`when`(authorizationService.getCurrentUser()).thenReturn(expectedUser)

        val restaurants = identityService.getRestaurants()
        Assert.assertEquals(0, restaurants.size)
    }

    @Test
    fun `getRestaurants_ one restaurant`() {
        val expectedUser = UserStub.getUserOwner()
        expectedUser.ownedRestaurants.add(RestaurantStub.getRestaurant().copy(id = 1, name = "NameStub1"))
        Mockito.`when`(authorizationService.getCurrentUser()).thenReturn(expectedUser)

        val restaurants = identityService.getRestaurants()
        Assert.assertEquals(1, restaurants.size)
        Assert.assertEquals(1, restaurants[0].id)
        Assert.assertEquals("NameStub1", restaurants[0].name)
    }

    @Test
    fun `getRestaurants_ many restaurants`() {
        val expectedUser = UserStub.getUserOwner()
        expectedUser.ownedRestaurants.add(RestaurantStub.getRestaurant().copy(id = 1, name = "NameStub1"))
        expectedUser.ownedRestaurants.add(RestaurantStub.getRestaurant().copy(id = 2, name = "NameStub2"))
        Mockito.`when`(authorizationService.getCurrentUser()).thenReturn(expectedUser)

        val restaurants = identityService.getRestaurants()
        Assert.assertEquals(2, restaurants.size)
        Assert.assertEquals(1, restaurants[0].id)
        Assert.assertEquals("NameStub1", restaurants[0].name)
        Assert.assertEquals(2, restaurants[1].id)
        Assert.assertEquals("NameStub2", restaurants[1].name)
    }

    @Test
    fun `getEmployees_ no employees`() {
        val expectedUser = UserStub.getUserRestaurant()
        expectedUser.restaurant?.employees = mutableListOf()
        Mockito.`when`(authorizationService.getCurrentUser()).thenReturn(expectedUser)

        val employees = identityService.getEmployees()
        Assert.assertEquals(1, employees.size)
        Assert.assertEquals("msOwner", employees[0].username)
        Assert.assertEquals(Authority.Role.ROLE_OWNER, employees[0].role)
    }

    @Test
    fun `getEmployees_ one employee`() {
        val expectedUser = UserStub.getUserRestaurant()
        expectedUser.restaurant?.employees?.removeIf { it.id != 10 }
        Mockito.`when`(authorizationService.getCurrentUser()).thenReturn(expectedUser)

        val employees = identityService.getEmployees()
        Assert.assertEquals(2, employees.size)
        Assert.assertEquals(10, employees[0].id)
        Assert.assertEquals("Stub", employees[0].firstName)
        Assert.assertEquals("One", employees[0].lastName)
        Assert.assertEquals("StubOne", employees[0].username)
        Assert.assertEquals(Authority.Role.ROLE_EMPLOYEE, employees[0].role)
        Assert.assertEquals(null, employees[0].avatar)
        Assert.assertEquals(1, employees[1].id)
        Assert.assertEquals("firstName", employees[1].firstName)
        Assert.assertEquals("lastName", employees[1].lastName)
        Assert.assertEquals("msOwner", employees[1].username)
        Assert.assertEquals(Authority.Role.ROLE_OWNER, employees[1].role)
        Assert.assertEquals(null, employees[1].avatar)
    }

    @Test
    fun `getEmployees_ many employees`() {
        val expectedUser = UserStub.getUserRestaurant()
        Mockito.`when`(authorizationService.getCurrentUser()).thenReturn(expectedUser)

        val employees = identityService.getEmployees()
        Assert.assertEquals(5, employees.size)
        Assert.assertEquals(10, employees[0].id)
        Assert.assertEquals("Stub", employees[0].firstName)
        Assert.assertEquals("One", employees[0].lastName)
        Assert.assertEquals(20, employees[1].id)
        Assert.assertEquals("Stub", employees[1].firstName)
        Assert.assertEquals("Two", employees[1].lastName)
        Assert.assertEquals(30, employees[2].id)
        Assert.assertEquals("Stub", employees[2].firstName)
        Assert.assertEquals("Three", employees[2].lastName)
        Assert.assertEquals(40, employees[3].id)
        Assert.assertEquals("Stub", employees[3].firstName)
        Assert.assertEquals("Four", employees[3].lastName)
        Assert.assertEquals(1, employees[4].id)
        Assert.assertEquals("firstName", employees[4].firstName)
        Assert.assertEquals("lastName", employees[4].lastName)
    }
}