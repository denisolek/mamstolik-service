package pl.denisolek.infrastructure.config.security

import org.springframework.context.annotation.Profile
import org.springframework.http.HttpStatus
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.oauth2.provider.OAuth2Authentication
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails
import org.springframework.stereotype.Service
import pl.denisolek.Exception.ServiceException
import pl.denisolek.core.user.User
import pl.denisolek.core.user.UserRepository

@Service("authorizationService")
@Profile("!fakeAuthorization")
class RealAuthorizationService(val userRepository: UserRepository): AuthorizationService {
    override fun getCurrentUser(): User {
        val authentication = SecurityContextHolder.getContext().authentication
        val user = ((authentication as OAuth2Authentication).details as OAuth2AuthenticationDetails).decodedDetails as User
        if (user.username != null )
            return userRepository.findByUsername(user.username!!)
        else
            throw ServiceException(HttpStatus.NOT_FOUND, "Couldn't find user")

    }
}