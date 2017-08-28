package pl.denisolek.core.restaurant

import pl.denisolek.core.BaseEntity
import pl.denisolek.core.address.Address
import pl.denisolek.core.reservation.Reservation
import java.time.Duration
import javax.persistence.*

@Entity
class Restaurant(
        var name: String,
        var description: String,
        var avgReservationTime: Duration,
        var rate: Float,
        var service_rate: Float,
        var food_rate: Float,
        var price_quality_rate: Float,
        var isActive: Boolean,

        @OneToOne(cascade = arrayOf(CascadeType.ALL))
        var address: Address,

        @OneToMany(mappedBy = "restaurant", cascade = arrayOf(CascadeType.ALL), orphanRemoval = true)
        var reservations: MutableList<Reservation>,

        @ElementCollection(fetch = FetchType.EAGER)
        @Enumerated(EnumType.STRING)
        @CollectionTable(name = "restaurant_kitchen", joinColumns = arrayOf(JoinColumn(name = "restaurantId")))
        @Column(name = "kitchen_type", nullable = false)
        var kitchenTypes: MutableSet<KitchenType>,

        @OneToMany(cascade = arrayOf(CascadeType.ALL))
        @JoinTable(name = "restaurant_business_hour", joinColumns = arrayOf(JoinColumn(name = "restaurant_id")), inverseJoinColumns = arrayOf(JoinColumn(name = "business_hour_id")))
        var businessHours: MutableSet<BusinessHour> = mutableSetOf()

) : BaseEntity() {
    enum class KitchenType {
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
}