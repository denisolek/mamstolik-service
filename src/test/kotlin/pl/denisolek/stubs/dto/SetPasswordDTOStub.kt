package pl.denisolek.stubs.dto

import pl.denisolek.panel.user.DTO.SetPasswordDTO

class SetPasswordDTOStub {
    companion object {
        fun getSetPasswordDTO(): SetPasswordDTO =
                SetPasswordDTO(
                        username = "ms800000",
                        password = "TestPassword123",
                        activationKey = "activationKeyTest"
                )
    }
}