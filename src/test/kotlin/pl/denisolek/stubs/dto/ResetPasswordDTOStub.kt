package pl.denisolek.stubs.dto

import pl.denisolek.panel.identity.DTO.ResetPasswordDTO

class ResetPasswordDTOStub {
    companion object {
        fun getResetPasswordDTO(): ResetPasswordDTO =
            ResetPasswordDTO(
                username = "ms900000",
                password = "TestPassword123",
                resetKey = "reestKeyTest"
            )
    }
}