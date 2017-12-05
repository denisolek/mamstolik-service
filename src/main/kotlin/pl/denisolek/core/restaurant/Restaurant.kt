package pl.denisolek.core.restaurant

import org.springframework.http.HttpStatus
import pl.denisolek.Exception.ServiceException
import pl.denisolek.core.address.Address
import pl.denisolek.core.floor.Floor
import pl.denisolek.core.image.Image
import pl.denisolek.core.menu.Menu
import pl.denisolek.core.reservation.Reservation
import pl.denisolek.core.reservation.Reservation.ReservationState.CANCELED
import pl.denisolek.core.spot.Spot
import pl.denisolek.core.user.User
import pl.denisolek.guest.restaurant.DTO.MenuCategoryDTO
import pl.denisolek.infrastructure.util.DateTimeInterval
import pl.denisolek.infrastructure.util.isAfterOrEqual
import pl.denisolek.infrastructure.util.isBeforeOrEqual
import java.time.*
import javax.persistence.*

@Entity
data class Restaurant(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        var id: Int? = null,
        var name: String,
        var urlName: String,
        var description: String = "",
        var phoneNumber: String,
        var email: String,
        var avgReservationTime: Duration = Duration.ofHours(1),
        var rate: Float = 0f,
        var service_rate: Float = 0f,
        var food_rate: Float = 0f,
        var price_quality_rate: Float = 0f,
        var isActive: Boolean = false,
        @Enumerated(EnumType.STRING)
        var type: RestaurantType,

        @ManyToOne
        var owner: User? = null,

        @OneToMany(mappedBy = "workPlace", cascade = arrayOf(CascadeType.ALL), orphanRemoval = true)
        var employees: MutableList<User> = mutableListOf(),

        @OneToOne(cascade = arrayOf(CascadeType.ALL))
        var address: Address? = null,

        @OneToMany(mappedBy = "restaurant", cascade = arrayOf(CascadeType.ALL), orphanRemoval = true)
        var reservations: MutableList<Reservation> = mutableListOf(),

        @OneToMany(mappedBy = "restaurant", cascade = arrayOf(CascadeType.ALL), orphanRemoval = true)
        var images: MutableList<Image> = mutableListOf(),

        @OneToOne(cascade = arrayOf(CascadeType.ALL))
        var mainImage: Image? = null,

        @OneToMany(mappedBy = "restaurant", cascade = arrayOf(CascadeType.ALL), orphanRemoval = true)
        var spots: MutableList<Spot> = mutableListOf(),

        @OneToMany(mappedBy = "restaurant", cascade = arrayOf(CascadeType.ALL), orphanRemoval = true)
        var floors: MutableList<Floor> = mutableListOf(),

        @OneToOne(cascade = arrayOf(CascadeType.ALL))
        var menu: Menu? = null,

        @OneToOne(cascade = arrayOf(CascadeType.ALL))
        var settings: Settings? = Settings(),

        @ElementCollection(fetch = FetchType.EAGER)
        @Enumerated(EnumType.STRING)
        @CollectionTable(name = "restaurant_kitchen", joinColumns = arrayOf(JoinColumn(name = "restaurantId")))
        @Column(name = "kitchen_type", nullable = false)
        var cuisineTypes: MutableSet<CuisineType> = mutableSetOf(),

        @ElementCollection(fetch = FetchType.EAGER)
        @Enumerated(EnumType.STRING)
        @CollectionTable(name = "restaurant_facility", joinColumns = arrayOf(JoinColumn(name = "restaurantId")))
        @Column(name = "facility", nullable = false)
        var facilities: MutableSet<Facilities> = mutableSetOf(),

        @OneToMany(cascade = arrayOf(CascadeType.ALL))
        @JoinTable(name = "restaurant_business_hour", joinColumns = arrayOf(JoinColumn(name = "restaurant_id")), inverseJoinColumns = arrayOf(JoinColumn(name = "business_hour_id")))
        @MapKeyEnumerated(EnumType.STRING)
        @MapKeyColumn(name = "day_of_week")
        var businessHours: MutableMap<DayOfWeek, BusinessHour> = mutableMapOf(),

        @OneToMany(mappedBy = "restaurant", cascade = arrayOf(CascadeType.ALL), orphanRemoval = true)
        var specialDates: MutableList<SpecialDate> = mutableListOf()
) {

    fun isOpenAt(date: LocalDateTime): Boolean {
        val businessHour = this.specialDates.find { it.date == date.toLocalDate() }?.businessHour ?: getBusinessHoursForDate(date.toLocalDate()) ?: return false

        return !businessHour.isClosed &&
                date.toLocalTime().isAfterOrEqual(businessHour.openTime) &&
                date.toLocalTime().isBeforeOrEqual(businessHour.closeTime.minusMinutes(this.avgReservationTime.toMinutes()))
    }

    fun getBusinessHoursForDate(date: LocalDate) = this.businessHours[date.dayOfWeek]

    fun getAvailableDates(date: LocalDateTime, peopleNumber: Int): Map<LocalDate, List<LocalTime>> {
        var searchDate = date
        val result = mutableMapOf<LocalDate, List<LocalTime>>()
        while (date.month == searchDate.month) {
            var hours = getAvailableHours(searchDate, peopleNumber)
            if (!hours.isEmpty()) {
                if (date == searchDate) hours = hours.filter { it.isAfter(date.toLocalTime()) }
                result.put(searchDate.toLocalDate(), hours)
            }
            searchDate = searchDate.plusDays(1)
        }
        return result
    }

    private fun getAvailableHours(date: LocalDateTime, peopleNumber: Int): List<LocalTime> {
        val businessHours = getBusinessHoursForDate(date.toLocalDate()) ?: return listOf()
        var searchTime = businessHours.openTime
        val result = mutableListOf<LocalTime>()
        while (searchTime.isBefore(businessHours.closeTime)) {
            when (getAvailability(LocalDateTime.of(date.toLocalDate(), searchTime), peopleNumber)) {
                AvailabilityType.AVAILABLE, AvailabilityType.POSSIBLE ->
                    result.add(searchTime)
            }
            searchTime = searchTime.plus(Duration.ofMinutes(15))
        }
        return result
    }

    fun getAvailability(date: LocalDateTime, peopleNumber: Int): AvailabilityType {
        if (!isOpenAt(date))
            return AvailabilityType.CLOSED

        val spots = getAvailableSpotsAt(date)
        spots.forEach {
            when {
                it.capacity >= peopleNumber && it.minPeopleNumber <= peopleNumber -> return AvailabilityType.AVAILABLE
                it.capacity >= peopleNumber && it.minPeopleNumber > peopleNumber -> return AvailabilityType.POSSIBLE
            }
        }
        return AvailabilityType.NOT_AVAILABLE
    }

    fun getAvailableSpotsAt(searchDate: LocalDateTime): List<Spot> {

        val searchDateInterval = object : DateTimeInterval {
            override var startDateTime: LocalDateTime = searchDate
            override var endDateTime: LocalDateTime = searchDate.plus(avgReservationTime)
        }

        val takenSpots = this.reservations
                .filter { searchDateInterval.overlaps(it) && it.state != CANCELED }
                .flatMap { it.spots }

        return this.spots.filterNot { takenSpots.contains(it) }
    }

    fun getTakenSpotsAt(searchDate: LocalDateTime): List<Spot> {

        val searchDateInterval = object : DateTimeInterval {
            override var startDateTime: LocalDateTime = searchDate
            override var endDateTime: LocalDateTime = searchDate.plus(avgReservationTime)
        }

        return this.reservations
                .filter { searchDateInterval.overlaps(it) && it.state != CANCELED }
                .flatMap { it.spots }
    }

    fun getFloor(floorId: Int): Floor =
            try {
                this.floors.first { it.id == floorId }
            } catch (e: NoSuchElementException) {
                throw ServiceException(HttpStatus.BAD_REQUEST, "Trying to assign item to not existing floor.")
            }

    fun getMenu(): List<MenuCategoryDTO>? {
        return if (this.settings!!.menu)
            this.menu?.categories?.map { MenuCategoryDTO.fromMenuCategory(it) }?.sortedBy { it.position }!!
        else
            null
    }


    enum class AvailabilityType {
        AVAILABLE,
        POSSIBLE,
        NOT_AVAILABLE,
        CLOSED
    }

    enum class CuisineType {
        POLISH,
        ITALIAN,
        SPANISH,
        PORTUGUESE,
        GREEK,
        FRENCH,
        ASIAN,
        VIETNAMESE,
        CHINESE,
        JAPANESE,
        EUROPEAN,
        AMERICAN,
        DUMPLINGS,
        PANCAKES,
        FAST_FOOD,
        BURGERS,
        SUSHI,
        PIZZA,
        HEALTHY_FOOD,
        VEGETARIAN,
        VEGAN,
        STEAK_HOUSE,
        MEXICAN,
        ENGLISH,
        CZECH,
        CROATIAN,
        BULGARIAN,
        UKRAINIAN,
        LITHUANIAN,
        AUSTRIAN,
        GERMAN,
        BELGIAN,
        RUSSIAN,
        HUNGARIAN,
        SLOVENIAN,
        INDIAN,
        TURKISH,
        THAI,
        INDONESIAN,
        SILESIAN,
        PASTA,
        SOUP,
        GRILLED_DISHES,
        BREAKFAST,
        DESSERTS,
        GLUTEN_FREE,
        BAR,
        CAFE
    }

    enum class Facilities {
        AIR_CONDITIONING,
        SMOKING_ROOM,
        NON_SMOKING_ROOM,
        TERRACE,
        GARDEN,
        PARKING,
        FREE_WIFI,
        TOILET_FOR_DISABLED,
        DISABLED_PEOPLE_IMPROVEMENTS,
        CHILDREN_MENU,
        CHILD_SEATS,
        PLAYGROUND,
        MONITORED_PARKING,
        BABY_CORNER,
        PETS_FRIENDLY,
        BILLIARDS,
        DARTS,
        TV,
        BICYCLES_STANDS,
        VIP_ROOM,
        SEPARATE_BANQUET_HALL,
        CLOSE_TO_HOTEL,
        ALCOHOL,
        TAKEAWAY,
        PAYMENT_AMERICAN_EXPRESS,
        PAYMENT_MASTERCARD,
        PAYMENT_VISA,
        BOWLING,
        PLASTIC_PLATES,
        BOILING_WATER,
        BABY_TOILET,
        PRESS,
        SPORTS_BROADCAST
    }

    enum class RestaurantType {
        RESTAURANT,
        BAR,
        PUB,
        CAFETERIA,
        EATING_HOUSE,
        OTHER
    }
}