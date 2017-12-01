package pl.denisolek.integration.panel

import org.junit.Assert.assertEquals
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
import pl.denisolek.core.reservation.Reservation
import pl.denisolek.core.user.UserRepository
import pl.denisolek.infrastructure.PANEL_BASE_PATH
import pl.denisolek.infrastructure.config.security.AuthorizationService
import pl.denisolek.infrastructure.util.convertJsonBytesToObject
import pl.denisolek.infrastructure.util.convertObjectToJsonBytes
import pl.denisolek.panel.reservation.DTO.PanelReservationDTO
import pl.denisolek.panel.reservation.DTO.PanelReservationsDTO
import pl.denisolek.panel.reservation.DTO.ReservationCustomerDTO
import pl.denisolek.panel.reservation.DTO.ReservationSpotInfoDTO
import pl.denisolek.panel.reservation.PanelReservationApi
import pl.denisolek.panel.reservation.PanelReservationController
import pl.denisolek.stubs.dto.PanelCreateReservationDTOStub
import java.time.LocalDate
import java.time.LocalTime
import javax.transaction.Transactional

@RunWith(SpringRunner::class)
@ActiveProfiles("test", "fakeAuthorization")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Transactional
class PanelReservationControllerTests {
    @SpyBean
    lateinit var authorizationService: AuthorizationService

    @Autowired
    lateinit var userRepository: UserRepository

    @Autowired
    lateinit var applicationContext: WebApplicationContext

    lateinit var mvc: MockMvc

    val RESERVATIONS_PATH = "$PANEL_BASE_PATH${PanelReservationApi.RESERVATIONS_PATH}"
    val RESERVATIONS_ID_PATH = "$PANEL_BASE_PATH${PanelReservationApi.RESERVATIONS_ID_PATH}"

    @Before
    fun setup() {
        this.mvc = MockMvcBuilders
                .webAppContextSetup(applicationContext)
                .apply<DefaultMockMvcBuilder>(SecurityMockMvcConfigurers.springSecurity())
                .build()
    }

    @Test
    fun `addReservation_ correct data`() {
        val createReservationStub = PanelCreateReservationDTOStub.getPanelCreateReservationDTOStub()
        val body = convertObjectToJsonBytes(createReservationStub)
        val result = mvc.perform(MockMvcRequestBuilders.post(RESERVATIONS_PATH, 1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(body))
                .andExpect(MockMvcResultMatchers.status().isCreated)
                .andReturn()

        val actual = convertJsonBytesToObject(result.response.contentAsString, PanelReservationsDTO::class.java)

        val expectedReservation = PanelReservationDTO(
                id = 6,
                customer = ReservationCustomerDTO(
                        firstName = "NameStub",
                        lastName = "SurnameStub",
                        email = "stub@test.pl",
                        phoneNumber = "123123123"
                ),
                peopleNumber = 3,
                time = LocalTime.of(14, 0),
                spots = listOf(ReservationSpotInfoDTO(
                        id = 1,
                        number = 1,
                        floorName = "Parter"
                )),
                note = "NoteStub",
                state = Reservation.ReservationState.ACCEPTED
        )

        assertEquals(LocalTime.of(13, 0), actual.openTime)
        assertEquals(LocalTime.of(23, 0), actual.closeTime)
        assertEquals(1, actual.reservations.count())
        assertEquals(expectedReservation, actual.reservations[0])
    }

    @Test
    fun `addReservation_ empty customer`() {
        val createReservationStub = PanelCreateReservationDTOStub.getPanelCreateReservationDTOStub()
        createReservationStub.customer = ReservationCustomerDTO(
                firstName = null,
                lastName = null,
                email = null,
                phoneNumber = null
        )
        val body = convertObjectToJsonBytes(createReservationStub)
        val result = mvc.perform(MockMvcRequestBuilders.post(RESERVATIONS_PATH, 1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(body))
                .andExpect(MockMvcResultMatchers.status().isCreated)
                .andReturn()

        val actual = convertJsonBytesToObject(result.response.contentAsString, PanelReservationsDTO::class.java)

        val expectedReservation = PanelReservationDTO(
                id = 7,
                customer = ReservationCustomerDTO(
                        firstName = "Tomasz",
                        lastName = "Jabłoński",
                        email = "test@test.pl",
                        phoneNumber = "780199283"
                ),
                peopleNumber = 3,
                time = LocalTime.of(14, 0),
                spots = listOf(ReservationSpotInfoDTO(
                        id = 1,
                        number = 1,
                        floorName = "Parter"
                )),
                note = "NoteStub",
                state = Reservation.ReservationState.ACCEPTED
        )

        assertEquals(LocalTime.of(13, 0), actual.openTime)
        assertEquals(LocalTime.of(23, 0), actual.closeTime)
        assertEquals(1, actual.reservations.count())
        assertEquals(expectedReservation, actual.reservations[0])
    }

    @Test
    fun `addReservation_ invalid email`() {
        val createReservationStub = PanelCreateReservationDTOStub.getPanelCreateReservationDTOStub()
        createReservationStub.customer.email = "invalidEmail"
        val body = convertObjectToJsonBytes(createReservationStub)
        val result = mvc.perform(MockMvcRequestBuilders.post(RESERVATIONS_PATH, 1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(body))

        result.andExpect(MockMvcResultMatchers.status().isBadRequest)
    }

    @Test
    fun `addReservation_ invalid spot`() {
        val createReservationStub = PanelCreateReservationDTOStub.getPanelCreateReservationDTOStub()
        createReservationStub.spots = listOf(500)
        val body = convertObjectToJsonBytes(createReservationStub)
        val result = mvc.perform(MockMvcRequestBuilders.post(RESERVATIONS_PATH, 1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(body))

        result.andExpect(MockMvcResultMatchers.status().isNotFound)
    }

    @Test
    fun `addReservation_ taken spot`() {
        val createReservationStub = PanelCreateReservationDTOStub.getPanelCreateReservationDTOStub()
        val body = convertObjectToJsonBytes(createReservationStub)
        mvc.perform(MockMvcRequestBuilders.post(RESERVATIONS_PATH, 1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(body))
                .andExpect(MockMvcResultMatchers.status().isCreated)

        val actualCreateReservationStub = PanelCreateReservationDTOStub.getPanelCreateReservationDTOStub()
        val actualBody = convertObjectToJsonBytes(actualCreateReservationStub)
        val actualResult = mvc.perform(MockMvcRequestBuilders.post(RESERVATIONS_PATH, 1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(actualBody))
        actualResult.andExpect(MockMvcResultMatchers.status().isBadRequest)
    }

    @Test
    fun `addReservation_ existing customer with other data`() {
        val createReservationStub = PanelCreateReservationDTOStub.getPanelCreateReservationDTOStub()
        createReservationStub.customer.phoneNumber = "666894323"
        val body = convertObjectToJsonBytes(createReservationStub)
        val result = mvc.perform(MockMvcRequestBuilders.post(RESERVATIONS_PATH, 1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(body))
                .andExpect(MockMvcResultMatchers.status().isCreated)
                .andReturn()

        val actual = convertJsonBytesToObject(result.response.contentAsString, PanelReservationsDTO::class.java)

        val expectedReservation = PanelReservationDTO(
                id = 8,
                customer = ReservationCustomerDTO(
                        firstName = "Karola",
                        lastName = "Szafrańska",
                        email = "karola.szafranska@gmail.pl",
                        phoneNumber = "666894323"
                ),
                peopleNumber = 3,
                time = LocalTime.of(14, 0),
                spots = listOf(ReservationSpotInfoDTO(
                        id = 1,
                        number = 1,
                        floorName = "Parter"
                )),
                note = "NoteStub",
                state = Reservation.ReservationState.ACCEPTED
        )

        assertEquals(LocalTime.of(13, 0), actual.openTime)
        assertEquals(LocalTime.of(23, 0), actual.closeTime)
        assertEquals(1, actual.reservations.count())
        assertEquals(expectedReservation, actual.reservations[0])
    }

    @Test
    fun `getReservations no reservations`() {
        val result = mvc.perform(MockMvcRequestBuilders.get(RESERVATIONS_PATH, 1)
                .param(PanelReservationController.API.DATE, LocalDate.of(2020, 10, 18).toString()))
                .andExpect(MockMvcResultMatchers.status().isOk)
                .andReturn()

        val actual = convertJsonBytesToObject(result.response.contentAsString, PanelReservationsDTO::class.java)
        assertEquals(LocalTime.of(13, 0), actual.openTime)
        assertEquals(LocalTime.of(20, 0), actual.closeTime)
        assertEquals(0, actual.reservations.count())
    }

    @Test
    fun `getReservations with one reservation`() {
        val result = mvc.perform(MockMvcRequestBuilders.get(RESERVATIONS_PATH, 1)
                .param(PanelReservationController.API.DATE, LocalDate.of(2018, 10, 18).toString()))
                .andExpect(MockMvcResultMatchers.status().isOk)
                .andReturn()

        val actual = convertJsonBytesToObject(result.response.contentAsString, PanelReservationsDTO::class.java)
        assertEquals(LocalTime.of(12, 0), actual.openTime)
        assertEquals(LocalTime.of(21, 0), actual.closeTime)
        assertEquals(1, actual.reservations.count())
    }

    @Test
    fun `getReservations with multiple reservations`() {
        val result = mvc.perform(MockMvcRequestBuilders.get(RESERVATIONS_PATH, 1)
                .param(PanelReservationController.API.DATE, LocalDate.of(2017, 10, 18).toString()))
                .andExpect(MockMvcResultMatchers.status().isOk)
                .andReturn()

        val actual = convertJsonBytesToObject(result.response.contentAsString, PanelReservationsDTO::class.java)
        assertEquals(LocalTime.of(12, 0), actual.openTime)
        assertEquals(LocalTime.of(21, 0), actual.closeTime)
        assertEquals(2, actual.reservations.count())
    }

    @Test
    fun `editReservation correct data`() {
        val createReservationStub = PanelCreateReservationDTOStub.getPanelCreateReservationDTOStub()
        val body = convertObjectToJsonBytes(createReservationStub)
        val result = mvc.perform(MockMvcRequestBuilders.put(RESERVATIONS_ID_PATH, 1, 1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(body))
                .andExpect(MockMvcResultMatchers.status().isOk)
                .andReturn()

        val actual = convertJsonBytesToObject(result.response.contentAsString, PanelReservationsDTO::class.java)

        val expectedReservation = PanelReservationDTO(
                id = 1,
                customer = ReservationCustomerDTO(
                        firstName = "NameStub",
                        lastName = "SurnameStub",
                        email = "stub@test.pl",
                        phoneNumber = "123123123"
                ),
                peopleNumber = 3,
                time = LocalTime.of(14, 0),
                spots = listOf(ReservationSpotInfoDTO(
                        id = 1,
                        number = 1,
                        floorName = "Parter"
                )),
                note = "NoteStub",
                state = Reservation.ReservationState.ACCEPTED
        )

        assertEquals(LocalTime.of(13, 0), actual.openTime)
        assertEquals(LocalTime.of(23, 0), actual.closeTime)
        assertEquals(1, actual.reservations.count())
        assertEquals(expectedReservation, actual.reservations[0])
    }
}