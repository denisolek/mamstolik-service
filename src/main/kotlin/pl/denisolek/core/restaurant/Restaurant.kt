package pl.denisolek.core.restaurant

import pl.denisolek.core.BaseEntity
import pl.denisolek.core.reservation.Reservation
import java.time.Duration
import java.util.*
import javax.persistence.*

@Entity
class Restaurant(
        var name: String,
        var city: String,
        var street: String,
        var latitude: Float,
        var longitude: Float,
        var description: String,
        var avgReservationTime: Duration,
        var rate: Float,
        var service_rate: Float,
        var place_rate: Float,
        var price_quality_rate: Float,
        var nip: String,
        var capacity: Int,
        var opinionCount: Int,
        var isActive: Boolean,

        @OneToMany(mappedBy = "restaurant", cascade = arrayOf(CascadeType.ALL), orphanRemoval = true)
        var reservations: MutableList<Reservation>,

        @ElementCollection(fetch = FetchType.EAGER)
        @Enumerated(EnumType.STRING)
        @CollectionTable(name = "restaurant_kitchen", joinColumns = arrayOf(JoinColumn(name = "restaurantId")))
        @Column(name = "kitchen_type", nullable = false)
        var kitchenTypes: Set<KitchenType>,

        @OneToMany(cascade = arrayOf(CascadeType.ALL))
        @JoinTable(name = "restaurant_business_hour", joinColumns = arrayOf(JoinColumn(name = "restaurant_id")), inverseJoinColumns = arrayOf(JoinColumn(name = "business_hour_id")))
        var businessHours: Set<BusinessHour> = HashSet()

) : BaseEntity() {
    enum class KitchenType {
        ITALIAN,
        BURGERS,
        ASIAN,
        JAPANESE,
        ARABIC_TURKISH,
        GREEK,
        INDIAN,
        POLISH,
        VIET_THAI,
        CHINESE,
        MEXICAN
    }
}