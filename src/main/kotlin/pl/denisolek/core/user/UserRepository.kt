package pl.denisolek.core.user

import org.springframework.data.jpa.repository.JpaRepository
import pl.denisolek.User.User

interface UserRepository : JpaRepository<User, Int> {
}
