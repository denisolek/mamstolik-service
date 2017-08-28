package pl.denisolek.core.address

import pl.denisolek.core.BaseEntity
import javax.persistence.CascadeType
import javax.persistence.Entity
import javax.persistence.OneToMany

@Entity
class City(
        var name: String,

        @OneToMany(mappedBy = "city", cascade = arrayOf(CascadeType.ALL), orphanRemoval = true)
        var aliases: MutableList<CityAlias> = mutableListOf()
) : BaseEntity()