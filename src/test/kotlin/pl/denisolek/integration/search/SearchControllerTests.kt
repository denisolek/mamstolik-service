package pl.denisolek.integration.search

import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.context.WebApplicationContext
import pl.denisolek.core.address.City
import pl.denisolek.infrastructure.API_BASE_PATH
import pl.denisolek.infrastructure.util.convertJsonBytesToObject
import pl.denisolek.shared.search.SearchApi
import pl.denisolek.shared.search.dto.CitiesRestaurantsDTO
import pl.denisolek.shared.search.dto.RestaurantSearchDTO

@RunWith(SpringRunner::class)
@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Transactional
class SearchControllerTests {

    @Autowired
    lateinit var applicationContext: WebApplicationContext

    lateinit var mvc: MockMvc

    val SEARCH_PATH = "$API_BASE_PATH/${SearchApi.SEARCH_PATH}"

    @Before
    fun setup() {
        this.mvc = MockMvcBuilders.webAppContextSetup(applicationContext).build()
    }

    @Test
    fun `findCitiesAndRestaurants_ name = p`() {
        val result = mvc.perform(get(SEARCH_PATH).param("name", "p"))
                .andExpect(MockMvcResultMatchers.status().isOk)
                .andReturn()

        val actual = convertJsonBytesToObject(result.response.contentAsString, CitiesRestaurantsDTO::class.java)

        val expected = CitiesRestaurantsDTO(
                cities = listOf(
                        City(name = "Pogorzela", aliases = null),
                        City(name = "Poręba", aliases = null),
                        City(name = "Poznań", aliases = null),
                        City(name = "Płock", aliases = null)),
                restaurants = listOf(
                        RestaurantSearchDTO(7, "Pasja", "pasja"),
                        RestaurantSearchDTO(1, "Piano Bar Restaurant & Cafe", "piano.bar.restaurant.&.cafe"))
        )

        Assert.assertEquals(expected, actual)
    }

    @Test
    fun `findCitiesAndRestaurants_ name = po`() {
        val result = mvc.perform(get(SEARCH_PATH).param("name", "po"))
                .andExpect(MockMvcResultMatchers.status().isOk)
                .andReturn()

        val actual = convertJsonBytesToObject(result.response.contentAsString, CitiesRestaurantsDTO::class.java)

        val expected = CitiesRestaurantsDTO(
                cities = listOf(
                        City(name = "Pogorzela", aliases = null),
                        City(name = "Poręba", aliases = null),
                        City(name = "Poznań", aliases = null)),
                restaurants = listOf()
        )

        Assert.assertEquals(expected, actual)
    }

    @Test
    fun `findCitiesAndRestaurants_ name = gorzow`() {
        val result = mvc.perform(get(SEARCH_PATH).param("name", "gorzow"))
                .andExpect(MockMvcResultMatchers.status().isOk)
                .andReturn()

        val actual = convertJsonBytesToObject(result.response.contentAsString, CitiesRestaurantsDTO::class.java)

        val expected = CitiesRestaurantsDTO(
                cities = listOf(
                        City(name = "Gorzów Wielkopolski", aliases = null),
                        City(name = "Gorzów Śląski", aliases = null)),
                restaurants = listOf()
        )

        Assert.assertEquals(expected, actual)
    }

    @Test
    fun `findCitiesAndRestaurants_ name = gw`() {
        val result = mvc.perform(get(SEARCH_PATH).param("name", "gw"))
                .andExpect(MockMvcResultMatchers.status().isOk)
                .andReturn()

        val actual = convertJsonBytesToObject(result.response.contentAsString, CitiesRestaurantsDTO::class.java)

        val expected = CitiesRestaurantsDTO(
                cities = listOf(
                        City(name = "Gorzów Wielkopolski", aliases = null)),
                restaurants = listOf()
        )

        Assert.assertEquals(expected, actual)
    }

    @Test
    fun `findCitiesAndRestaurants_ name = pzn`() {
        val result = mvc.perform(get(SEARCH_PATH).param("name", "pzn"))
                .andExpect(MockMvcResultMatchers.status().isOk)
                .andReturn()

        val actual = convertJsonBytesToObject(result.response.contentAsString, CitiesRestaurantsDTO::class.java)

        val expected = CitiesRestaurantsDTO(
                cities = listOf(
                        City(name = "Poznań", aliases = null)),
                restaurants = listOf()
        )

        Assert.assertEquals(expected, actual)
    }

    @Test
    fun `findCitiesAndRestaurants_ name = man`() {
        val result = mvc.perform(get(SEARCH_PATH).param("name", "man"))
                .andExpect(MockMvcResultMatchers.status().isOk)
                .andReturn()

        val actual = convertJsonBytesToObject(result.response.contentAsString, CitiesRestaurantsDTO::class.java)

        val expected = CitiesRestaurantsDTO(
                cities = listOf(),
                restaurants = listOf(RestaurantSearchDTO(3, "Manekin", "manekin"))
        )

        Assert.assertEquals(expected, actual)
    }
}