package pl.denisolek.core.address

import org.codehaus.jackson.annotate.JsonIgnore
import pl.denisolek.core.BaseEntity
import javax.persistence.Entity
import javax.persistence.ManyToOne

@Entity
data class CityAlias(
    var name: String,

    @ManyToOne
    @JsonIgnore
    var city: City
) : BaseEntity()