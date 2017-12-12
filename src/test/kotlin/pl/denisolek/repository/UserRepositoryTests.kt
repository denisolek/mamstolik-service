package pl.denisolek.repository

import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.transaction.annotation.Transactional
import pl.denisolek.core.user.UserRepository

@RunWith(SpringRunner::class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@Transactional
class UserRepositoryTests {

    @Autowired
    lateinit var userRepository: UserRepository

    @Test
    fun `findByUrlName correct data`() {
        val result = userRepository.findByUrlName("piano.bar.restaurant.&.cafe")
        Assert.assertEquals("ms100001", result?.username)
    }
}