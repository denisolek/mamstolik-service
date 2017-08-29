package pl.denisolek.core.security

import javax.persistence.Entity
import javax.persistence.Id

@Entity
class Authority(@Id
                val name: String)