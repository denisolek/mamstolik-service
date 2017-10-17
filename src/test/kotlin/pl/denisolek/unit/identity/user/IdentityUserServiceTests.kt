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
import pl.denisolek.core.email.EmailService
import pl.denisolek.core.user.UserService
import pl.denisolek.identity.user.IdentityUserService
import pl.denisolek.stubs.UserStub

@RunWith(MockitoJUnitRunner::class)
class IdentityUserServiceTests {
    @InjectMocks
    lateinit var identityUserService: IdentityUserService

    @Mock
    private val userServiceMock = mock<UserService>()

    @Mock
    private val emailServiceMock = mock<EmailService>()

    @Test
    fun `resendActivationKey_ wrong email`() {
        Mockito.`when`(userServiceMock.findByEmail("test@test.pl")).thenReturn(null)
        identityUserService.resendActivationKey("test@test.pl")
        verify(emailServiceMock, times(0)).registerOwner(any())
    }

    @Test
    fun `resendActivationKey_ correct email`() {
        val expectedUser = UserStub.getResendActivationKeyUser()
        Mockito.`when`(userServiceMock.findByEmail("emailStub@test.pl")).thenReturn(expectedUser)
        identityUserService.resendActivationKey("emailStub@test.pl")
        verify(emailServiceMock, times(1)).registerOwner(any())
    }
}