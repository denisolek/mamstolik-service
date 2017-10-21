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

        fun getSetPasswordUser(): User =
                User(
                        username = "usernameStub",
                        email = "emailStub@test.pl",
                        activationKey = "activationKeyStub",
                        authorities = setOf(Authority(Authority.Role.ROLE_OWNER)),
                        accountState = User.AccountState.NOT_ACTIVE
                )

        fun getChangePasswordUser(): User =
                User(
                        username = "usernameStub",
                        email = "emailStub@test.pl",
                        password = "\$2a\$10\$IlfSzDHKiu5oOmuXVLmrXO.wAeWdK2dpmcbGHZZ1mOSKkzP/QF3uG",
                        activationKey = null,
                        authorities = setOf(Authority(Authority.Role.ROLE_OWNER)),
                        accountState = User.AccountState.ACTIVE
                )
    }
}