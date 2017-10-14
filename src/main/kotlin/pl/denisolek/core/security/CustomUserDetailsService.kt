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

        var userFromDatabase: User? = userRepository.findByEmail(lowercaseLogin)

        if (userFromDatabase == null) {
            throw ServiceException(HttpStatus.NOT_FOUND, "User not found")
        }

        val grantedAuthorities = ArrayList<GrantedAuthority>()

        for (authority in userFromDatabase.authorities) {
            val grantedAuthority = SimpleGrantedAuthority(authority.role.toString())
            grantedAuthorities.add(grantedAuthority)
        }

        return org.springframework.security.core.userdetails.User(userFromDatabase.email, userFromDatabase.password, grantedAuthorities)
    }

}