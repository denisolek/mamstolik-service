package pl.denisolek.integration.panel

import com.nhaarman.mockito_kotlin.doReturn
import com.nhaarman.mockito_kotlin.whenever
import org.hamcrest.Matchers
import org.junit.Assert
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
import pl.denisolek.core.schema.SchemaItem
import pl.denisolek.core.user.UserRepository
import pl.denisolek.infrastructure.PANEL_BASE_PATH
import pl.denisolek.infrastructure.config.security.AuthorizationService
import pl.denisolek.infrastructure.util.convertJsonBytesToObject
import pl.denisolek.infrastructure.util.convertObjectToJsonBytes
import pl.denisolek.panel.schema.DTO.FloorDTO
import pl.denisolek.panel.schema.DTO.SchemaDTO
import pl.denisolek.panel.schema.DTO.type.SchemaDetailsDTO
import pl.denisolek.panel.schema.DTO.type.SchemaPositionDTO
import pl.denisolek.panel.schema.DTO.type.SchemaSpotInfoDTO
import pl.denisolek.panel.schema.DTO.type.TypeTableDTO
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

    val SCHEMAS_PATH = "${PANEL_BASE_PATH}${PanelSchemaApi.SCHEMAS_PATH}"
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
        val result = mvc.perform(get(SCHEMAS_PATH, 1))
                .andExpect(status().isOk)
                .andReturn()

        val actual = convertJsonBytesToObject(result.response.contentAsString, SchemaDTO::class.java)

        assertEquals(3, actual.floors!!.size)
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

        assertEquals(2, actual.floors!!.size)
        assertEquals(22, actual.tables.size)
        assertEquals(15, actual.walls.size)
        assertEquals(1, actual.items.size)
        assertEquals(12, actual.wallItems.size)
        actual.floors!!.map {
            assertTrue(it.id != 2)
        }
    }

    @Test
    fun `updateSchema_ update existing item - floorId`() {
        val schemaDTO = prepareUpdateSchemaDTO()
        schemaDTO.tables[0].floorId = 2

        val body = convertObjectToJsonBytes(schemaDTO)

        val result = mvc.perform(put(SCHEMAS_PATH, 1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(body))
                .andExpect(status().isOk)
                .andReturn()

        val actual = convertJsonBytesToObject(result.response.contentAsString, SchemaDTO::class.java)
        Assert.assertEquals(1, actual.tables.last().id)
        Assert.assertEquals(2, actual.tables.last().floorId)
    }

    @Test
    fun `updateSchema_ update existing item - floorId not existing`() {
        val schemaDTO = prepareUpdateSchemaDTO()
        schemaDTO.tables[0].floorId = 100

        val body = convertObjectToJsonBytes(schemaDTO)

        mvc.perform(put(SCHEMAS_PATH, 1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(body))
                .andExpect(status().isBadRequest)
    }

    @Test
    fun `updateSchema_ update existing item - subType`() {
        val schemaDTO = prepareUpdateSchemaDTO()
        schemaDTO.tables[0].subType = SchemaItem.TableType.EIGHT_ROUND

        val body = convertObjectToJsonBytes(schemaDTO)

        val result = mvc.perform(put(SCHEMAS_PATH, 1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(body))
                .andExpect(status().isOk)
                .andReturn()

        val actual = convertJsonBytesToObject(result.response.contentAsString, SchemaDTO::class.java)
        Assert.assertEquals(SchemaItem.TableType.EIGHT_ROUND, actual.tables[0].subType)
    }

    @Test
    fun `updateSchema_ update existing item - position`() {
        val schemaDTO = prepareUpdateSchemaDTO()
        schemaDTO.tables[0].position = SchemaPositionDTO(10000f, 10000f)

        val body = convertObjectToJsonBytes(schemaDTO)

        val result = mvc.perform(put(SCHEMAS_PATH, 1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(body))
                .andExpect(status().isOk)
                .andReturn()

        val actual = convertJsonBytesToObject(result.response.contentAsString, SchemaDTO::class.java)
        Assert.assertEquals(SchemaPositionDTO(10000f, 10000f), actual.tables[0].position)
    }

    @Test
    fun `updateSchema_ update existing item - details`() {
        val schemaDTO = prepareUpdateSchemaDTO()
        schemaDTO.tables[0].details = SchemaDetailsDTO(10000, 10000, 10000f)

        val body = convertObjectToJsonBytes(schemaDTO)

        val result = mvc.perform(put(SCHEMAS_PATH, 1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(body))
                .andExpect(status().isOk)
                .andReturn()

        val actual = convertJsonBytesToObject(result.response.contentAsString, SchemaDTO::class.java)
        Assert.assertEquals(SchemaDetailsDTO(10000, 10000, 10000f), actual.tables[0].details)
    }

    @Test
    fun `updateSchema_ update existing table - spotInfo`() {
        val schemaDTO = prepareUpdateSchemaDTO()
        schemaDTO.tables[0].spotInfo = SchemaSpotInfoDTO(5, 100, 4, 1)

        val body = convertObjectToJsonBytes(schemaDTO)

        val result = mvc.perform(put(SCHEMAS_PATH, 1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(body))
                .andExpect(status().isOk)
                .andReturn()

        val actual = convertJsonBytesToObject(result.response.contentAsString, SchemaDTO::class.java)
        Assert.assertEquals(SchemaSpotInfoDTO(1, 1, 4, 1), actual.tables[0].spotInfo)
    }

    @Test
    fun `updateSchema_ update not existing table when request tableId is not null and spotId is already existing post - spotInfo`() {
        val schemaDTO = prepareUpdateSchemaDTO()
        schemaDTO.tables[0].id = 312312
        schemaDTO.tables[0].spotInfo = SchemaSpotInfoDTO(5, 100, 4, 1)

        val body = convertObjectToJsonBytes(schemaDTO)

        mvc.perform(put(SCHEMAS_PATH, 1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(body))
                .andExpect(status().isBadRequest)
                .andReturn()
    }

    @Test
    fun `updateSchema_ add new table`() {
        val schemaDTO = prepareUpdateSchemaDTO()
        schemaDTO.tables[0].spotInfo = SchemaSpotInfoDTO(1, 100, 4, 1)

        val newTable = TypeTableDTO(
                floorId = 1,
                subType = SchemaItem.TableType.EIGHT_ROUND,
                position = SchemaPositionDTO(15000f, 15000f),
                details = SchemaDetailsDTO(15000, 15000, 15000f),
                spotInfo = SchemaSpotInfoDTO(
                        number = 15000,
                        capacity = 10,
                        minPeopleNumber = 5
                ))

        schemaDTO.tables.add(newTable)

        val body = convertObjectToJsonBytes(schemaDTO)

        val result = mvc.perform(put(SCHEMAS_PATH, 1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(body))
                .andExpect(status().isOk)
                .andReturn()

        val actual = convertJsonBytesToObject(result.response.contentAsString, SchemaDTO::class.java)
        Assert.assertNotNull(actual.tables.last().spotInfo.id)
        Assert.assertEquals(15000, actual.tables.last().spotInfo.number)
        Assert.assertEquals(10, actual.tables.last().spotInfo.capacity)
        Assert.assertEquals(5, actual.tables.last().spotInfo.minPeopleNumber)
    }

    private fun prepareUpdateSchemaDTO(): SchemaDTO {
        val user = userRepository.findOne(1)
        doReturn(user).whenever(authorizationService).getCurrentUser()
        val schemas = mvc.perform(get(SCHEMAS_PATH, 1))
                .andExpect(status().isOk)
                .andReturn()

        val schemaDTO = convertJsonBytesToObject(schemas.response.contentAsString, SchemaDTO::class.java)
        schemaDTO.floors = null
        return schemaDTO
    }

//    @Test
//    fun `updateSchema_ add new item`() {
//
//    }
//
//    @Test
//    fun `updateSchema_ remove item from schema (should not remove)`() {
//
//    }
}