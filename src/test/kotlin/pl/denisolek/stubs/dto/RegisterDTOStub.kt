package pl.denisolek.stubs.dto

import pl.denisolek.panel.user.DTO.RegisterDTO

class RegisterDTOStub {
    companion object {
        fun getRegisterDTO(): RegisterDTO =
                RegisterDTO(
                        email = "emailStub@test.pl",
                        firstName = "NameStub",
                        lastName = "LastNameStub",
                        phoneNumber = "111222333",
                        nip = "8362704639",
                        companyName = "CompanyNameStub"
                )
    }
}