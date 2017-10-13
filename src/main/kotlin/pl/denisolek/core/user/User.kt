package pl.denisolek.core.user

import pl.denisolek.core.BaseEntity
import pl.denisolek.core.restaurant.Restaurant
import pl.denisolek.core.security.Authority
import javax.persistence.*

@Entity
@Table(name = "[user]")
data class User(

        @Column(updatable = false, nullable = false, unique = true)
        var username: String? = null,

        @Column(nullable = false, unique = true)
        var email: String? = null,

        var password: String? = null,
        var firstName: String? = null,
        var lastName: String? = null,
        var companyName: String? = null,
        var nip: String? = null,
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

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other?.javaClass != javaClass) return false
        if (!super.equals(other)) return false

        other as User

        if (username != other.username) return false
        if (email != other.email) return false
        if (password != other.password) return false
        if (firstName != other.firstName) return false
        if (lastName != other.lastName) return false
        if (companyName != other.companyName) return false
        if (nip != other.nip) return false
        if (accountState != other.accountState) return false
        if (phoneNumber != other.phoneNumber) return false
        if (workPlace != other.workPlace) return false
        if (ownedRestaurants != other.ownedRestaurants) return false
        return true
    }

    enum class AccountState {
        ACTIVE,
        NOT_ACTIVE,
        DISABLED,
        WAITING,
        BANNED
    }
}