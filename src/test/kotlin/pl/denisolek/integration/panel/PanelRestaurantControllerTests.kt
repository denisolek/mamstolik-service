package pl.denisolek.integration.panel

import com.nhaarman.mockito_kotlin.doReturn
import com.nhaarman.mockito_kotlin.whenever
import org.apache.commons.lang3.RandomStringUtils
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
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.test.web.servlet.setup.DefaultMockMvcBuilder
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.web.context.WebApplicationContext
import pl.denisolek.core.restaurant.BusinessHour
import pl.denisolek.core.restaurant.Restaurant
import pl.denisolek.core.restaurant.Restaurant.CuisineType.AMERICAN
import pl.denisolek.core.restaurant.Restaurant.CuisineType.ASIAN
import pl.denisolek.core.restaurant.Restaurant.Facilities.BABY_CORNER
import pl.denisolek.core.restaurant.Restaurant.Facilities.BABY_TOILET
import pl.denisolek.core.restaurant.Settings
import pl.denisolek.core.user.UserRepository
import pl.denisolek.guest.restaurant.DTO.MenuCategoryDTO
import pl.denisolek.guest.restaurant.DTO.MenuItemDTO
import pl.denisolek.infrastructure.PANEL_BASE_PATH
import pl.denisolek.infrastructure.config.security.AuthorizationService
import pl.denisolek.infrastructure.util.convertJsonBytesToObject
import pl.denisolek.infrastructure.util.convertObjectToJsonBytes
import pl.denisolek.panel.restaurant.DTO.SpecialDateDTO
import pl.denisolek.panel.restaurant.DTO.baseInfo.AddressDTO
import pl.denisolek.panel.restaurant.DTO.details.PanelRestaurantDetailsDTO
import pl.denisolek.panel.restaurant.PanelRestaurantApi
import pl.denisolek.stubs.dto.BaseInfoDTOStub
import pl.denisolek.stubs.dto.PanelRestaurantDetailsDTOStub
import pl.denisolek.stubs.dto.ProfileDTOStub
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.LocalTime
import javax.transaction.Transactional

@RunWith(SpringRunner::class)
@ActiveProfiles("test", "fakeAuthorization")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Transactional
class PanelRestaurantControllerTests {
    @SpyBean
    lateinit var authorizationService: AuthorizationService

    @Autowired
    lateinit var userRepository: UserRepository

    @Autowired
    lateinit var applicationContext: WebApplicationContext

    lateinit var mvc: MockMvc

    val DETAILS_PATH = "$PANEL_BASE_PATH${PanelRestaurantApi.DETAILS_PATH}"
    val BASE_INFO_PATH = "$PANEL_BASE_PATH${PanelRestaurantApi.BASE_INFO_PATH}"
    val PROFILE_PATH = "$PANEL_BASE_PATH${PanelRestaurantApi.PROFILE_PATH}"


    @Before
    fun setup() {
        this.mvc = MockMvcBuilders
            .webAppContextSetup(applicationContext)
            .apply<DefaultMockMvcBuilder>(SecurityMockMvcConfigurers.springSecurity())
            .build()
    }

    @Test
    fun `getRestaurantDetails_`() {
        val user = userRepository.findOne(1)
        doReturn(user).whenever(authorizationService).getCurrentUser()
        val result = mvc.perform(MockMvcRequestBuilders.get(DETAILS_PATH, 1))
            .andExpect(status().isOk)
            .andReturn()

        val actual = convertJsonBytesToObject(result.response.contentAsString, PanelRestaurantDetailsDTO::class.java)

        val expectedAddress = AddressDTO(
            streetName = "Półwiejska 42",
            buildingNumber = "1A",
            postalCode = "60-681",
            latitude = 52.40764f,
            longitude = 16.93201f,
            city = "Poznań"
        )

        val expectedSettings = Settings(
            id = 1,
            localization = true,
            specialDates = true,
            description = true,
            photos = true,
            menu = true,
            schema = true
        )

        assertEquals("Piano Bar Restaurant & Cafe", actual.name)
        assertEquals("piano.bar.restaurant.&.cafe", actual.urlName)
        assertNotNull(actual.description)
        assertEquals("pianobar@gmail.com", actual.email)
        assertEquals("780199283", actual.phoneNumber)
        assertEquals(Restaurant.RestaurantType.RESTAURANT, actual.type)
        assertEquals(expectedAddress, actual.address)
        assertEquals(7, actual.businessHours.count())
        assertEquals(3, actual.specialDates.count())
        assertEquals(2, actual.cuisineTypes.count())
        assertEquals(2, actual.facilities.count())
        assertEquals(5, actual.menu.count())
        assertEquals(0, actual.images.count())
        assertEquals(expectedSettings, actual.settings)
    }

    @Test
    fun `updateBaseInfo_ correct data`() {
        val baseInfoStub = BaseInfoDTOStub.getBaseInfoDTO()
        val body = convertObjectToJsonBytes(baseInfoStub)

        val result = mvc.perform(
            MockMvcRequestBuilders.put(BASE_INFO_PATH, 1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(body)
        )
            .andExpect(status().isOk)
            .andReturn()

        val actual = convertJsonBytesToObject(result.response.contentAsString, PanelRestaurantDetailsDTO::class.java)

        val expected = PanelRestaurantDetailsDTOStub.getUpdatedBaseInfo()

        assertEquals(expected.name, actual.name)
        assertEquals(expected.urlName, actual.urlName)
        assertEquals(expected.phoneNumber, actual.phoneNumber)
        assertEquals(expected.type, actual.type)
        assertEquals(expected.businessHours, actual.businessHours)
        assertEquals(expected.address, actual.address)
        assertEquals(expected.specialDates, actual.specialDates)
        assertEquals(expected.settings, actual.settings)
    }

    @Test
    fun `updateBaseInfo_ empty name`() {
        val baseInfoStub = BaseInfoDTOStub.getBaseInfoDTO()
        baseInfoStub.name = ""
        val body = convertObjectToJsonBytes(baseInfoStub)
        val result = mvc.perform(
            MockMvcRequestBuilders.put(BASE_INFO_PATH, 1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(body)
        )

        result.andExpect(status().isBadRequest)
    }

    @Test
    fun `updateBaseInfo_ type is empty`() {
        val baseInfoStub = BaseInfoDTOStub.getBaseInfoDTO()
        val body = convertObjectToJsonBytes(baseInfoStub).replace("\"type\":\"BAR\"", "\"type\":\"\"")
        val result = mvc.perform(
            MockMvcRequestBuilders.put(BASE_INFO_PATH, 1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(body)
        )

        result.andExpect(status().isBadRequest)
    }

    @Test
    fun `updateBaseInfo_ phoneNumber is empty`() {
        var baseInfoStub = BaseInfoDTOStub.getBaseInfoDTO()
        baseInfoStub.phoneNumber = ""

        val body = convertObjectToJsonBytes(baseInfoStub)

        val result = mvc.perform(
            MockMvcRequestBuilders.put(BASE_INFO_PATH, 1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(body)
        )

        result.andExpect(status().isBadRequest)
    }

    @Test
    fun `updateBaseInfo_ phoneNumber with letters`() {
        var baseInfoStub = BaseInfoDTOStub.getBaseInfoDTO()
        baseInfoStub.phoneNumber = "111222ccc"

        val body = convertObjectToJsonBytes(baseInfoStub)

        val result = mvc.perform(
            MockMvcRequestBuilders.put(BASE_INFO_PATH, 1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(body)
        )

        result.andExpect(status().isBadRequest)
    }

    @Test
    fun `updateBaseInfo_ phoneNumber wrong length`() {
        var baseInfoStub = BaseInfoDTOStub.getBaseInfoDTO()
        baseInfoStub.phoneNumber = "111 222 33"

        val body = convertObjectToJsonBytes(baseInfoStub)

        val result = mvc.perform(
            MockMvcRequestBuilders.put(BASE_INFO_PATH, 1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(body)
        )

        result.andExpect(status().isBadRequest)
    }

    @Test
    fun `updateBaseInfo_ phoneNumber with not allowed chars`() {
        var baseInfoStub = BaseInfoDTOStub.getBaseInfoDTO()
        baseInfoStub.phoneNumber = "+48 111*222*333"

        val body = convertObjectToJsonBytes(baseInfoStub)

        val result = mvc.perform(
            MockMvcRequestBuilders.put(BASE_INFO_PATH, 1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(body)
        )

        result.andExpect(status().isBadRequest)
    }

    @Test
    fun `updateBaseInfo_ empty city name`() {
        val baseInfoStub = BaseInfoDTOStub.getBaseInfoDTO()
        baseInfoStub.address.city = ""
        val body = convertObjectToJsonBytes(baseInfoStub)
        val result = mvc.perform(
            MockMvcRequestBuilders.put(BASE_INFO_PATH, 1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(body)
        )

        result.andExpect(status().isBadRequest)
    }

    @Test
    fun `updateBaseInfo_ city name with multiple dashes`() {
        val baseInfoStub = BaseInfoDTOStub.getBaseInfoDTO()
        baseInfoStub.address.city = "test--name"
        val body = convertObjectToJsonBytes(baseInfoStub)
        val result = mvc.perform(
            MockMvcRequestBuilders.put(BASE_INFO_PATH, 1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(body)
        )

        result.andExpect(status().isBadRequest)
    }

    @Test
    fun `updateBaseInfo_ city name with dash at the beggining and ending`() {
        val baseInfoStub = BaseInfoDTOStub.getBaseInfoDTO()
        baseInfoStub.address.city = "-test-"
        val body = convertObjectToJsonBytes(baseInfoStub)
        val result = mvc.perform(
            MockMvcRequestBuilders.put(BASE_INFO_PATH, 1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(body)
        )

        result.andExpect(status().isBadRequest)
    }

    @Test
    fun `updateBaseInfo_ city name with special characters`() {
        val baseInfoStub = BaseInfoDTOStub.getBaseInfoDTO()
        baseInfoStub.address.city = "test%:"
        val body = convertObjectToJsonBytes(baseInfoStub)
        val result = mvc.perform(
            MockMvcRequestBuilders.put(BASE_INFO_PATH, 1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(body)
        )

        result.andExpect(status().isBadRequest)
    }

    @Test
    fun `updateBaseInfo_ existing city`() {
        val baseInfoStub = BaseInfoDTOStub.getBaseInfoDTO()
        baseInfoStub.address.city = "WARSZAWA"
        val body = convertObjectToJsonBytes(baseInfoStub)

        val result = mvc.perform(
            MockMvcRequestBuilders.put(BASE_INFO_PATH, 1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(body)
        )
            .andExpect(status().isOk)
            .andReturn()

        val actual = convertJsonBytesToObject(result.response.contentAsString, PanelRestaurantDetailsDTO::class.java)

        assertEquals("Warszawa", actual.address.city)
    }

    @Test
    fun `updateBaseInfo_ not existing city`() {
        val baseInfoStub = BaseInfoDTOStub.getBaseInfoDTO()
        baseInfoStub.address.city = "SuperCity"
        val body = convertObjectToJsonBytes(baseInfoStub)

        val result = mvc.perform(
            MockMvcRequestBuilders.put(BASE_INFO_PATH, 1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(body)
        )
            .andExpect(status().isOk)
            .andReturn()

        val actual = convertJsonBytesToObject(result.response.contentAsString, PanelRestaurantDetailsDTO::class.java)

        assertEquals("SuperCity", actual.address.city)
    }

    @Test
    fun `updateBaseInfo_ businessHour no id`() {
        val baseInfoStub = BaseInfoDTOStub.getBaseInfoDTO()
        baseInfoStub.businessHours[DayOfWeek.MONDAY] = BusinessHour(
            openTime = LocalTime.of(4, 0),
            closeTime = LocalTime.of(5, 0),
            isClosed = false
        )
        val body = convertObjectToJsonBytes(baseInfoStub)
        val result = mvc.perform(
            MockMvcRequestBuilders.put(BASE_INFO_PATH, 1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(body)
        )
            .andExpect(status().isOk)
            .andReturn()

        val actual = convertJsonBytesToObject(result.response.contentAsString, PanelRestaurantDetailsDTO::class.java)

        assertEquals(1, actual.businessHours[DayOfWeek.MONDAY]!!.id)
        assertEquals(LocalTime.of(4, 0), actual.businessHours[DayOfWeek.MONDAY]!!.openTime)
        assertEquals(LocalTime.of(5, 0), actual.businessHours[DayOfWeek.MONDAY]!!.closeTime)
        assertEquals(false, actual.businessHours[DayOfWeek.MONDAY]!!.isClosed)
    }

    @Test
    fun `updateBaseInfo_ businessHour not existing id`() {
        val baseInfoStub = BaseInfoDTOStub.getBaseInfoDTO()
        baseInfoStub.businessHours[DayOfWeek.MONDAY] = BusinessHour(
            id = 5000,
            openTime = LocalTime.of(4, 0),
            closeTime = LocalTime.of(5, 0),
            isClosed = false
        )
        val body = convertObjectToJsonBytes(baseInfoStub)
        val result = mvc.perform(
            MockMvcRequestBuilders.put(BASE_INFO_PATH, 1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(body)
        )
            .andExpect(status().isOk)
            .andReturn()

        val actual = convertJsonBytesToObject(result.response.contentAsString, PanelRestaurantDetailsDTO::class.java)

        assertEquals(1, actual.businessHours[DayOfWeek.MONDAY]!!.id)
        assertEquals(LocalTime.of(4, 0), actual.businessHours[DayOfWeek.MONDAY]!!.openTime)
        assertEquals(LocalTime.of(5, 0), actual.businessHours[DayOfWeek.MONDAY]!!.closeTime)
        assertEquals(false, actual.businessHours[DayOfWeek.MONDAY]!!.isClosed)
    }

    @Test
    fun `updateBaseInfo_ businessHour with reservations in the future`() {
        val baseInfoStub = BaseInfoDTOStub.getBaseInfoDTO()
        baseInfoStub.businessHours[DayOfWeek.FRIDAY] = BusinessHour(
            openTime = LocalTime.of(18, 0),
            closeTime = LocalTime.of(20, 0),
            isClosed = false
        )
        val body = convertObjectToJsonBytes(baseInfoStub)
        val result = mvc.perform(
            MockMvcRequestBuilders.put(BASE_INFO_PATH, 1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(body)
        )

        result.andExpect(status().isBadRequest)
    }

    @Test
    fun `updateBaseInfo_ businessHour removed`() {
        val baseInfoStub = BaseInfoDTOStub.getBaseInfoDTO()
        baseInfoStub.businessHours.remove(DayOfWeek.MONDAY)
        val body = convertObjectToJsonBytes(baseInfoStub)
        val result = mvc.perform(
            MockMvcRequestBuilders.put(BASE_INFO_PATH, 1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(body)
        )

        result.andExpect(status().isBadRequest)
    }

    @Test
    fun `updateBaseInfo_ businessHour closeTime before openTime`() {
        val baseInfoStub = BaseInfoDTOStub.getBaseInfoDTO()
        baseInfoStub.businessHours[DayOfWeek.MONDAY] = BusinessHour(
            id = 5000,
            openTime = LocalTime.of(5, 0),
            closeTime = LocalTime.of(4, 0),
            isClosed = false
        )
        val body = convertObjectToJsonBytes(baseInfoStub)
        val result = mvc.perform(
            MockMvcRequestBuilders.put(BASE_INFO_PATH, 1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(body)
        )

        result.andExpect(status().isBadRequest)
    }

    @Test
    fun `updateBaseInfo_ businessHour no openTime`() {
        val baseInfoStub = BaseInfoDTOStub.getBaseInfoDTO()
        baseInfoStub.businessHours[DayOfWeek.MONDAY] = BusinessHour(
            id = 5000,
            closeTime = LocalTime.of(15, 0),
            isClosed = false
        )
        val body = convertObjectToJsonBytes(baseInfoStub)
        val result = mvc.perform(
            MockMvcRequestBuilders.put(BASE_INFO_PATH, 1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(body)
        )
            .andExpect(status().isOk)
            .andReturn()

        val actual = convertJsonBytesToObject(result.response.contentAsString, PanelRestaurantDetailsDTO::class.java)

        assertEquals(1, actual.businessHours[DayOfWeek.MONDAY]!!.id)
        assertEquals(LocalTime.of(8, 0), actual.businessHours[DayOfWeek.MONDAY]!!.openTime)
        assertEquals(LocalTime.of(15, 0), actual.businessHours[DayOfWeek.MONDAY]!!.closeTime)
        assertEquals(false, actual.businessHours[DayOfWeek.MONDAY]!!.isClosed)
    }

    @Test
    fun `updateBaseInfo_ businessHour no closeTime`() {
        val baseInfoStub = BaseInfoDTOStub.getBaseInfoDTO()
        baseInfoStub.businessHours[DayOfWeek.MONDAY] = BusinessHour(
            id = 5000,
            openTime = LocalTime.of(5, 0),
            isClosed = false
        )
        val body = convertObjectToJsonBytes(baseInfoStub)
        val result = mvc.perform(
            MockMvcRequestBuilders.put(BASE_INFO_PATH, 1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(body)
        )
            .andExpect(status().isOk)
            .andReturn()

        val actual = convertJsonBytesToObject(result.response.contentAsString, PanelRestaurantDetailsDTO::class.java)

        assertEquals(1, actual.businessHours[DayOfWeek.MONDAY]!!.id)
        assertEquals(LocalTime.of(5, 0), actual.businessHours[DayOfWeek.MONDAY]!!.openTime)
        assertEquals(LocalTime.of(20, 0), actual.businessHours[DayOfWeek.MONDAY]!!.closeTime)
        assertEquals(false, actual.businessHours[DayOfWeek.MONDAY]!!.isClosed)
    }

    @Test
    fun `updateBaseInfo_ businessHour no isClosed`() {
        val baseInfoStub = BaseInfoDTOStub.getBaseInfoDTO()
        baseInfoStub.businessHours[DayOfWeek.MONDAY] = BusinessHour(
            id = 5000,
            openTime = LocalTime.of(4, 0),
            closeTime = LocalTime.of(5, 0)
        )
        val body = convertObjectToJsonBytes(baseInfoStub)
        val result = mvc.perform(
            MockMvcRequestBuilders.put(BASE_INFO_PATH, 1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(body)
        )
            .andExpect(status().isOk)
            .andReturn()

        val actual = convertJsonBytesToObject(result.response.contentAsString, PanelRestaurantDetailsDTO::class.java)

        assertEquals(1, actual.businessHours[DayOfWeek.MONDAY]!!.id)
        assertEquals(LocalTime.of(4, 0), actual.businessHours[DayOfWeek.MONDAY]!!.openTime)
        assertEquals(LocalTime.of(5, 0), actual.businessHours[DayOfWeek.MONDAY]!!.closeTime)
        assertEquals(false, actual.businessHours[DayOfWeek.MONDAY]!!.isClosed)
    }

    @Test
    fun `updateBaseInfo_ specialDate update existing`() {
        val baseInfoStub = BaseInfoDTOStub.getBaseInfoDTO()
        baseInfoStub.specialDates.first { it.id == 1 }.let {
            it.businessHour.openTime = LocalTime.of(20, 20)
            it.businessHour.closeTime = LocalTime.of(21, 21)
            it.businessHour.isClosed = true
        }
        val body = convertObjectToJsonBytes(baseInfoStub)
        val result = mvc.perform(
            MockMvcRequestBuilders.put(BASE_INFO_PATH, 1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(body)
        )
            .andExpect(status().isOk)
            .andReturn()

        val actual = convertJsonBytesToObject(result.response.contentAsString, PanelRestaurantDetailsDTO::class.java)

        val actualSpecialDate = actual.specialDates.first { it.id == 1 }
        assertEquals(LocalDate.of(2017, 10, 10), actualSpecialDate.date)
        assertEquals(LocalTime.of(20, 20), actualSpecialDate.businessHour.openTime)
        assertEquals(LocalTime.of(21, 21), actualSpecialDate.businessHour.closeTime)
        assertEquals(true, actualSpecialDate.businessHour.isClosed)
    }

    @Test
    fun `updateBaseInfo_ specialDate update existing, change date`() {
        val baseInfoStub = BaseInfoDTOStub.getBaseInfoDTO()
        baseInfoStub.specialDates.first { it.id == 1 }.let {
            it.businessHour.openTime = LocalTime.of(20, 20)
            it.businessHour.closeTime = LocalTime.of(21, 21)
            it.businessHour.isClosed = true
            it.date = LocalDate.of(2017, 10, 11)
        }
        val body = convertObjectToJsonBytes(baseInfoStub)
        val result = mvc.perform(
            MockMvcRequestBuilders.put(BASE_INFO_PATH, 1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(body)
        )

        result.andExpect(status().isBadRequest)
    }

    @Test
    fun `updateBaseInfo_ specialDate add new for existing date`() {
        val baseInfoStub = BaseInfoDTOStub.getBaseInfoDTO()
        baseInfoStub.specialDates.add(
            SpecialDateDTO(
                id = null,
                date = LocalDate.of(2017, 10, 10),
                businessHour = BusinessHour(
                    id = 50,
                    openTime = LocalTime.of(17, 20),
                    closeTime = LocalTime.of(18, 20),
                    isClosed = false
                )
            )
        )
        val body = convertObjectToJsonBytes(baseInfoStub)
        val result = mvc.perform(
            MockMvcRequestBuilders.put(BASE_INFO_PATH, 1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(body)
        )

        result.andExpect(status().isConflict)
    }

    @Test
    fun `updateBaseInfo_ specialDate add new for existing date and different day id`() {
        val baseInfoStub = BaseInfoDTOStub.getBaseInfoDTO()
        baseInfoStub.specialDates.add(
            SpecialDateDTO(
                id = 2,
                date = LocalDate.of(2017, 10, 10),
                businessHour = BusinessHour(
                    id = null,
                    openTime = LocalTime.of(17, 20),
                    closeTime = LocalTime.of(18, 20),
                    isClosed = false
                )
            )
        )
        val body = convertObjectToJsonBytes(baseInfoStub)
        val result = mvc.perform(
            MockMvcRequestBuilders.put(BASE_INFO_PATH, 1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(body)
        )

        result.andExpect(status().isConflict)
    }

    @Test
    fun `updateBaseInfo_ specialDate add new`() {
        val baseInfoStub = BaseInfoDTOStub.getBaseInfoDTO()
        baseInfoStub.specialDates.add(
            SpecialDateDTO(
                id = null,
                date = LocalDate.of(2017, 10, 12),
                businessHour = BusinessHour(
                    id = null,
                    openTime = LocalTime.of(10, 0),
                    closeTime = LocalTime.of(11, 0),
                    isClosed = false
                )
            )
        )

        val body = convertObjectToJsonBytes(baseInfoStub)
        val result = mvc.perform(
            MockMvcRequestBuilders.put(BASE_INFO_PATH, 1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(body)
        )
            .andExpect(status().isOk)
            .andReturn()

        val actual = convertJsonBytesToObject(result.response.contentAsString, PanelRestaurantDetailsDTO::class.java)

        val actualSpecialDate = actual.specialDates.find { it.date == LocalDate.of(2017, 10, 12) }!!
        assertEquals(LocalTime.of(10, 0), actualSpecialDate.businessHour.openTime)
        assertEquals(LocalTime.of(11, 0), actualSpecialDate.businessHour.closeTime)
        assertEquals(false, actualSpecialDate.businessHour.isClosed)
    }

    @Test
    fun `updateBaseInfo_ specialDate remove existing`() {
        val baseInfoStub = BaseInfoDTOStub.getBaseInfoDTO()
        baseInfoStub.specialDates.removeIf { it.id == 2 }

        val body = convertObjectToJsonBytes(baseInfoStub)
        val result = mvc.perform(
            MockMvcRequestBuilders.put(BASE_INFO_PATH, 1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(body)
        )
            .andExpect(status().isOk)
            .andReturn()

        val actual = convertJsonBytesToObject(result.response.contentAsString, PanelRestaurantDetailsDTO::class.java)

        val exists = actual.specialDates.any { it.id == 2 }
        assertFalse(exists)
    }

    @Test
    fun `updateBaseInfo_ specialDate add new close time before open time`() {
        val baseInfoStub = BaseInfoDTOStub.getBaseInfoDTO()
        baseInfoStub.specialDates.add(
            SpecialDateDTO(
                id = null,
                date = LocalDate.of(2018, 10, 22),
                businessHour = BusinessHour(
                    id = null,
                    openTime = LocalTime.of(17, 20),
                    closeTime = LocalTime.of(13, 20),
                    isClosed = false
                )
            )
        )
        val body = convertObjectToJsonBytes(baseInfoStub)
        val result = mvc.perform(
            MockMvcRequestBuilders.put(BASE_INFO_PATH, 1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(body)
        )

        result.andExpect(status().isBadRequest)
    }

    @Test
    fun `updateBaseInfo_ specialDate remove one with future reservation`() {
        val baseInfoStub = BaseInfoDTOStub.getBaseInfoDTO()
        baseInfoStub.specialDates.removeIf { it.id == 3 }

        val body = convertObjectToJsonBytes(baseInfoStub)
        val result = mvc.perform(
            MockMvcRequestBuilders.put(BASE_INFO_PATH, 1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(body)
        )

        result.andExpect(status().isBadRequest)
    }

    @Test
    fun `updateBaseInfo_ specialDate add new for day with reservations`() {
        val baseInfoStub = BaseInfoDTOStub.getBaseInfoDTO()
        baseInfoStub.specialDates.add(
            SpecialDateDTO(
                id = null,
                date = LocalDate.of(2018, 10, 20),
                businessHour = BusinessHour(
                    id = null,
                    openTime = LocalTime.of(18, 20),
                    closeTime = LocalTime.of(22, 20),
                    isClosed = false
                )
            )
        )
        val body = convertObjectToJsonBytes(baseInfoStub)
        val result = mvc.perform(
            MockMvcRequestBuilders.put(BASE_INFO_PATH, 1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(body)
        )

        result.andExpect(status().isBadRequest)
    }

    @Test
    fun `updateProfile_ correct data`() {
        val profileDTOStub = ProfileDTOStub.getProfileDTO()
        val body = convertObjectToJsonBytes(profileDTOStub)
        val result = mvc.perform(
            MockMvcRequestBuilders.put(PROFILE_PATH, 1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(body)
        )
            .andExpect(status().isOk)
            .andReturn()

        val actual = convertJsonBytesToObject(result.response.contentAsString, PanelRestaurantDetailsDTO::class.java)

        assertEquals("Updated profile", actual.description)
        assertEquals(2, actual.cuisineTypes.size)
        assertTrue(actual.cuisineTypes.contains(ASIAN))
        assertTrue(actual.cuisineTypes.contains(AMERICAN))
        assertEquals(2, actual.facilities.size)
        assertTrue(actual.facilities.contains(BABY_TOILET))
        assertTrue(actual.facilities.contains(BABY_CORNER))
        assertFalse(actual.settings.menu)
        assertFalse(actual.settings.description)
        assertFalse(actual.settings.photos)
        assertEquals(1, actual.menu.size)
        assertEquals(4, actual.menu[0].items.size)
    }

    @Test
    fun `updateProfile_ description is too long`() {
        val profileDTOStub = ProfileDTOStub.getProfileDTO()
        profileDTOStub.description = RandomStringUtils.random(1001)
        val body = convertObjectToJsonBytes(profileDTOStub)
        val result = mvc.perform(
            MockMvcRequestBuilders.put(PROFILE_PATH, 1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(body)
        )

        result.andExpect(status().isBadRequest)
    }

    @Test
    fun `updateProfile_ add menu category`() {
        val profileDTOStub = ProfileDTOStub.getProfileDTO()
        profileDTOStub.menu.add(
            MenuCategoryDTO(
                name = "new category",
                description = "description",
                position = 1,
                items = mutableListOf()
            )
        )
        val body = convertObjectToJsonBytes(profileDTOStub)
        val result = mvc.perform(
            MockMvcRequestBuilders.put(PROFILE_PATH, 1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(body)
        )
            .andExpect(status().isOk)
            .andReturn()

        val actual = convertJsonBytesToObject(result.response.contentAsString, PanelRestaurantDetailsDTO::class.java)

        assertEquals(2, actual.menu.size)
        assertEquals("new category", actual.menu[1].name)
        assertEquals(0, actual.menu[1].items.size)
    }

    @Test
    fun `updateProfile_ add menu category with items`() {
        val profileDTOStub = ProfileDTOStub.getProfileDTO()
        profileDTOStub.menu.add(
            MenuCategoryDTO(
                name = "new category",
                description = "description",
                position = 1,
                items = mutableListOf(
                    MenuItemDTO(
                        name = "new item",
                        description = "new item description",
                        price = 100f,
                        position = 0
                    )
                )
            )
        )
        val body = convertObjectToJsonBytes(profileDTOStub)
        val result = mvc.perform(
            MockMvcRequestBuilders.put(PROFILE_PATH, 1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(body)
        )
            .andExpect(status().isOk)
            .andReturn()

        val actual = convertJsonBytesToObject(result.response.contentAsString, PanelRestaurantDetailsDTO::class.java)

        assertEquals(2, actual.menu.size)
        assertEquals("new category", actual.menu[1].name)
        assertEquals(1, actual.menu[1].items.size)
        assertNotNull(actual.menu[1].items[0].id)
        assertEquals("new item", actual.menu[1].items[0].name)
    }

    @Test
    fun `updateProfile_ remove items from category`() {
        val profileDTOStub = ProfileDTOStub.getProfileDTO()
        profileDTOStub.menu[0].items.removeIf { it.id == 1 }

        val body = convertObjectToJsonBytes(profileDTOStub)
        val result = mvc.perform(
            MockMvcRequestBuilders.put(PROFILE_PATH, 1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(body)
        )
            .andExpect(status().isOk)
            .andReturn()

        val actual = convertJsonBytesToObject(result.response.contentAsString, PanelRestaurantDetailsDTO::class.java)

        assertEquals(1, actual.menu.size)
        assertEquals(3, actual.menu[0].items.size)
        assertFalse(actual.menu[0].items.any { it.id == 1 })
    }

    @Test
    fun `updateProfile_ remove category`() {
        val profileDTOStub = ProfileDTOStub.getProfileDTO()
        profileDTOStub.menu.clear()

        val body = convertObjectToJsonBytes(profileDTOStub)
        val result = mvc.perform(
            MockMvcRequestBuilders.put(PROFILE_PATH, 1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(body)
        )
            .andExpect(status().isOk)
            .andReturn()

        val actual = convertJsonBytesToObject(result.response.contentAsString, PanelRestaurantDetailsDTO::class.java)

        assertEquals(0, actual.menu.size)
    }

    @Test
    fun `updateProfile_ update category`() {
        val profileDTOStub = ProfileDTOStub.getProfileDTO()
        profileDTOStub.menu[0].name = "Updated category name"

        val body = convertObjectToJsonBytes(profileDTOStub)
        val result = mvc.perform(
            MockMvcRequestBuilders.put(PROFILE_PATH, 1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(body)
        )
            .andExpect(status().isOk)
            .andReturn()

        val actual = convertJsonBytesToObject(result.response.contentAsString, PanelRestaurantDetailsDTO::class.java)

        assertEquals(1, actual.menu.size)
        assertEquals("Updated category name", actual.menu[0].name)
    }

    @Test
    fun `updateProfile_ update category item`() {
        val profileDTOStub = ProfileDTOStub.getProfileDTO()
        profileDTOStub.menu[0].items[0].name = "Updated category item name"

        val body = convertObjectToJsonBytes(profileDTOStub)
        val result = mvc.perform(
            MockMvcRequestBuilders.put(PROFILE_PATH, 1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(body)
        )
            .andExpect(status().isOk)
            .andReturn()

        val actual = convertJsonBytesToObject(result.response.contentAsString, PanelRestaurantDetailsDTO::class.java)

        assertEquals(1, actual.menu.size)
        assertEquals("Updated category item name", actual.menu[0].items[0].name)
    }
}