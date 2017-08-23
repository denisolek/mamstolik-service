package pl.denisolek.core.user

import pl.denisolek.core.BaseEntity
import pl.denisolek.core.restaurant.Restaurant
import pl.denisolek.core.security.Authority
import javax.persistence.*

@Entity
class User(

        var name: String,
        var surname: String,
        var accountState: AccountState,
        var password: String,

        @Column(updatable = false, nullable = false)
        var email: String,

        @ManyToMany(fetch = FetchType.EAGER)
        @JoinTable(name = "user_authority", joinColumns = arrayOf(JoinColumn(name = "username")), inverseJoinColumns = arrayOf(JoinColumn(name = "authority")))
        private val authorities: Set<Authority>,

        @OneToOne(cascade = arrayOf(CascadeType.ALL))
        @JoinColumn(name = "restaurant_id")
        var restaurant: Restaurant

) : BaseEntity() {
    enum class AccountState {
        ACTIVE,
        DISABLED,
        BANNED
    }
}