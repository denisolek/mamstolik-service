package pl.denisolek.core.user

import pl.denisolek.core.image.Image
import pl.denisolek.core.restaurant.Restaurant
import pl.denisolek.core.security.Authority
import javax.persistence.*

@Entity
@Table(name = "[user]")
data class User(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        var id: Int? = null,

        @Column(updatable = false, nullable = false, unique = true)
        var username: String? = null,

        @Column(nullable = false, unique = true)
        var email: String,

        var password: String? = null,
        var firstName: String? = null,
        var lastName: String? = null,
        var companyName: String? = null,
        var nip: String? = null,
        var phoneNumber: String? = null,
        var activationKey: String? = null,
        var resetPasswordKey: String? = null,

        @Enumerated(EnumType.STRING)
        var accountState: AccountState,

        @ManyToMany(fetch = FetchType.EAGER)
        @JoinTable(name = "user_authority", joinColumns = arrayOf(JoinColumn(name = "username")), inverseJoinColumns = arrayOf(JoinColumn(name = "authority")))
        val authorities: Set<Authority>,

        @ManyToOne
        var workPlace: Restaurant? = null,

        var workEmail: String? = null,

        @OneToMany(mappedBy = "owner", cascade = arrayOf(CascadeType.ALL), orphanRemoval = true)
        var ownedRestaurants: MutableList<Restaurant> = mutableListOf(),

        @OneToOne(cascade = arrayOf(CascadeType.ALL))
        var restaurant: Restaurant? = null,

        @OneToOne(cascade = arrayOf(CascadeType.ALL))
        var avatar: Image? = null
) {

    enum class AccountState {
        ACTIVE,
        NOT_ACTIVE,
        DISABLED,
        WAITING,
        BANNED
    }

    fun getRole() =
            when {
                this.authorities.contains(Authority(Authority.Role.ROLE_OWNER)) ->
                    Authority.Role.ROLE_OWNER
                this.authorities.contains(Authority(Authority.Role.ROLE_MANAGER)) ->
                    Authority.Role.ROLE_MANAGER
                else ->
                    Authority.Role.ROLE_EMPLOYEE
            }
}