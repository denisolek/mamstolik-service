package pl.denisolek.stubs.dto

import pl.denisolek.panel.identity.DTO.ChangePasswordDTO

class ChangePasswordDTOStub {
    companion object {
        fun getChangePasswordDTO(): ChangePasswordDTO =
            ChangePasswordDTO(
                oldPassword = "Test12345",
                newPassword = "Updated12345"
            )
    }
}