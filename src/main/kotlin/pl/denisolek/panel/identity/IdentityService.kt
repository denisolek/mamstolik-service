package pl.denisolek.panel.identity

import org.apache.commons.lang3.RandomStringUtils
import org.springframework.http.HttpStatus
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import pl.denisolek.Exception.ServiceException
import pl.denisolek.core.address.Address
import pl.denisolek.core.address.CityService
import pl.denisolek.core.email.EmailService
import pl.denisolek.core.floor.Floor
import pl.denisolek.core.restaurant.Restaurant
import pl.denisolek.core.restaurant.RestaurantService
import pl.denisolek.core.schema.SchemaItem
import pl.denisolek.core.security.Authority
import pl.denisolek.core.spot.Spot
import pl.denisolek.core.user.User
import pl.denisolek.core.user.UserService
import pl.denisolek.infrastructure.config.security.AuthorizationService
import pl.denisolek.panel.identity.DTO.*

@Service
class IdentityService(private val userService: UserService,
                      private val emailService: EmailService,
                      private val authorizationService: AuthorizationService,
                      private val passwordEncoder: PasswordEncoder,
                      private val restaurantService: RestaurantService,
                      private val cityService: CityService) {
    fun registerOwner(registerDTO: RegisterDTO) {
        val username = userService.generateUsername()
        val newUser = userService.save(registerDTO.toUser().copy(
                username = username,
                email = registerDTO.email.toLowerCase()
        ))
        Thread { emailService.registerOwner(newUser) }.start()
    }

    fun resendActivationKey(email: String) {
        val user: User? = userService.findByEmail(email)

        if (user?.activationKey != null) Thread { emailService.registerOwner(user) }.start()
    }

    fun setPassword(setPasswordDTO: SetPasswordDTO) {
        val user = userService.findByUsername(setPasswordDTO.username) ?: throw ServiceException(HttpStatus.NOT_FOUND, "User not found.")

        when {
            user.activationKey == setPasswordDTO.activationKey -> {
                user.password = passwordEncoder.encode(setPasswordDTO.password)
                user.activationKey = null
                user.accountState = User.AccountState.ACTIVE
                userService.save(user)
            }
            else -> throw ServiceException(HttpStatus.BAD_REQUEST, "Activation key doesn't match or password is already set.")
        }
    }

    fun changePassword(changePasswordDTO: ChangePasswordDTO) {
        val user = authorizationService.getCurrentUser()
        if (passwordEncoder.matches(changePasswordDTO.oldPassword, user.password))
            user.password = passwordEncoder.encode(changePasswordDTO.newPassword)
        else
            throw ServiceException(HttpStatus.BAD_REQUEST, "Old password doesn't match")
        userService.save(user)
    }

    fun lostPassword(lostPasswordDTO: LostPasswordDTO) {
        val user = userService.findByEmail(lostPasswordDTO.email.toLowerCase())

        if (user != null && user.authorities.contains(Authority(Authority.Role.ROLE_OWNER))) {
            val resetKey = RandomStringUtils.randomAlphabetic(30)
            user.resetPasswordKey = passwordEncoder.encode(resetKey)
            userService.save(user)
            Thread { emailService.lostPassword(user, resetKey) }.start()
        }
    }

    fun resetPassword(resetPasswordDTO: ResetPasswordDTO) {
        val user = userService.findByUsername(resetPasswordDTO.username)

        if (user == null || !passwordEncoder.matches(resetPasswordDTO.resetKey, user.resetPasswordKey))
            throw ServiceException(HttpStatus.BAD_REQUEST, "Reset key doesn't match this user")

        if (user.accountState == User.AccountState.NOT_ACTIVE) {
            user.accountState = User.AccountState.ACTIVE
        }

        user.resetPasswordKey = null
        user.activationKey = null
        user.password = passwordEncoder.encode(resetPasswordDTO.password)
        userService.save(user)
    }

    fun getRestaurants(): List<UserRestaurantDTO> {
        val user = authorizationService.getCurrentUser()
        return user.ownedRestaurants.map {
            UserRestaurantDTO.fromRestaurant(it)
        }
    }

    fun getEmployees(): List<RestaurantEmployeeDTO> {
        val user = authorizationService.getCurrentUser()
        val restaurant = user.restaurant!!
        return listOf(
                restaurant.employees.map { RestaurantEmployeeDTO.fromUser(it) },
                listOf(RestaurantEmployeeDTO.fromUser(restaurant.owner!!, true))
        ).flatten()
    }

    fun createRestaurant(createRestaurantDTO: CreateRestaurantDTO): UserRestaurantDTO {
        val restaurant = Restaurant(
                name = createRestaurantDTO.name,
                urlName = restaurantService.generateUrlName(createRestaurantDTO.name),
                type = createRestaurantDTO.type,
                owner = authorizationService.getCurrentUser(),
                phoneNumber = createRestaurantDTO.phoneNumber,
                email = createRestaurantDTO.email,
                address = Address(latitude = 52.402675f, longitude = 16.923123f, city = cityService.findByNameIgnoreCase("poznań")),
                isActive = true // TODO it shouldnt be active as default
        )
        restaurant.floors = createDefaultFloor(restaurant)
        restaurant.settings.schema = true
        userService.save(User(
                username = userService.generateUsername(),
                email = createRestaurantDTO.email,
                password = passwordEncoder.encode(createRestaurantDTO.password),
                authorities = setOf(Authority(Authority.Role.ROLE_RESTAURANT)),
                accountState = User.AccountState.ACTIVE,
                restaurant = restaurant
        ))
        return UserRestaurantDTO.fromRestaurant(restaurant)
    }

    fun createDefaultFloor(restaurant: Restaurant): MutableList<Floor> {
        val floor = Floor(
                name = "Parter",
                restaurant = restaurant
        )
        floor.schemaItems = mutableListOf(
                createDefaultTables(floor, restaurant),
                createDefaultWalls(floor),
                createDefaultItems(floor),
                createDefaultWallItems(floor)
        ).flatten().toMutableList()
        return mutableListOf(floor)
    }

    fun getRestaurant(urlName: String): RestaurantLoginDTO {
        val restaurant = restaurantService.findByUrlName(urlName) ?: throw ServiceException(HttpStatus.NOT_FOUND, "Restaurant not found")
        val user = userService.findByRestaurant(restaurant) ?: throw ServiceException(HttpStatus.NOT_FOUND, "Restaurant user not found")
        return RestaurantLoginDTO.fromRestaurantUser(user, restaurant)
    }

    private fun createDefaultTables(floor: Floor, restaurant: Restaurant): MutableList<SchemaItem> {
        return mutableListOf(
                SchemaItem(
                        x = 837.999966666667f,
                        y = 1008.0000000000002f,
                        floor = floor,
                        spot = Spot(
                                restaurant = restaurant,
                                minPeopleNumber = 1,
                                capacity = 2,
                                number = 1
                        ),
                        width = 200f,
                        height = 200f,
                        rotation = 0f,
                        tableType = SchemaItem.TableType.TWO,
                        type = SchemaItem.Type.TABLE
                ),
                SchemaItem(
                        x = 833f,
                        y = 533.6666666666666f,
                        floor = floor,
                        spot = Spot(
                                restaurant = restaurant,
                                minPeopleNumber = 3,
                                capacity = 5,
                                number = 2
                        ),
                        width = 200f,
                        height = 200f,
                        rotation = 0f,
                        tableType = SchemaItem.TableType.FIVE_RECT_2,
                        type = SchemaItem.Type.TABLE
                ),
                SchemaItem(
                        x = 1974.9999333333335f,
                        y = 878.6667333333335f,
                        floor = floor,
                        spot = Spot(
                                restaurant = restaurant,
                                minPeopleNumber = 5,
                                capacity = 6,
                                number = 3
                        ),
                        width = 200f,
                        height = 200f,
                        rotation = 0f,
                        tableType = SchemaItem.TableType.SIX_RECT_2,
                        type = SchemaItem.Type.TABLE
                ),
                SchemaItem(
                        x = 1725.3332666666665f,
                        y = 1107f,
                        floor = floor,
                        spot = Spot(
                                restaurant = restaurant,
                                minPeopleNumber = 2,
                                capacity = 4,
                                number = 4
                        ),
                        width = 200f,
                        height = 200f,
                        rotation = 0f,
                        tableType = SchemaItem.TableType.FOUR_RECT,
                        type = SchemaItem.Type.TABLE
                ))
    }

    private fun createDefaultWallItems(floor: Floor): MutableList<SchemaItem> {
        return mutableListOf(
                SchemaItem(
                        floor = floor,
                        x = 967.3333666666665f,
                        y = 1371.6666f,
                        width = 450f,
                        height = 48f,
                        rotation = 0f,
                        type = SchemaItem.Type.WALL_ITEM,
                        wallItemType = SchemaItem.WallItemType.DOOR
                ),
                SchemaItem(
                        floor = floor,
                        x = 1614.3334f,
                        y = 1373f,
                        width = 450f,
                        height = 48f,
                        rotation = 0f,
                        type = SchemaItem.Type.WALL_ITEM,
                        wallItemType = SchemaItem.WallItemType.WINDOW
                )
        )
    }

    private fun createDefaultItems(floor: Floor): MutableList<SchemaItem> {
        return mutableListOf(
                SchemaItem(
                        floor = floor,
                        x = 1489.3334f,
                        y = 499.33334f,
                        width = 200f,
                        height = 200f,
                        rotation = 0f,
                        type = SchemaItem.Type.ITEM,
                        itemType = SchemaItem.ItemType.TOILET
                )
        )
    }

    private fun createDefaultWalls(floor: Floor): MutableList<SchemaItem> {
        return mutableListOf(
                SchemaItem(
                        floor = floor,
                        x = 671.3333f,
                        y = 1371.6666f,
                        width = 1606.6666f,
                        height = 50f,
                        rotation = 0f,
                        type = SchemaItem.Type.WALL
                ),
                SchemaItem(
                        floor = floor,
                        x = 671f,
                        y = 366f,
                        width = 1173.3334f,
                        height = 50f,
                        rotation = 0f,
                        type = SchemaItem.Type.WALL
                ),
                SchemaItem(
                        floor = floor,
                        x = 187.66527515908192f,
                        y = 892.6378742659563f,
                        width = 1050.0000666666667f,
                        height = 50f,
                        rotation = 89.52029335370797f,
                        type = SchemaItem.Type.WALL
                ),
                SchemaItem(
                        floor = floor,
                        x = 1650.3793f,
                        y = 569.986f,
                        width = 410f,
                        height = 50f,
                        rotation = 89.06547f,
                        type = SchemaItem.Type.WALL
                ),
                SchemaItem(
                        floor = floor,
                        x = 1338.6666f,
                        y = 756.3333f,
                        width = 520f,
                        height = 50f,
                        rotation = 89.06547f,
                        type = SchemaItem.Type.WALL
                ),
                SchemaItem(
                        floor = floor,
                        x = 1858.3334f,
                        y = 757.3333f,
                        width = 396.66666f,
                        height = 50f,
                        rotation = 89.06547f,
                        type = SchemaItem.Type.WALL
                ),
                SchemaItem(
                        floor = floor,
                        x = 1949.3334f,
                        y = 1089.3334f,
                        width = 666.6667f,
                        height = 50f,
                        rotation = 89.99999f,
                        type = SchemaItem.Type.WALL
                ),
                SchemaItem(
                        floor = floor,
                        x = 1195.6705f,
                        y = 572.95074f,
                        width = 383.33334f,
                        height = 50f,
                        rotation = 89.773285f,
                        type = SchemaItem.Type.WALL
                )
        )
    }
}