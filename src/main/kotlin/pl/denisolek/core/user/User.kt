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

        @Column(updatable = false, nullable = false)
        var email: String,

        var password: String,
        var name: String,
        var surname: String,
        var companyName: String,
        var accountState: AccountState,

        @ManyToMany(fetch = FetchType.EAGER)
        @JoinTable(name = "user_authority", joinColumns = arrayOf(JoinColumn(name = "username")), inverseJoinColumns = arrayOf(JoinColumn(name = "authority")))
        val authorities: Set<Authority>,

        @ManyToOne
        var workPlace: Restaurant,

        @OneToMany(mappedBy = "owner", cascade = arrayOf(CascadeType.ALL), orphanRemoval = true)
        var ownedRestaurants: List<Restaurant>
) : BaseEntity() {
    enum class AccountState {
        ACTIVE,
        DISABLED,
        WAITING,
        BANNED
    }
}