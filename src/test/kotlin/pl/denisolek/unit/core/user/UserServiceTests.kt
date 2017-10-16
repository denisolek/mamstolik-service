package pl.denisolek.unit.core.user

import com.nhaarman.mockito_kotlin.mock
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.runners.MockitoJUnitRunner
import pl.denisolek.core.user.UserRepository
import pl.denisolek.core.user.UserService

@RunWith(MockitoJUnitRunner::class)
class UserServiceTests {

    @InjectMocks
    lateinit var userService: UserService

    @Mock
    private val userRepositoryMock = mock<UserRepository>()

    @Test
    fun `generateUsername_ should return correct username`() {
        `when`(userRepositoryMock.countByUsername("username")).thenReturn(0)
        val actual = userService.generateUsername()

        Assert.assertEquals(8, actual.length)
        Assert.assertTrue(actual.startsWith("ms"))
    }
}