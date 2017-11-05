package pl.denisolek.infrastructure.config.security

import org.springframework.context.annotation.Profile
import org.springframework.core.Ordered
import org.springframework.core.annotation.Order
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import pl.denisolek.Exception.ServiceException
import pl.denisolek.core.user.User
import pl.denisolek.core.user.UserRepository

@Service("authorizationService")
@Profile("fakeAuthorization")
@Order(Ordered.HIGHEST_PRECEDENCE)
class FakeAuthorizationService(val userRepository: UserRepository) : AuthorizationService {
    override fun getCurrentUser(): User {
        return userRepository.findOne(1) ?: throw ServiceException(HttpStatus.NOT_FOUND, "Couldn't find identity")
    }
}