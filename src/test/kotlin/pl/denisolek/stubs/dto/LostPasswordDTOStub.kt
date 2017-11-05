package pl.denisolek.stubs.dto

import pl.denisolek.panel.identity.DTO.LostPasswordDTO

class LostPasswordDTOStub {
    companion object {
        fun getLostPasswordDTO(): LostPasswordDTO =
                LostPasswordDTO(
                        email = "ms500000@mamstolik.pl"
                )
    }
}