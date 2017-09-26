package pl.denisolek.repository

import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.transaction.annotation.Transactional
import pl.denisolek.core.address.CityRepository

@RunWith(SpringRunner::class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@Transactional
class CityRepositoryTests {

    @Autowired
    lateinit var cityRepository: CityRepository

    @Test
    fun `findPartlyByNameOrAlias_ find by alias, name = pzn`() {
        val result = cityRepository.findPartlyByNameOrAlias("pzn")
        Assert.assertEquals(1, result.size)
        Assert.assertEquals("Poznań", result[0].name)
    }

    @Test
    fun `findPartlyByNameOrAlias_ find by alias, name = gw`() {
        val result = cityRepository.findPartlyByNameOrAlias("gw")
        Assert.assertEquals(1, result.size)
        Assert.assertEquals("Gorzów Wielkopolski", result[0].name)
    }

    @Test
    fun `findPartlyByNameOrAlias_ find by name, name = go`() {
        val expectedCities = listOf("Gorzów Śląski", "Gorzów Wielkopolski", "Gostyń", "Gostynin")
        val result = cityRepository.findPartlyByNameOrAlias("go")
        Assert.assertEquals(4, result.size)
        Assert.assertEquals(true, expectedCities.contains(result[0].name))
        Assert.assertEquals(true, expectedCities.contains(result[1].name))
        Assert.assertEquals(true, expectedCities.contains(result[2].name))
        Assert.assertEquals(true, expectedCities.contains(result[3].name))
    }
}