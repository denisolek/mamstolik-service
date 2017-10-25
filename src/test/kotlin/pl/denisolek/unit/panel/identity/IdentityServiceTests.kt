package pl.denisolek.unit.panel.identity

import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.times
import com.nhaarman.mockito_kotlin.verify
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.runners.MockitoJUnitRunner
import org.springframework.security.crypto.password.PasswordEncoder
import pl.denisolek.core.email.EmailService
import pl.denisolek.core.user.UserService
import pl.denisolek.infrastructure.config.security.AuthorizationService
import pl.denisolek.panel.identity.IdentityService
import pl.denisolek.stubs.RestaurantStub
import pl.denisolek.stubs.UserStub
import pl.denisolek.stubs.dto.ChangePasswordDTOStub
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
    fun `getRestaurants_ no restaurants`() {
        val expectedUser = UserStub.getUserOwner()
        Mockito.`when`(authorizationService.getCurrentUser()).thenReturn(expectedUser)

        val restaurants = identityService.getRestaurants()
        Assert.assertEquals(0, restaurants.size)
    }

    @Test
    fun `getRestaurants_ one restaurant`() {
        val expectedUser = UserStub.getUserOwner()
        expectedUser.ownedRestaurants?.add(RestaurantStub.getRestaurant().copy(id = 1, name = "NameStub1"))
        Mockito.`when`(authorizationService.getCurrentUser()).thenReturn(expectedUser)

        val restaurants = identityService.getRestaurants()
        Assert.assertEquals(1, restaurants.size)
        Assert.assertEquals(1, restaurants[0].id)
        Assert.assertEquals("NameStub1", restaurants[0].name)
    }

    @Test
    fun `getRestaurants_ many restaurants`() {
        val expectedUser = UserStub.getUserOwner()
        expectedUser.ownedRestaurants?.add(RestaurantStub.getRestaurant().copy(id = 1, name = "NameStub1"))
        expectedUser.ownedRestaurants?.add(RestaurantStub.getRestaurant().copy(id = 2, name = "NameStub2"))
        Mockito.`when`(authorizationService.getCurrentUser()).thenReturn(expectedUser)

        val restaurants = identityService.getRestaurants()
        Assert.assertEquals(2, restaurants.size)
        Assert.assertEquals(1, restaurants[0].id)
        Assert.assertEquals("NameStub1", restaurants[0].name)
        Assert.assertEquals(2, restaurants[1].id)
        Assert.assertEquals("NameStub2", restaurants[1].name)
    }
}