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
import pl.denisolek.core.floor.Floor
import pl.denisolek.core.restaurant.RestaurantRepository
import pl.denisolek.core.schema.SchemaItem
import pl.denisolek.core.schema.SchemaItem.ItemType.TOILET
import pl.denisolek.core.schema.SchemaItem.TableType.EIGHT_ROUND
import pl.denisolek.core.schema.SchemaItem.WallItemType.WINDOW
import pl.denisolek.core.user.UserRepository
import pl.denisolek.guest.restaurant.GuestRestaurantApi
import pl.denisolek.infrastructure.API_BASE_PATH
import pl.denisolek.infrastructure.PANEL_BASE_PATH
import pl.denisolek.infrastructure.config.security.AuthorizationService
import pl.denisolek.infrastructure.util.convertJsonBytesToObject
import pl.denisolek.infrastructure.util.convertObjectToJsonBytes
import pl.denisolek.panel.schema.DTO.FloorDTO
import pl.denisolek.panel.schema.DTO.SchemaDTO
import pl.denisolek.panel.schema.DTO.type.*
import pl.denisolek.panel.schema.PanelSchemaApi
import pl.denisolek.stubs.dto.SchemaSpotInfoDTOStub
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

    val API_SCHEMA_PATH = "$API_BASE_PATH${GuestRestaurantApi.RESTAURANTS_ID_PATH_SCHEMAS}"
    val SCHEMAS_PATH = "$PANEL_BASE_PATH${PanelSchemaApi.SCHEMAS_PATH}"
    val FLOORS_PATH = "$PANEL_BASE_PATH${PanelSchemaApi.FLOORS_PATH}"
    val FLOORS_ID_PATH = "$PANEL_BASE_PATH${PanelSchemaApi.FLOORS_ID_PATH}"
    val SPOTS_ID_PATH = "$PANEL_BASE_PATH${PanelSchemaApi.SPOTS_ID_PATH}"

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
        val result = mvc.perform(get(API_SCHEMA_PATH, 1))
                .andExpect(status().isOk)
                .andReturn()

        val actual = convertJsonBytesToObject(result.response.contentAsString, SchemaDTO::class.java)

        assertEquals(3, actual.floors!!.size)
        assertEquals(22, actual.tables.size)
        assertEquals(30, actual.walls.size)
        assertEquals(3, actual.items.size)
        assertEquals(24, actual.wallItems.size)
    }

    @Test
    fun `addFloor_ correct data`() {
        val floorDTOStub = FloorDTO(name = "Stubbed name")
        val body = convertObjectToJsonBytes(floorDTOStub)
        mvc.perform(post(FLOORS_PATH, 1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(body))
                .andExpect(status().isCreated)

        val restaurant = restaurantRepository.findOne(1)
        val expectedFloor = Floor(id = 5, name = "Stubbed name", restaurant = restaurant)
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
        mvc.perform(delete(FLOORS_ID_PATH, 1, 2))
                .andExpect(status().isNoContent)
                .andReturn()

        val restaurant = restaurantRepository.findOne(1)
        restaurant.floors.forEach {
            Assert.assertFalse(it.id == 2)
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
        assertEquals(1, actual.tables.last().id)
        assertEquals(2, actual.tables.last().floorId)
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
        schemaDTO.tables[0].subType = EIGHT_ROUND

        val body = convertObjectToJsonBytes(schemaDTO)

        val result = mvc.perform(put(SCHEMAS_PATH, 1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(body))
                .andExpect(status().isOk)
                .andReturn()

        val actual = convertJsonBytesToObject(result.response.contentAsString, SchemaDTO::class.java)
        assertEquals(EIGHT_ROUND, actual.tables[0].subType)
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
        assertEquals(SchemaPositionDTO(10000f, 10000f), actual.tables[0].position)
    }

    @Test
    fun `updateSchema_ update existing item - details`() {
        val schemaDTO = prepareUpdateSchemaDTO()
        schemaDTO.tables[0].details = SchemaDetailsDTO(10000f, 10000f, 10000f)

        val body = convertObjectToJsonBytes(schemaDTO)

        val result = mvc.perform(put(SCHEMAS_PATH, 1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(body))
                .andExpect(status().isOk)
                .andReturn()

        val actual = convertJsonBytesToObject(result.response.contentAsString, SchemaDTO::class.java)
        assertEquals(SchemaDetailsDTO(10000f, 10000f, 10000f), actual.tables[0].details)
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
        assertEquals(SchemaSpotInfoDTO(1, 1, 4, 1), actual.tables[0].spotInfo)
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
                subType = EIGHT_ROUND,
                position = SchemaPositionDTO(15000f, 15000f),
                details = SchemaDetailsDTO(15000f, 15000f, 15000f),
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
        assertEquals(15000, actual.tables.last().spotInfo.number)
        assertEquals(10, actual.tables.last().spotInfo.capacity)
        assertEquals(5, actual.tables.last().spotInfo.minPeopleNumber)
    }

    @Test
    fun `updateSchema_ add new table - tableId not existing, spotId not existing`() {
        val schemaDTO = prepareUpdateSchemaDTO()
        schemaDTO.tables[0].spotInfo = SchemaSpotInfoDTO(1, 100, 4, 1)

        val newTable = TypeTableDTO(
                id = 123456,
                floorId = 1,
                subType = EIGHT_ROUND,
                position = SchemaPositionDTO(16000f, 16000f),
                details = SchemaDetailsDTO(16000f, 16000f, 16000f),
                spotInfo = SchemaSpotInfoDTO(
                        id = 123456,
                        number = 16000,
                        capacity = 11,
                        minPeopleNumber = 6
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
        assertEquals(16000, actual.tables.last().spotInfo.number)
        assertEquals(11, actual.tables.last().spotInfo.capacity)
        assertEquals(6, actual.tables.last().spotInfo.minPeopleNumber)
    }

    @Test
    fun `updateSchema_ add new table - tableId null, spotId not existing`() {
        val schemaDTO = prepareUpdateSchemaDTO()
        schemaDTO.tables[0].spotInfo = SchemaSpotInfoDTO(1, 100, 4, 1)

        val newTable = TypeTableDTO(
                id = null,
                floorId = 1,
                subType = EIGHT_ROUND,
                position = SchemaPositionDTO(17000f, 17000f),
                details = SchemaDetailsDTO(17000f, 17000f, 17000f),
                spotInfo = SchemaSpotInfoDTO(
                        id = 123456,
                        number = 17000,
                        capacity = 12,
                        minPeopleNumber = 7
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
        assertEquals(17000, actual.tables.last().spotInfo.number)
        assertEquals(12, actual.tables.last().spotInfo.capacity)
        assertEquals(7, actual.tables.last().spotInfo.minPeopleNumber)
    }

    @Test
    fun `updateSchema_ add new table - tableId not existing, spotId null`() {
        val schemaDTO = prepareUpdateSchemaDTO()
        schemaDTO.tables[0].spotInfo = SchemaSpotInfoDTO(1, 100, 4, 1)

        val newTable = TypeTableDTO(
                id = 123456,
                floorId = 1,
                subType = EIGHT_ROUND,
                position = SchemaPositionDTO(18000f, 18000f),
                details = SchemaDetailsDTO(18000f, 18000f, 18000f),
                spotInfo = SchemaSpotInfoDTO(
                        id = null,
                        number = 18000,
                        capacity = 13,
                        minPeopleNumber = 8
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
        assertEquals(18000, actual.tables.last().spotInfo.number)
        assertEquals(13, actual.tables.last().spotInfo.capacity)
        assertEquals(8, actual.tables.last().spotInfo.minPeopleNumber)
    }

    @Test
    fun `updateSchema_ add new table no grid, capacity 1`() {
        val schemaDTO = prepareUpdateSchemaDTO()
        schemaDTO.tables[0].spotInfo = SchemaSpotInfoDTO(1, 100, 4, 1)

        val newTable = TypeTableDTO(
                floorId = 1,
                spotInfo = SchemaSpotInfoDTO(
                        number = 19000,
                        capacity = 1,
                        minPeopleNumber = 1
                ))

        schemaDTO.tables.add(newTable)

        val body = convertObjectToJsonBytes(schemaDTO)

        val result = mvc.perform(put(SCHEMAS_PATH, 1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(body))
                .andExpect(status().isOk)
                .andReturn()

        val actual = convertJsonBytesToObject(result.response.contentAsString, SchemaDTO::class.java)
        val actualTable = actual.tables.last()
        Assert.assertNotNull(actualTable.spotInfo.id)
        assertEquals(300f, actualTable.position!!.x)
        assertEquals(300f, actualTable.position!!.y)
        assertEquals(200f, actualTable.details!!.width)
        assertEquals(200f, actualTable.details!!.height)
        assertEquals(0f, actualTable.details!!.rotation)
        assertEquals(SchemaItem.TableType.TWO, actualTable.subType)
        assertEquals(19000, actualTable.spotInfo.number)
        assertEquals(1, actualTable.spotInfo.capacity)
        assertEquals(1, actualTable.spotInfo.minPeopleNumber)
    }

    @Test
    fun `updateSchema_ add new table no grid, capacity 10`() {
        val schemaDTO = prepareUpdateSchemaDTO()
        schemaDTO.tables[0].spotInfo = SchemaSpotInfoDTO(1, 100, 4, 1)

        val newTable = TypeTableDTO(
                floorId = 1,
                spotInfo = SchemaSpotInfoDTO(
                        number = 20000,
                        capacity = 10,
                        minPeopleNumber = 1
                ))

        schemaDTO.tables.add(newTable)

        val body = convertObjectToJsonBytes(schemaDTO)

        val result = mvc.perform(put(SCHEMAS_PATH, 1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(body))
                .andExpect(status().isOk)
                .andReturn()

        val actual = convertJsonBytesToObject(result.response.contentAsString, SchemaDTO::class.java)
        val actualTable = actual.tables.last()
        Assert.assertNotNull(actualTable.spotInfo.id)
        assertEquals(300f, actualTable.position!!.x)
        assertEquals(300f, actualTable.position!!.y)
        assertEquals(200f, actualTable.details!!.width)
        assertEquals(200f, actualTable.details!!.height)
        assertEquals(0f, actualTable.details!!.rotation)
        assertEquals(EIGHT_ROUND, actualTable.subType)
        assertEquals(20000, actualTable.spotInfo.number)
        assertEquals(10, actualTable.spotInfo.capacity)
        assertEquals(1, actualTable.spotInfo.minPeopleNumber)
    }

    @Test
    fun `updateSchema_ add new table capacity less than 0`() {
        val schemaDTO = prepareUpdateSchemaDTO()
        schemaDTO.tables[0].spotInfo = SchemaSpotInfoDTO(1, 100, 4, 1)

        val newTable = TypeTableDTO(
                floorId = 1,
                spotInfo = SchemaSpotInfoDTO(
                        number = 19000,
                        capacity = -10,
                        minPeopleNumber = 1
                ))

        schemaDTO.tables.add(newTable)

        val body = convertObjectToJsonBytes(schemaDTO)

        mvc.perform(put(SCHEMAS_PATH, 1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(body))
                .andExpect(status().isBadRequest)
    }

    @Test
    fun `updateSchema_ add new table capacity more than 200`() {
        val schemaDTO = prepareUpdateSchemaDTO()
        schemaDTO.tables[0].spotInfo = SchemaSpotInfoDTO(1, 100, 4, 1)

        val newTable = TypeTableDTO(
                floorId = 1,
                spotInfo = SchemaSpotInfoDTO(
                        number = 19000,
                        capacity = 200,
                        minPeopleNumber = 1
                ))

        schemaDTO.tables.add(newTable)

        val body = convertObjectToJsonBytes(schemaDTO)

        mvc.perform(put(SCHEMAS_PATH, 1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(body))
                .andExpect(status().isBadRequest)
    }

    @Test
    fun `updateSchema_ add new table minPeopleNumber less than 0`() {
        val schemaDTO = prepareUpdateSchemaDTO()
        schemaDTO.tables[0].spotInfo = SchemaSpotInfoDTO(1, 100, 4, 1)

        val newTable = TypeTableDTO(
                floorId = 1,
                spotInfo = SchemaSpotInfoDTO(
                        number = 19000,
                        capacity = 5,
                        minPeopleNumber = -10
                ))

        schemaDTO.tables.add(newTable)

        val body = convertObjectToJsonBytes(schemaDTO)

        mvc.perform(put(SCHEMAS_PATH, 1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(body))
                .andExpect(status().isBadRequest)
    }

    @Test
    fun `updateSchema_ add new table minPeopleNumber more than 100`() {
        val schemaDTO = prepareUpdateSchemaDTO()
        schemaDTO.tables[0].spotInfo = SchemaSpotInfoDTO(1, 100, 4, 1)

        val newTable = TypeTableDTO(
                floorId = 1,
                spotInfo = SchemaSpotInfoDTO(
                        number = 19000,
                        capacity = 5,
                        minPeopleNumber = 200
                ))

        schemaDTO.tables.add(newTable)

        val body = convertObjectToJsonBytes(schemaDTO)

        mvc.perform(put(SCHEMAS_PATH, 1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(body))
                .andExpect(status().isBadRequest)
    }

    @Test
    fun `updateSchema_ add new table, minPeopleNumber is null`() {
        val schemaDTO = prepareUpdateSchemaDTO()
        schemaDTO.tables[0].spotInfo = SchemaSpotInfoDTO(1, 100, 4, 1)

        val newTable = TypeTableDTO(
                floorId = 1,
                subType = EIGHT_ROUND,
                position = SchemaPositionDTO(15000f, 15000f),
                details = SchemaDetailsDTO(15000f, 15000f, 15000f),
                spotInfo = SchemaSpotInfoDTO(
                        number = 21000,
                        capacity = 5
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
        assertEquals(21000, actual.tables.last().spotInfo.number)
        assertEquals(5, actual.tables.last().spotInfo.capacity)
        assertEquals(1, actual.tables.last().spotInfo.minPeopleNumber)
    }

    @Test
    fun `updateSchema_ enable grid`() {
        val schemaDTO = prepareUpdateSchemaDTO()
        schemaDTO.isGridEnabled = true
        val body = convertObjectToJsonBytes(schemaDTO)

        val result = mvc.perform(put(SCHEMAS_PATH, 1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(body))
                .andExpect(status().isOk)
                .andReturn()

        val actual = convertJsonBytesToObject(result.response.contentAsString, SchemaDTO::class.java)
        assertTrue(actual.isGridEnabled)
    }

    @Test
    fun `updateSchema_ disable grid`() {
        val schemaDTO = prepareUpdateSchemaDTO()
        schemaDTO.isGridEnabled = false
        val body = convertObjectToJsonBytes(schemaDTO)

        val result = mvc.perform(put(SCHEMAS_PATH, 1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(body))
                .andExpect(status().isOk)
                .andReturn()

        val actual = convertJsonBytesToObject(result.response.contentAsString, SchemaDTO::class.java)
        assertFalse(actual.isGridEnabled)
    }

    private fun prepareUpdateSchemaDTO(): SchemaDTO {
        val user = userRepository.findOne(1)
        doReturn(user).whenever(authorizationService).getCurrentUser()
        val schemas = mvc.perform(get(API_SCHEMA_PATH, 1))
                .andExpect(status().isOk)
                .andReturn()

        val schemaDTO = convertJsonBytesToObject(schemas.response.contentAsString, SchemaDTO::class.java)
        schemaDTO.floors = null
        return schemaDTO
    }

    @Test
    fun `updateSchema_ add new wall`() {
        val schemaDTO = prepareUpdateSchemaDTO()
        schemaDTO.tables[0].spotInfo = SchemaSpotInfoDTO(1, 100, 4, 1)

        val newWall = TypeWallDTO(
                floorId = 1,
                position = SchemaPositionDTO(19000f, 19000f),
                details = SchemaDetailsDTO(19000f, 19000f, 19000f))

        schemaDTO.walls.add(newWall)

        val body = convertObjectToJsonBytes(schemaDTO)

        val result = mvc.perform(put(SCHEMAS_PATH, 1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(body))
                .andExpect(status().isOk)
                .andReturn()

        val actual = convertJsonBytesToObject(result.response.contentAsString, SchemaDTO::class.java)
        Assert.assertNotNull(actual.walls[15].id)
        assertEquals(1, actual.walls[15].floorId)
        assertEquals(SchemaPositionDTO(19000f, 19000f), actual.walls[15].position)
        assertEquals(SchemaDetailsDTO(19000f, 19000f, 19000f), actual.walls[15].details)
    }

    @Test
    fun `updateSchema_ add new wallItem`() {
        val schemaDTO = prepareUpdateSchemaDTO()
        schemaDTO.tables[0].spotInfo = SchemaSpotInfoDTO(1, 100, 4, 1)

        val newWallItem = TypeWallItemDTO(
                floorId = 1,
                subType = WINDOW,
                position = SchemaPositionDTO(20000f, 20000f),
                details = SchemaDetailsDTO(20000f, 20000f, 20000f))

        schemaDTO.wallItems.add(newWallItem)

        val body = convertObjectToJsonBytes(schemaDTO)

        val result = mvc.perform(put(SCHEMAS_PATH, 1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(body))
                .andExpect(status().isOk)
                .andReturn()

        val actual = convertJsonBytesToObject(result.response.contentAsString, SchemaDTO::class.java)
        Assert.assertNotNull(actual.wallItems[12].id)
        assertEquals(1, actual.wallItems[12].floorId)
        assertEquals(WINDOW, actual.wallItems[12].subType)
        assertEquals(SchemaPositionDTO(20000f, 20000f), actual.wallItems[12].position)
        assertEquals(SchemaDetailsDTO(20000f, 20000f, 20000f), actual.wallItems[12].details)
    }

    @Test
    fun `updateSchema_ add new item`() {
        val schemaDTO = prepareUpdateSchemaDTO()
        schemaDTO.tables[0].spotInfo = SchemaSpotInfoDTO(1, 100, 4, 1)

        val item = TypeItemDTO(
                floorId = 1,
                subType = TOILET,
                position = SchemaPositionDTO(21000f, 21000f),
                details = SchemaDetailsDTO(21000f, 21000f, 21000f))

        schemaDTO.items.add(item)

        val body = convertObjectToJsonBytes(schemaDTO)

        val result = mvc.perform(put(SCHEMAS_PATH, 1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(body))
                .andExpect(status().isOk)
                .andReturn()

        val actual = convertJsonBytesToObject(result.response.contentAsString, SchemaDTO::class.java)
        Assert.assertNotNull(actual.items[1].id)
        assertEquals(1, actual.items[1].floorId)
        assertEquals(TOILET, actual.items[1].subType)
        assertEquals(SchemaPositionDTO(21000f, 21000f), actual.items[1].position)
        assertEquals(SchemaDetailsDTO(21000f, 21000f, 21000f), actual.items[1].details)
    }

    @Test
    fun `updateSchema_ remove items from schema - should not remove`() {
        val schemaDTO = prepareUpdateSchemaDTO()
        schemaDTO.tables.clear()
        schemaDTO.walls.clear()
        schemaDTO.items.clear()
        schemaDTO.wallItems.clear()

        val body = convertObjectToJsonBytes(schemaDTO)

        val result = mvc.perform(put(SCHEMAS_PATH, 1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(body))
                .andExpect(status().isOk)
                .andReturn()

        val actual = convertJsonBytesToObject(result.response.contentAsString, SchemaDTO::class.java)
        assertTrue(actual.tables.isNotEmpty())
        assertTrue(actual.wallItems.isEmpty())
        assertTrue(actual.items.isEmpty())
        assertTrue(actual.wallItems.isEmpty())
    }

    @Test
    fun `updateSpot_ not existing spot`() {
        val schemaSpotInfoDTO = SchemaSpotInfoDTOStub.getSchemaSpotInfoDTOStub()
        val body = convertObjectToJsonBytes(schemaSpotInfoDTO)

        mvc.perform(put(SPOTS_ID_PATH, 1, 900)
                .contentType(MediaType.APPLICATION_JSON)
                .content(body))
                .andExpect(status().isNotFound)
                .andReturn()
    }

    @Test
    fun `updateSpot_ capacity less than 1`() {
        val schemaSpotInfoDTO = SchemaSpotInfoDTOStub.getSchemaSpotInfoDTOStub()
        schemaSpotInfoDTO.capacity = -10
        val body = convertObjectToJsonBytes(schemaSpotInfoDTO)

        mvc.perform(put(SPOTS_ID_PATH, 1, 1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(body))
                .andExpect(status().isBadRequest)
                .andReturn()
    }

    @Test
    fun `updateSpot_ capacity more than 100`() {
        val schemaSpotInfoDTO = SchemaSpotInfoDTOStub.getSchemaSpotInfoDTOStub()
        schemaSpotInfoDTO.capacity = 200
        val body = convertObjectToJsonBytes(schemaSpotInfoDTO)

        mvc.perform(put(SPOTS_ID_PATH, 1, 1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(body))
                .andExpect(status().isBadRequest)
                .andReturn()
    }

    @Test
    fun `updateSpot_ minPeopleNumber less than 1`() {
        val schemaSpotInfoDTO = SchemaSpotInfoDTOStub.getSchemaSpotInfoDTOStub()
        schemaSpotInfoDTO.minPeopleNumber = -10
        val body = convertObjectToJsonBytes(schemaSpotInfoDTO)

        mvc.perform(put(SPOTS_ID_PATH, 1, 1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(body))
                .andExpect(status().isBadRequest)
                .andReturn()
    }

    @Test
    fun `updateSpot_ minPeopleNumber more than 100`() {
        val schemaSpotInfoDTO = SchemaSpotInfoDTOStub.getSchemaSpotInfoDTOStub()
        schemaSpotInfoDTO.minPeopleNumber = 200
        val body = convertObjectToJsonBytes(schemaSpotInfoDTO)

        mvc.perform(put(SPOTS_ID_PATH, 1, 1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(body))
                .andExpect(status().isBadRequest)
                .andReturn()
    }

    @Test
    fun `updateSpot_ spot with future reservations`() {
        val schemaSpotInfoDTO = SchemaSpotInfoDTOStub.getSchemaSpotInfoDTOStub()
        val body = convertObjectToJsonBytes(schemaSpotInfoDTO)

        mvc.perform(put(SPOTS_ID_PATH, 1, 3)
                .contentType(MediaType.APPLICATION_JSON)
                .content(body))
                .andExpect(status().isConflict)
                .andReturn()
    }

    @Test
    fun `updateSpot_ spot from different restaurant`() {
        val schemaSpotInfoDTO = SchemaSpotInfoDTOStub.getSchemaSpotInfoDTOStub()
        val body = convertObjectToJsonBytes(schemaSpotInfoDTO)

        mvc.perform(put(SPOTS_ID_PATH, 1, 24)
                .contentType(MediaType.APPLICATION_JSON)
                .content(body))
                .andExpect(status().isNotFound)
                .andReturn()
    }

    @Test
    fun `updateSpot_ correct data`() {
        val schemaSpotInfoDTO = SchemaSpotInfoDTO(
                number = 10000,
                capacity = 50,
                minPeopleNumber = 25
        )
        val body = convertObjectToJsonBytes(schemaSpotInfoDTO)

        val result = mvc.perform(put(SPOTS_ID_PATH, 1, 1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(body))
                .andExpect(status().isOk)
                .andReturn()

        val actual = convertJsonBytesToObject(result.response.contentAsString, SchemaSpotInfoDTO::class.java)
        assertEquals(10000, actual.number)
        assertEquals(50, actual.capacity)
        assertEquals(25, actual.minPeopleNumber)
    }

    @Test
    fun `deleteSpot_ not existing spot`() {
        mvc.perform(delete(SPOTS_ID_PATH, 1, 500)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound)
                .andReturn()
    }

    @Test
    fun `deleteSpot_ spot from different restaurant`() {
        mvc.perform(delete(SPOTS_ID_PATH, 1, 24)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound)
                .andReturn()
    }

    @Test
    fun `deleteSpot_ spot with reservations in the future`() {
        mvc.perform(delete(SPOTS_ID_PATH, 1, 3)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isConflict)
                .andReturn()
    }

    @Test
    fun `deleteSpot_ correct data`() {
        mvc.perform(delete(SPOTS_ID_PATH, 1, 2)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent)
                .andReturn()

        val restaurant = restaurantRepository.findOne(1)
        restaurant.spots.forEach {
            Assert.assertFalse(it.id == 2)
        }
    }
}