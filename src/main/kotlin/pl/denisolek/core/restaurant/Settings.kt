package pl.denisolek.core.restaurant

import javax.persistence.*

@Entity
class Settings(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        var id: Int? = null,
        var localization: Boolean? = false,
        var special_dates: Boolean? = false,
        var description: Boolean? = false,
        var photos: Boolean? = false,
        var menu: Boolean? = false,
        var schema: Boolean? = false
)