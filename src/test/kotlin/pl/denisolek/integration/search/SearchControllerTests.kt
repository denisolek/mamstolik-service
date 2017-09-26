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
import pl.denisolek.infrastructure.convertJsonBytesToObject
import pl.denisolek.shared.search.SearchController
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

    @Before
    fun setup() {
        this.mvc = MockMvcBuilders.webAppContextSetup(applicationContext).build()
    }

    @Test
    fun `findCitiesAndRestaurants_ name = p`() {
        val url = SearchController.API.SEARCH_PATH

        val result = mvc.perform(get(url).param("name", "p"))
                .andExpect(MockMvcResultMatchers.status().isOk)
                .andReturn()

        val actual = convertJsonBytesToObject(result.response.contentAsString, CitiesRestaurantsDTO::class.java)

        val expected = CitiesRestaurantsDTO(
                cities = listOf(
                        City("Pogorzela", null),
                        City("Poręba", null),
                        City("Poznań", null),
                        City("Płock", null)),
                restaurants = listOf(
                        RestaurantSearchDTO(7, "Pasja"),
                        RestaurantSearchDTO(1, "Piano Bar Restaurant & Cafe"))
        )

        Assert.assertEquals(expected, actual)
    }

    @Test
    fun `findCitiesAndRestaurants_ name = po`() {
        val url = SearchController.API.SEARCH_PATH

        val result = mvc.perform(get(url).param("name", "po"))
                .andExpect(MockMvcResultMatchers.status().isOk)
                .andReturn()

        val actual = convertJsonBytesToObject(result.response.contentAsString, CitiesRestaurantsDTO::class.java)

        val expected = CitiesRestaurantsDTO(
                cities = listOf(
                        City("Pogorzela", null),
                        City("Poręba", null),
                        City("Poznań", null)),
                restaurants = listOf()
        )

        Assert.assertEquals(expected, actual)
    }

    @Test
    fun `findCitiesAndRestaurants_ name = gorzow`() {
        val url = SearchController.API.SEARCH_PATH

        val result = mvc.perform(get(url).param("name", "gorzow"))
                .andExpect(MockMvcResultMatchers.status().isOk)
                .andReturn()

        val actual = convertJsonBytesToObject(result.response.contentAsString, CitiesRestaurantsDTO::class.java)

        val expected = CitiesRestaurantsDTO(
                cities = listOf(
                        City("Gorzów Wielkopolski", null),
                        City("Gorzów Śląski", null)),
                restaurants = listOf()
        )

        Assert.assertEquals(expected, actual)
    }

    @Test
    fun `findCitiesAndRestaurants_ name = gw`() {
        val url = SearchController.API.SEARCH_PATH

        val result = mvc.perform(get(url).param("name", "gw"))
                .andExpect(MockMvcResultMatchers.status().isOk)
                .andReturn()

        val actual = convertJsonBytesToObject(result.response.contentAsString, CitiesRestaurantsDTO::class.java)

        val expected = CitiesRestaurantsDTO(
                cities = listOf(
                        City("Gorzów Wielkopolski", null)),
                restaurants = listOf()
        )

        Assert.assertEquals(expected, actual)
    }

    @Test
    fun `findCitiesAndRestaurants_ name = pzn`() {
        val url = SearchController.API.SEARCH_PATH

        val result = mvc.perform(get(url).param("name", "pzn"))
                .andExpect(MockMvcResultMatchers.status().isOk)
                .andReturn()

        val actual = convertJsonBytesToObject(result.response.contentAsString, CitiesRestaurantsDTO::class.java)

        val expected = CitiesRestaurantsDTO(
                cities = listOf(
                        City("Poznań", null)),
                restaurants = listOf()
        )

        Assert.assertEquals(expected, actual)
    }

    @Test
    fun `findCitiesAndRestaurants_ name = man`() {
        val url = SearchController.API.SEARCH_PATH

        val result = mvc.perform(get(url).param("name", "man"))
                .andExpect(MockMvcResultMatchers.status().isOk)
                .andReturn()

        val actual = convertJsonBytesToObject(result.response.contentAsString, CitiesRestaurantsDTO::class.java)

        val expected = CitiesRestaurantsDTO(
                cities = listOf(),
                restaurants = listOf(RestaurantSearchDTO(3, "Manekin"))
        )

        Assert.assertEquals(expected, actual)
    }
}