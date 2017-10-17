package pl.denisolek.unit.identity.user

import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.times
import com.nhaarman.mockito_kotlin.verify
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.runners.MockitoJUnitRunner
import org.springframework.security.crypto.password.PasswordEncoder
import pl.denisolek.core.email.EmailService
import pl.denisolek.core.user.UserService
import pl.denisolek.identity.user.IdentityUserService
import pl.denisolek.stubs.UserStub
import pl.denisolek.stubs.dto.SetPasswordDTOStub

@RunWith(MockitoJUnitRunner::class)
class IdentityUserServiceTests {
    @InjectMocks
    lateinit var identityUserService: IdentityUserService

    @Mock
    private val userServiceMock = mock<UserService>()

    @Mock
    private val emailServiceMock = mock<EmailService>()

    @Mock
    private val passwordEncoderMock = mock<PasswordEncoder>()

    @Test
    fun `resendActivationKey_ wrong email`() {
        Mockito.`when`(userServiceMock.findByEmail("test@test.pl")).thenReturn(null)
        identityUserService.resendActivationKey("test@test.pl")
        verify(emailServiceMock, times(0)).registerOwner(any())
    }

    @Test
    fun `setPassword_ correct data`() {
        val setPasswordDTO = SetPasswordDTOStub.getSetPasswordDTO().copy(
                activationKey = "activationKeyStub"
        )
        val expectedUser = UserStub.getSetPasswordUser()
        Mockito.`when`(userServiceMock.findByUsername(setPasswordDTO.username)).thenReturn(expectedUser)
        identityUserService.setPassword(setPasswordDTO)
        verify(passwordEncoderMock, times(1)).encode("TestPassword123")
        verify(userServiceMock, times(1)).save(any())
    }
}