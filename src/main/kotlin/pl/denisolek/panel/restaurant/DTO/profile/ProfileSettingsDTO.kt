package pl.denisolek.panel.restaurant.DTO.profile

data class ProfileSettingsDTO(
        var description: Boolean = false,
        var photos: Boolean = false,
        var menu: Boolean = false
)