package pl.denisolek.integration.panel

import com.nhaarman.mockito_kotlin.doReturn
import com.nhaarman.mockito_kotlin.whenever
import org.hamcrest.Matchers
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.SpyBean
import org.springframework.http.MediaType
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.test.web.servlet.setup.DefaultMockMvcBuilder
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.web.context.WebApplicationContext
import pl.denisolek.Exception.ServiceException
import pl.denisolek.core.restaurant.RestaurantRepository
import pl.denisolek.core.schema.Floor
import pl.denisolek.core.user.UserRepository
import pl.denisolek.infrastructure.PANEL_BASE_PATH
import pl.denisolek.infrastructure.config.security.AuthorizationService
import pl.denisolek.infrastructure.util.convertJsonBytesToObject
import pl.denisolek.infrastructure.util.convertObjectToJsonBytes
import pl.denisolek.panel.schema.DTO.FloorDTO
import pl.denisolek.panel.schema.DTO.SchemaDTO
import pl.denisolek.panel.schema.PanelSchemaApi
import javax.transaction.Transactional

@RunWith(SpringRunner::class)
@ActiveProfiles("test", "fakeAuthorization")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Transactional
class PanelSchemaControllerTests {
    @SpyBean
    lateinit var authorizationService: AuthorizationService

    @Autowired
    lateinit var userRepository: UserRepository

    @Autowired
    lateinit var restaurantRepository: RestaurantRepository

    @Autowired
    lateinit var applicationContext: WebApplicationContext

    lateinit var mvc: MockMvc

    val SCHEMA_PATH = "${PANEL_BASE_PATH}${PanelSchemaApi.SCHEMAS_PATH}"
    val FLOORS_PATH = "${PANEL_BASE_PATH}${PanelSchemaApi.FLOORS_PATH}"
    val FLOORS_ID_PATH = "${PANEL_BASE_PATH}${PanelSchemaApi.FLOORS_ID_PATH}"

    @Before
    fun setup() {
        this.mvc = MockMvcBuilders
                .webAppContextSetup(applicationContext)
                .apply<DefaultMockMvcBuilder>(SecurityMockMvcConfigurers.springSecurity())
                .build()
    }

    @Test
    fun `getSchema_`() {
        val user = userRepository.findOne(1)
        doReturn(user).whenever(authorizationService).getCurrentUser()
        val result = mvc.perform(get(SCHEMA_PATH, 1))
                .andExpect(status().isOk)
                .andReturn()

        val actual = convertJsonBytesToObject(result.response.contentAsString, SchemaDTO::class.java)

        assertEquals(3, actual.floors.size)
        assertEquals(22, actual.tables.size)
        assertEquals(15, actual.walls.size)
        assertEquals(1, actual.items.size)
        assertEquals(12, actual.wallItems.size)
    }

    @Test
    fun `addFloor_ correct data`() {
        val floorDTOStub = FloorDTO("Stubbed name")
        val body = convertObjectToJsonBytes(floorDTOStub)
        mvc.perform(post(FLOORS_PATH, 1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(body))
                .andExpect(status().isCreated)

        val restaurant = restaurantRepository.findOne(1)
        val expectedFloor = Floor(id = 4, name = "Stubbed name", restaurant = restaurant)
        assertTrue(restaurant.floors.contains(expectedFloor))
    }

    @Test
    fun `deleteFloor_ with reservations in future`() {
        val user = userRepository.findOne(1)
        doReturn(user).whenever(authorizationService).getCurrentUser()
        val result = mvc.perform(delete(FLOORS_ID_PATH, 1, 1))

        result
                .andExpect(status().isConflict)
                .andReturn()

        assertThat(result.andReturn().resolvedException, Matchers.instanceOf(ServiceException::class.java))
    }

    @Test
    fun `deleteFloor_ without reservations in future`() {
        val user = userRepository.findOne(1)
        doReturn(user).whenever(authorizationService).getCurrentUser()
        val result = mvc.perform(delete(FLOORS_ID_PATH, 1, 2))
                .andExpect(status().isOk)
                .andReturn()

        val actual = convertJsonBytesToObject(result.response.contentAsString, SchemaDTO::class.java)

        assertEquals(2, actual.floors.size)
        assertEquals(22, actual.tables.size)
        assertEquals(15, actual.walls.size)
        assertEquals(1, actual.items.size)
        assertEquals(12, actual.wallItems.size)
        actual.floors.map {
            assertTrue(it.id != 2)
        }
    }
}