package pl.denisolek.repository

import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.transaction.annotation.Transactional
import pl.denisolek.core.restaurant.RestaurantRepository

@RunWith(SpringRunner::class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@Transactional
class RestaurantRepositoryTests {

    @Autowired
    lateinit var restaurantRepository: RestaurantRepository

    @Test
    fun `findPartlyByName_ name = man`() {
        val result = restaurantRepository.findPartlyByName("man")
        Assert.assertEquals(1, result.size)
    }

    @Test
    fun `findPartlyByName_ name = ra`() {
        val expectedRestaurants = listOf("Ratuszova", "Rapudei Berek")
        val result = restaurantRepository.findPartlyByName("ra")
        Assert.assertEquals(2, result.size)
        Assert.assertTrue(expectedRestaurants.contains(result[0].name))
        Assert.assertTrue(expectedRestaurants.contains(result[1].name))
    }

    @Test
    fun `findByCityAndIsActive_ active = true`() {
        val result = restaurantRepository.findByCityAndIsActive(3, true)
        Assert.assertEquals(1, result.size)
        Assert.assertEquals("Pasja", result[0].name)
    }

    @Test
    fun `findByCityAndIsActive_ active = false`() {
        val result = restaurantRepository.findByCityAndIsActive(3, false)
        Assert.assertEquals(0, result.size)
    }
}