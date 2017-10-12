package pl.denisolek.core.user

import pl.denisolek.core.BaseEntity
import pl.denisolek.core.restaurant.Restaurant
import pl.denisolek.core.security.Authority
import javax.persistence.*

@Entity
@Table(name = "[user]")
data class User(

        @Column(updatable = false, nullable = false)
        var username: String,

        var email: String,
        var password: String,
        var name: String? = null,
        var surname: String? = null,
        var companyName: String? = null,
        var accountState: AccountState,
        var phoneNumber: String? = null,

        @ManyToMany(fetch = FetchType.EAGER)
        @JoinTable(name = "user_authority", joinColumns = arrayOf(JoinColumn(name = "username")), inverseJoinColumns = arrayOf(JoinColumn(name = "authority")))
        val authorities: Set<Authority>,

        @ManyToOne
        var workPlace: Restaurant? = null,

        @OneToMany(mappedBy = "owner", cascade = arrayOf(CascadeType.ALL), orphanRemoval = true)
        var ownedRestaurants: List<Restaurant>? = listOf(),

        @OneToOne(cascade = arrayOf(CascadeType.ALL))
        var restaurant: Restaurant? = null
) : BaseEntity() {
    enum class AccountState {
        ACTIVE,
        DISABLED,
        WAITING,
        BANNED
    }
}