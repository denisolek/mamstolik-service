package pl.denisolek.core.restaurant

import org.apache.commons.lang3.StringUtils
import org.springframework.stereotype.Service
import pl.denisolek.core.address.City

@Service
class RestaurantService(private val restaurantRepository: RestaurantRepository) {
    fun findById(id: Int): Restaurant? = restaurantRepository.findOne(id)

    fun getActiveRestaurantsByCity(city: City): List<Restaurant> =
        restaurantRepository.findByCityAndIsActive(city.id)

    fun findPartlyByName(name: String): List<Restaurant> =
        restaurantRepository.findPartlyByName(name)

    fun findByUrlName(urlName: String): Restaurant? =
        restaurantRepository.findByUrlName(urlName)

    fun save(restaurant: Restaurant) =
        restaurantRepository.save(restaurant)

    fun generateUrlName(name: String): String {
        var urlName = name.replace(" ", ".").toLowerCase()
        urlName = StringUtils.stripAccents(urlName)
        urlName = replaceUmlaut(urlName)
        var urlNameIdentifier = 1
        var exists = when (restaurantRepository.countByUrlName(urlName)) {
            0 -> false
            else -> {
                urlName = "$urlName.$urlNameIdentifier"
                true
            }
        }

        while (exists) when {
            restaurantRepository.countByUrlName(urlName) == 0 -> exists = false
            else -> {
                urlNameIdentifier++
                urlName = urlName.removeRange(urlName.lastIndexOf("."), urlName.count())
                urlName = "$urlName.$urlNameIdentifier"
            }
        }
        return urlName
    }

    private fun replaceUmlaut(input: String): String {
        var output = input.replace("ü", "ue")
            .replace("ö", "oe")
            .replace("ä", "ae")
            .replace("ß", "ss")

        output = output.replace("Ü(?=[a-zäöüß ])", "Ue")
            .replace("Ö(?=[a-zäöüß ])", "Oe")
            .replace("Ä(?=[a-zäöüß ])", "Ae")

        output = output.replace("Ü", "UE")
            .replace("Ö", "OE")
            .replace("Ä", "AE")
        return output
    }
}