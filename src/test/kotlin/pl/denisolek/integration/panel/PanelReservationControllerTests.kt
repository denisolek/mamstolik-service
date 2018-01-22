package pl.denisolek.integration.panel

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
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import org.springframework.test.web.servlet.setup.DefaultMockMvcBuilder
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.web.context.WebApplicationContext
import pl.denisolek.core.reservation.Reservation
import pl.denisolek.core.reservation.ReservationRepository
import pl.denisolek.core.user.UserRepository
import pl.denisolek.infrastructure.PANEL_BASE_PATH
import pl.denisolek.infrastructure.config.security.AuthorizationService
import pl.denisolek.infrastructure.util.convertJsonBytesToObject
import pl.denisolek.infrastructure.util.convertObjectToJsonBytes
import pl.denisolek.panel.reservation.DTO.*
import pl.denisolek.panel.reservation.PanelReservationApi
import pl.denisolek.panel.reservation.PanelReservationController
import pl.denisolek.stubs.dto.PanelCreateReservationDTOStub
import java.time.LocalDate
import java.time.LocalDateTime
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
    lateinit var reservationRepository: ReservationRepository

    @Autowired
    lateinit var applicationContext: WebApplicationContext

    lateinit var mvc: MockMvc

    val RESERVATIONS_PATH = "$PANEL_BASE_PATH${PanelReservationApi.RESERVATIONS_PATH}"
    val RESERVATIONS_ID_PATH = "$PANEL_BASE_PATH${PanelReservationApi.RESERVATIONS_ID_PATH}"
    val RESERVATIONS_ID_CHANGE_STATE_PATH = "$PANEL_BASE_PATH${PanelReservationApi.RESERVATIONS_ID_CHANGE_STATE_PATH}"

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
        val result = mvc.perform(
            MockMvcRequestBuilders.post(RESERVATIONS_PATH, 1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(body)
        )
            .andExpect(MockMvcResultMatchers.status().isCreated)
            .andReturn()

        val actual = convertJsonBytesToObject(result.response.contentAsString, PanelReservationsDTO::class.java)

        val expectedReservation = PanelReservationDTO(
            id = 10,
            customer = ReservationCustomerDTO(
                firstName = "NameStub",
                lastName = "SurnameStub",
                email = "stub@test.pl",
                phoneNumber = "123123123"
            ),
            peopleNumber = 3,
            dateTime = LocalDateTime.of(LocalDate.of(2018, 3, 30), LocalTime.of(14, 0)),
            spots = listOf(
                ReservationSpotInfoDTO(
                    id = 1,
                    number = 1,
                    floorId = 1,
                    floorName = "Parter"
                )
            ),
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
        val result = mvc.perform(
            MockMvcRequestBuilders.post(RESERVATIONS_PATH, 1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(body)
        )
            .andExpect(MockMvcResultMatchers.status().isCreated)
            .andReturn()

        val actual = convertJsonBytesToObject(result.response.contentAsString, PanelReservationsDTO::class.java)

        val expectedReservation = PanelReservationDTO(
            id = 11,
            customer = ReservationCustomerDTO(
                firstName = "Piano Bar Restaurant & Cafe",
                lastName = "",
                email = "pianobar@gmail.com",
                phoneNumber = "780199283"
            ),
            peopleNumber = 3,
            dateTime = LocalDateTime.of(LocalDate.of(2018, 3, 30), LocalTime.of(14, 0)),
            spots = listOf(
                ReservationSpotInfoDTO(
                    id = 1,
                    number = 1,
                    floorId = 1,
                    floorName = "Parter"
                )
            ),
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
        val result = mvc.perform(
            MockMvcRequestBuilders.post(RESERVATIONS_PATH, 1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(body)
        )

        result.andExpect(MockMvcResultMatchers.status().isBadRequest)
    }

    @Test
    fun `addReservation_ invalid spot`() {
        val createReservationStub = PanelCreateReservationDTOStub.getPanelCreateReservationDTOStub()
        createReservationStub.spots = listOf(500)
        val body = convertObjectToJsonBytes(createReservationStub)
        val result = mvc.perform(
            MockMvcRequestBuilders.post(RESERVATIONS_PATH, 1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(body)
        )

        result.andExpect(MockMvcResultMatchers.status().isNotFound)
    }

    @Test
    fun `addReservation_ date in the past`() {
        val createReservationStub = PanelCreateReservationDTOStub.getPanelCreateReservationDTOStub()
        createReservationStub.dateTime = LocalDateTime.of(LocalDate.of(2016, 1, 1), LocalTime.of(12, 0))
        val body = convertObjectToJsonBytes(createReservationStub)
        val result = mvc.perform(
            MockMvcRequestBuilders.post(RESERVATIONS_PATH, 1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(body)
        )

        result.andExpect(MockMvcResultMatchers.status().isBadRequest)
    }

    @Test
    fun `addReservation_ time is indivisible by 15`() {
        val createReservationStub = PanelCreateReservationDTOStub.getPanelCreateReservationDTOStub()
        createReservationStub.dateTime = LocalDateTime.of(LocalDate.of(2018, 3, 30), LocalTime.of(14, 1))
        val body = convertObjectToJsonBytes(createReservationStub)
        val result = mvc.perform(
            MockMvcRequestBuilders.post(RESERVATIONS_PATH, 1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(body)
        )

        result.andExpect(MockMvcResultMatchers.status().isBadRequest)
    }

    @Test
    fun `addReservation_ taken spot`() {
        val createReservationStub = PanelCreateReservationDTOStub.getPanelCreateReservationDTOStub()
        val body = convertObjectToJsonBytes(createReservationStub)
        mvc.perform(
            MockMvcRequestBuilders.post(RESERVATIONS_PATH, 1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(body)
        )
            .andExpect(MockMvcResultMatchers.status().isCreated)

        val actualCreateReservationStub = PanelCreateReservationDTOStub.getPanelCreateReservationDTOStub()
        val actualBody = convertObjectToJsonBytes(actualCreateReservationStub)
        val actualResult = mvc.perform(
            MockMvcRequestBuilders.post(RESERVATIONS_PATH, 1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(actualBody)
        )
        actualResult.andExpect(MockMvcResultMatchers.status().isBadRequest)
    }

    @Test
    fun `addReservation_ existing customer with other data`() {
        val createReservationStub = PanelCreateReservationDTOStub.getPanelCreateReservationDTOStub()
        createReservationStub.customer.phoneNumber = "666894323"
        val body = convertObjectToJsonBytes(createReservationStub)
        val result = mvc.perform(
            MockMvcRequestBuilders.post(RESERVATIONS_PATH, 1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(body)
        )
            .andExpect(MockMvcResultMatchers.status().isCreated)
            .andReturn()

        val actual = convertJsonBytesToObject(result.response.contentAsString, PanelReservationsDTO::class.java)

        val expectedReservation = PanelReservationDTO(
            id = 12,
            customer = ReservationCustomerDTO(
                firstName = "Karola",
                lastName = "Szafrańska",
                email = "karola.szafranska@gmail.pl",
                phoneNumber = "666894323"
            ),
            peopleNumber = 3,
            dateTime = LocalDateTime.of(LocalDate.of(2018, 3, 30), LocalTime.of(14, 0)),
            spots = listOf(
                ReservationSpotInfoDTO(
                    id = 1,
                    number = 1,
                    floorId = 1,
                    floorName = "Parter"
                )
            ),
            note = "NoteStub",
            state = Reservation.ReservationState.ACCEPTED
        )

        assertEquals(LocalTime.of(13, 0), actual.openTime)
        assertEquals(LocalTime.of(23, 0), actual.closeTime)
        assertEquals(1, actual.reservations.count())
        assertEquals(expectedReservation, actual.reservations[0])
    }

    @Test
    fun `getReservations_ no reservations`() {
        val result = mvc.perform(
            MockMvcRequestBuilders.get(RESERVATIONS_PATH, 1)
                .param(PanelReservationController.API.DATE, LocalDate.of(2020, 10, 18).toString())
        )
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andReturn()

        val actual = convertJsonBytesToObject(result.response.contentAsString, PanelReservationsDTO::class.java)
        assertEquals(LocalTime.of(13, 0), actual.openTime)
        assertEquals(LocalTime.of(20, 0), actual.closeTime)
        assertEquals(0, actual.reservations.count())
    }

    @Test
    fun `getReservations_ with one reservation`() {
        val result = mvc.perform(
            MockMvcRequestBuilders.get(RESERVATIONS_PATH, 1)
                .param(PanelReservationController.API.DATE, LocalDate.of(2018, 10, 18).toString())
        )
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andReturn()

        val actual = convertJsonBytesToObject(result.response.contentAsString, PanelReservationsDTO::class.java)
        assertEquals(LocalTime.of(12, 0), actual.openTime)
        assertEquals(LocalTime.of(21, 0), actual.closeTime)
        assertEquals(1, actual.reservations.count())
    }

    @Test
    fun `getReservations_ with multiple reservations`() {
        val result = mvc.perform(
            MockMvcRequestBuilders.get(RESERVATIONS_PATH, 1)
                .param(PanelReservationController.API.DATE, LocalDate.of(2017, 10, 18).toString())
        )
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andReturn()

        val actual = convertJsonBytesToObject(result.response.contentAsString, PanelReservationsDTO::class.java)
        assertEquals(LocalTime.of(12, 0), actual.openTime)
        assertEquals(LocalTime.of(21, 0), actual.closeTime)
        assertEquals(2, actual.reservations.count())
    }

    @Test
    fun `getReservation_ not existing`() {
        mvc.perform(MockMvcRequestBuilders.get(RESERVATIONS_ID_PATH, 1, 100))
            .andExpect(MockMvcResultMatchers.status().isNotFound)
    }

    @Test
    fun `getReservation_ correct data`() {
        val result = mvc.perform(MockMvcRequestBuilders.get(RESERVATIONS_ID_PATH, 1, 1))
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andReturn()

        val actual = convertJsonBytesToObject(result.response.contentAsString, PanelReservationDTO::class.java)
        assertEquals("Karola", actual.customer.firstName)
        assertEquals("Szafrańska", actual.customer.lastName)
        assertEquals("karola.szafranska@gmail.pl", actual.customer.email)
        assertEquals("666894323", actual.customer.phoneNumber)
        assertNotNull(actual.customer)
        assertEquals(5, actual.peopleNumber)
        assertEquals(LocalTime.of(14, 45), actual.dateTime.toLocalTime())
        assertEquals(3, actual.spots[0].id)
        assertEquals("Parter", actual.spots[0].floorName)
        assertNull(actual.note)
        assertEquals(Reservation.ReservationState.FINISHED, actual.state)
    }


    @Test
    fun `editReservation_ correct data`() {
        val createReservationStub = PanelCreateReservationDTOStub.getPanelCreateReservationDTOStub()
        val body = convertObjectToJsonBytes(createReservationStub)
        val result = mvc.perform(
            MockMvcRequestBuilders.put(RESERVATIONS_ID_PATH, 1, 1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(body)
        )
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
            dateTime = LocalDateTime.of(LocalDate.of(2018, 3, 30), LocalTime.of(14, 0)),
            spots = listOf(
                ReservationSpotInfoDTO(
                    id = 1,
                    number = 1,
                    floorId = 1,
                    floorName = "Parter"
                )
            ),
            note = "NoteStub",
            state = Reservation.ReservationState.ACCEPTED
        )

        assertEquals(LocalTime.of(13, 0), actual.openTime)
        assertEquals(LocalTime.of(23, 0), actual.closeTime)
        assertEquals(1, actual.reservations.count())
        assertEquals(expectedReservation, actual.reservations[0])
    }

    @Test
    fun `editReservation_ empty customer`() {
        val createReservationStub = PanelCreateReservationDTOStub.getPanelCreateReservationDTOStub()
        createReservationStub.customer = ReservationCustomerDTO(
            firstName = null,
            lastName = null,
            email = null,
            phoneNumber = null
        )
        val body = convertObjectToJsonBytes(createReservationStub)
        val result = mvc.perform(
            MockMvcRequestBuilders.put(RESERVATIONS_ID_PATH, 1, 1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(body)
        )
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andReturn()

        val actual = convertJsonBytesToObject(result.response.contentAsString, PanelReservationsDTO::class.java)

        val expectedReservation = PanelReservationDTO(
            id = 1,
            customer = ReservationCustomerDTO(
                firstName = "Piano Bar Restaurant & Cafe",
                lastName = "",
                email = "pianobar@gmail.com",
                phoneNumber = "780199283"
            ),
            peopleNumber = 3,
            dateTime = LocalDateTime.of(LocalDate.of(2018, 3, 30), LocalTime.of(14, 0)),
            spots = listOf(
                ReservationSpotInfoDTO(
                    id = 1,
                    number = 1,
                    floorId = 1,
                    floorName = "Parter"
                )
            ),
            note = "NoteStub",
            state = Reservation.ReservationState.ACCEPTED
        )

        assertEquals(LocalTime.of(13, 0), actual.openTime)
        assertEquals(LocalTime.of(23, 0), actual.closeTime)
        assertEquals(1, actual.reservations.count())
        assertEquals(expectedReservation, actual.reservations[0])
    }

    @Test
    fun `editReservation_ invalid email`() {
        val createReservationStub = PanelCreateReservationDTOStub.getPanelCreateReservationDTOStub()
        createReservationStub.customer.email = "invalidEmail"
        val body = convertObjectToJsonBytes(createReservationStub)
        val result = mvc.perform(
            MockMvcRequestBuilders.put(RESERVATIONS_ID_PATH, 1, 1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(body)
        )

        result.andExpect(MockMvcResultMatchers.status().isBadRequest)
    }

    @Test
    fun `editReservation_ invalid spot`() {
        val createReservationStub = PanelCreateReservationDTOStub.getPanelCreateReservationDTOStub()
        createReservationStub.spots = listOf(500)
        val body = convertObjectToJsonBytes(createReservationStub)
        val result = mvc.perform(
            MockMvcRequestBuilders.put(RESERVATIONS_ID_PATH, 1, 1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(body)
        )

        result.andExpect(MockMvcResultMatchers.status().isNotFound)
    }

    @Test
    fun `editReservation_ date in the past`() {
        val createReservationStub = PanelCreateReservationDTOStub.getPanelCreateReservationDTOStub()
        createReservationStub.dateTime = LocalDateTime.of(LocalDate.of(2016, 1, 1), LocalTime.of(12, 0))
        val body = convertObjectToJsonBytes(createReservationStub)
        val result = mvc.perform(
            MockMvcRequestBuilders.put(RESERVATIONS_ID_PATH, 1, 1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(body)
        )

        result.andExpect(MockMvcResultMatchers.status().isBadRequest)
    }

    @Test
    fun `editReservation_ time is indivisible by 15`() {
        val createReservationStub = PanelCreateReservationDTOStub.getPanelCreateReservationDTOStub()
        createReservationStub.dateTime = LocalDateTime.of(LocalDate.of(2018, 3, 30), LocalTime.of(14, 1))
        val body = convertObjectToJsonBytes(createReservationStub)
        val result = mvc.perform(
            MockMvcRequestBuilders.put(RESERVATIONS_ID_PATH, 1, 1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(body)
        )

        result.andExpect(MockMvcResultMatchers.status().isBadRequest)
    }

    @Test
    fun `editReservation_ taken spot`() {
        val createReservationStub = PanelCreateReservationDTOStub.getPanelCreateReservationDTOStub()
        val body = convertObjectToJsonBytes(createReservationStub)
        mvc.perform(
            MockMvcRequestBuilders.post(RESERVATIONS_PATH, 1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(body)
        )
            .andExpect(MockMvcResultMatchers.status().isCreated)

        val actualCreateReservationStub = PanelCreateReservationDTOStub.getPanelCreateReservationDTOStub()
        val actualBody = convertObjectToJsonBytes(actualCreateReservationStub)
        val actualResult = mvc.perform(
            MockMvcRequestBuilders.put(RESERVATIONS_ID_PATH, 1, 1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(actualBody)
        )
        actualResult.andExpect(MockMvcResultMatchers.status().isBadRequest)
    }

    @Test
    fun `editReservation_ existing customer with other data`() {
        val createReservationStub = PanelCreateReservationDTOStub.getPanelCreateReservationDTOStub()
        createReservationStub.customer.phoneNumber = "666894323"
        val body = convertObjectToJsonBytes(createReservationStub)
        val result = mvc.perform(
            MockMvcRequestBuilders.put(RESERVATIONS_ID_PATH, 1, 1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(body)
        )
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andReturn()

        val actual = convertJsonBytesToObject(result.response.contentAsString, PanelReservationsDTO::class.java)

        val expectedReservation = PanelReservationDTO(
            id = 1,
            customer = ReservationCustomerDTO(
                firstName = "Karola",
                lastName = "Szafrańska",
                email = "karola.szafranska@gmail.pl",
                phoneNumber = "666894323"
            ),
            peopleNumber = 3,
            dateTime = LocalDateTime.of(LocalDate.of(2018, 3, 30), LocalTime.of(14, 0)),
            spots = listOf(
                ReservationSpotInfoDTO(
                    id = 1,
                    number = 1,
                    floorId = 1,
                    floorName = "Parter"
                )
            ),
            note = "NoteStub",
            state = Reservation.ReservationState.ACCEPTED
        )

        assertEquals(LocalTime.of(13, 0), actual.openTime)
        assertEquals(LocalTime.of(23, 0), actual.closeTime)
        assertEquals(1, actual.reservations.count())
        assertEquals(expectedReservation, actual.reservations[0])
    }

    @Test
    fun `cancelReservation_ existing reservation`() {
        mvc.perform(MockMvcRequestBuilders.delete(RESERVATIONS_ID_PATH, 1, 2))
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andReturn()

        val canceledReservation = reservationRepository.findOne(2)
        assertEquals(Reservation.ReservationState.CANCELED, canceledReservation.state)
    }

    @Test
    fun `cancelReservation_ not existing reservation`() {
        mvc.perform(MockMvcRequestBuilders.delete(RESERVATIONS_ID_PATH, 1, 500))
            .andExpect(MockMvcResultMatchers.status().isNotFound)
    }

    @Test
    fun `changeReservationState not existing reservation`() {
        val stateDTOStub = ReservationStateDTO(Reservation.ReservationState.ACCEPTED)
        val body = convertObjectToJsonBytes(stateDTOStub)
        mvc.perform(
            MockMvcRequestBuilders.put(RESERVATIONS_ID_CHANGE_STATE_PATH, 1, 500)
                .contentType(MediaType.APPLICATION_JSON)
                .content(body)
        )
            .andExpect(MockMvcResultMatchers.status().isNotFound)
            .andReturn()
    }

    @Test
    fun `changeReservationState correct data`() {
        val stateDTOStub = ReservationStateDTO(Reservation.ReservationState.CANCELED)
        val body = convertObjectToJsonBytes(stateDTOStub)
        val result = mvc.perform(
            MockMvcRequestBuilders.put(RESERVATIONS_ID_CHANGE_STATE_PATH, 1, 1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(body)
        )
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andReturn()

        val actual = convertJsonBytesToObject(result.response.contentAsString, PanelReservationDTO::class.java)
        assertEquals(Reservation.ReservationState.CANCELED, actual.state)
    }

    @Test
    fun `changeReservationState empty state`() {
        val stateDTOStub = ReservationStateDTO(Reservation.ReservationState.CANCELED)
        val body = convertObjectToJsonBytes(stateDTOStub).replace("\"state\":\"CANCELED\"", "\"state\":\"\"")
        mvc.perform(
            MockMvcRequestBuilders.put(RESERVATIONS_ID_CHANGE_STATE_PATH, 1, 1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(body)
        )
            .andExpect(MockMvcResultMatchers.status().isBadRequest)
            .andReturn()
    }

    @Test
    fun `changeReservationState not a ReservationState`() {
        val stateDTOStub = ReservationStateDTO(Reservation.ReservationState.CANCELED)
        val body = convertObjectToJsonBytes(stateDTOStub).replace("\"state\":\"CANCELED\"", "\"state\":\"CANCELEDs\"")
        mvc.perform(
            MockMvcRequestBuilders.put(RESERVATIONS_ID_CHANGE_STATE_PATH, 1, 1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(body)
        )
            .andExpect(MockMvcResultMatchers.status().isBadRequest)
            .andReturn()
    }
}