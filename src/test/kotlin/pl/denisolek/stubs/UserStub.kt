package pl.denisolek.stubs

import pl.denisolek.core.restaurant.Restaurant
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

        fun getUserOwner(): User =
                User(
                        username = "usernameStub",
                        email = "emailStub@test.pl",
                        password = "\$2a\$10\$IlfSzDHKiu5oOmuXVLmrXO.wAeWdK2dpmcbGHZZ1mOSKkzP/QF3uG",
                        activationKey = null,
                        authorities = setOf(Authority(Authority.Role.ROLE_OWNER)),
                        accountState = User.AccountState.ACTIVE
                )

        fun getUserEmployee(): User =
                User(
                        username = "usernameStub",
                        email = "emailStub@test.pl",
                        password = "\$2a\$10\$IlfSzDHKiu5oOmuXVLmrXO.wAeWdK2dpmcbGHZZ1mOSKkzP/QF3uG",
                        activationKey = null,
                        authorities = setOf(Authority(Authority.Role.ROLE_EMPLOYEE)),
                        accountState = User.AccountState.ACTIVE
                )

        fun getUserRestaurant(): User =
                User(
                        username = "usernameStub",
                        email = "emailStub@test.pl",
                        password = "\$2a\$10\$IlfSzDHKiu5oOmuXVLmrXO.wAeWdK2dpmcbGHZZ1mOSKkzP/QF3uG",
                        activationKey = null,
                        authorities = setOf(Authority(Authority.Role.ROLE_RESTAURANT)),
                        accountState = User.AccountState.ACTIVE,
                        restaurant = getRestaurant()
                )

        private fun getRestaurant(): Restaurant =
                RestaurantStub.getRestaurant().copy(
                        employees = mutableListOf(
                                getUserEmployee().copy(id = 10, username = "StubOne", firstName = "Stub", lastName = "One", phoneNumber = "111000000"),
                                getUserEmployee().copy(id = 20, username = "StubTwo", firstName = "Stub", lastName = "Two", phoneNumber = "222000000"),
                                getUserEmployee().copy(id = 30, username = "StubThree", firstName = "Stub", lastName = "Three", phoneNumber = "333000000"),
                                getUserEmployee().copy(id = 40, username = "StubFour", firstName = "Stub", lastName = "Four", phoneNumber = "444000000")
                        )
                )
    }
}