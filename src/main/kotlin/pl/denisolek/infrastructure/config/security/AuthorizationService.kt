package pl.denisolek.infrastructure.config.security

import pl.denisolek.core.user.User

interface AuthorizationService {
    fun getCurrentUser(): User
}