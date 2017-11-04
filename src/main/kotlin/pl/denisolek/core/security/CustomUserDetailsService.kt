package pl.denisolek.core.security

import org.springframework.http.HttpStatus
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Component
import pl.denisolek.Exception.ServiceException
import pl.denisolek.core.user.User
import pl.denisolek.core.user.UserRepository


@Component("userDetailsService")
class CustomUserDetailsService(private val userRepository: UserRepository) : org.springframework.security.core.userdetails.UserDetailsService {

    @Throws(UsernameNotFoundException::class)
    override fun loadUserByUsername(username: String): UserDetails {
        val lowercaseLogin = username.toLowerCase()

        val userByEmail: User? = userRepository.findByEmail(lowercaseLogin)
        val userByUsername: User? = userRepository.findByUsername(lowercaseLogin)

        val userFromDatabase: User = (userByEmail ?: userByUsername) ?: throw ServiceException(HttpStatus.NOT_FOUND, "User not found")

        val grantedAuthorities = ArrayList<GrantedAuthority>()

        userFromDatabase.authorities.mapTo(grantedAuthorities) { SimpleGrantedAuthority(it.role.toString()) }

        return org.springframework.security.core.userdetails.User(userFromDatabase.email, userFromDatabase.password, grantedAuthorities)
    }
}