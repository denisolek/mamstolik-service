package pl.denisolek.integration.panel

import com.nhaarman.mockito_kotlin.doReturn
import com.nhaarman.mockito_kotlin.whenever
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
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
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import org.springframework.test.web.servlet.setup.DefaultMockMvcBuilder
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.web.context.WebApplicationContext
import pl.denisolek.core.restaurant.Restaurant
import pl.denisolek.core.restaurant.Settings
import pl.denisolek.core.user.UserRepository
import pl.denisolek.infrastructure.PANEL_BASE_PATH
import pl.denisolek.infrastructure.config.security.AuthorizationService
import pl.denisolek.infrastructure.util.convertJsonBytesToObject
import pl.denisolek.infrastructure.util.convertObjectToJsonBytes
import pl.denisolek.panel.restaurant.DTO.baseInfo.AddressDTO
import pl.denisolek.panel.restaurant.DTO.details.PanelRestaurantDetailsDTO
import pl.denisolek.panel.restaurant.PanelRestaurantApi
import pl.denisolek.stubs.dto.BaseInfoDTOStub
import pl.denisolek.stubs.dto.PanelRestaurantDetailsDTOStub
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
                .andExpect(MockMvcResultMatchers.status().isOk)
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
        assertEquals(2, actual.specialDates.count())
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

        val result = mvc.perform(MockMvcRequestBuilders.put(BASE_INFO_PATH, 1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(body))
                .andExpect(MockMvcResultMatchers.status().isOk)
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
        val result = mvc.perform(MockMvcRequestBuilders.put(BASE_INFO_PATH, 1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(body))

        result.andExpect(MockMvcResultMatchers.status().isBadRequest)
    }

    @Test
    fun `updateBaseInfo_ name with multiple dashes`() {
        val baseInfoStub = BaseInfoDTOStub.getBaseInfoDTO()
        baseInfoStub.name = "test--name"
        val body = convertObjectToJsonBytes(baseInfoStub)
        val result = mvc.perform(MockMvcRequestBuilders.put(BASE_INFO_PATH, 1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(body))

        result.andExpect(MockMvcResultMatchers.status().isBadRequest)
    }

    @Test
    fun `updateBaseInfo_ name with dash at the beggining and ending`() {
        val baseInfoStub = BaseInfoDTOStub.getBaseInfoDTO()
        baseInfoStub.name = "-test-"
        val body = convertObjectToJsonBytes(baseInfoStub)
        val result = mvc.perform(MockMvcRequestBuilders.put(BASE_INFO_PATH, 1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(body))

        result.andExpect(MockMvcResultMatchers.status().isBadRequest)
    }

    @Test
    fun `updateBaseInfo_ name with special characters`() {
        val baseInfoStub = BaseInfoDTOStub.getBaseInfoDTO()
        baseInfoStub.name = "test%:"
        val body = convertObjectToJsonBytes(baseInfoStub)
        val result = mvc.perform(MockMvcRequestBuilders.put(BASE_INFO_PATH, 1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(body))

        result.andExpect(MockMvcResultMatchers.status().isBadRequest)
    }

    @Test
    fun `updateBaseInfo_ type is empty`() {
        val baseInfoStub = BaseInfoDTOStub.getBaseInfoDTO()
        val body = convertObjectToJsonBytes(baseInfoStub).replace("\"type\":\"BAR\"", "\"type\":\"\"")
        val result = mvc.perform(MockMvcRequestBuilders.put(BASE_INFO_PATH, 1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(body))

        result.andExpect(MockMvcResultMatchers.status().isBadRequest)
    }

    @Test
    fun `updateBaseInfo_ type is invalid`() {
        val baseInfoStub = BaseInfoDTOStub.getBaseInfoDTO()
        val body = convertObjectToJsonBytes(baseInfoStub).replace("\"type\":\"BAR\"", "\"type\":\"INVALID_TYPE\"")
        val result = mvc.perform(MockMvcRequestBuilders.put(BASE_INFO_PATH, 1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(body))

        result.andExpect(MockMvcResultMatchers.status().isBadRequest)
    }
}