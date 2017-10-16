package pl.denisolek.stubs

import pl.denisolek.core.security.Authority
import pl.denisolek.core.user.User

class UserStub {
    companion object {
        fun getResendActivationKeyUser(): User =
                User(
                        email = "emailStub@test.pl",
                        firstName = "NameStub",
                        activationKey = "activationKeyStub",
                        authorities = setOf(Authority(Authority.Role.ROLE_OWNER)),
                        accountState = User.AccountState.NOT_ACTIVE
                )
    }
}