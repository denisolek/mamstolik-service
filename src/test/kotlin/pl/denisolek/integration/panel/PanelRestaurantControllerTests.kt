package pl.denisolek.integration.panel

import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.doReturn
import com.nhaarman.mockito_kotlin.isNotNull
import com.nhaarman.mockito_kotlin.whenever
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.SpyBean
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import org.springframework.test.web.servlet.setup.DefaultMockMvcBuilder
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.web.context.WebApplicationContext
import pl.denisolek.core.address.Address
import pl.denisolek.core.address.City
import pl.denisolek.core.restaurant.Restaurant
import pl.denisolek.core.restaurant.Settings
import pl.denisolek.core.user.UserRepository
import pl.denisolek.infrastructure.PANEL_BASE_PATH
import pl.denisolek.infrastructure.config.security.AuthorizationService
import pl.denisolek.infrastructure.util.convertJsonBytesToObject
import pl.denisolek.panel.restaurant.DTO.baseInfo.AddressDTO
import pl.denisolek.panel.restaurant.DTO.details.PanelRestaurantDetailsDTO
import pl.denisolek.panel.restaurant.PanelRestaurantApi
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
                id = 1,
                streetName = "Półwiejska 42",
                buildingNumber = "1A",
                postalCode = "60-681",
                latitude = 52.40764f,
                longitude = 16.93201f,
                city = "Poznań"
        )

        val expectedSettings = Settings (
                id = 1,
                localization = true,
                specialDates = true,
                description = true,
                photos = true,
                menu = true,
                schema = true
        )

        Assert.assertEquals("Piano Bar Restaurant & Cafe", actual.name)
        Assert.assertEquals("piano.bar.restaurant.&.cafe", actual.urlName)
        Assert.assertNotNull(actual.description)
        Assert.assertEquals("pianobar@gmail.com", actual.email)
        Assert.assertEquals("780199283", actual.phoneNumber)
        Assert.assertEquals(Restaurant.RestaurantType.RESTAURANT, actual.type)
        Assert.assertEquals(expectedAddress, actual.address)
        Assert.assertEquals(7, actual.businessHours.count())
        Assert.assertEquals(2, actual.specialDates.count())
        Assert.assertEquals(2, actual.cuisineTypes.count())
        Assert.assertEquals(2, actual.facilities.count())
        Assert.assertEquals(5, actual.menu.count())
        Assert.assertEquals(0, actual.images.count())
        Assert.assertEquals(expectedSettings, actual.settings)
    }
}