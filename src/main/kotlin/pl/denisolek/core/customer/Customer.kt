package pl.denisolek.core.customer

import pl.denisolek.core.comment.Comment
import javax.persistence.*

@Entity
data class Customer(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        var id: Int? = null,
        var email: String,
        var phoneNumber: String,
        var firstName: String,
        var lastName: String,
        @OneToMany(mappedBy = "customer", cascade = arrayOf(CascadeType.ALL), orphanRemoval = true)
        var comments: MutableList<Comment> = mutableListOf()
)