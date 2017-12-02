package pl.denisolek.unit.core.restaurant

import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.mock
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.runners.MockitoJUnitRunner
import pl.denisolek.core.restaurant.RestaurantRepository
import pl.denisolek.core.restaurant.RestaurantService

@RunWith(MockitoJUnitRunner::class)
class RestaurantServiceTests {

    @InjectMocks
    lateinit var restaurantService: RestaurantService

    @Mock
    private val restaurantRepositoryMock = mock<RestaurantRepository>()

    @Test
    fun `generateUrlName_ not existing`() {
        Mockito.`when`(restaurantRepositoryMock.countByUrlName("url.name")).thenReturn(0)

        val actual = restaurantService.generateUrlName("url name")
        Assert.assertEquals("url.name", actual)
    }

    @Test
    fun `generateUrlName_ existing without identifier`() {
        Mockito.`when`(restaurantRepositoryMock.countByUrlName(any()))
                .thenReturn(1)
                .thenReturn(0)

        val actual = restaurantService.generateUrlName("url name")
        Assert.assertEquals("url.name.1", actual)
    }

    @Test
    fun `generateUrlName_ existing with identifier 1`() {
        Mockito.`when`(restaurantRepositoryMock.countByUrlName(any()))
                .thenReturn(1)
                .thenReturn(1)
                .thenReturn(0)

        val actual = restaurantService.generateUrlName("url name")
        Assert.assertEquals("url.name.2", actual)
    }

    @Test
    fun `generateUrlName_ existing with identifier 2`() {
        Mockito.`when`(restaurantRepositoryMock.countByUrlName(any()))
                .thenReturn(1)
                .thenReturn(1)
                .thenReturn(1)
                .thenReturn(0)

        val actual = restaurantService.generateUrlName("url name")
        Assert.assertEquals("url.name.3", actual)
    }

    @Test
    fun `generateUrlName_ existing with identifier 3`() {
        Mockito.`when`(restaurantRepositoryMock.countByUrlName(any()))
                .thenReturn(1)
                .thenReturn(1)
                .thenReturn(1)
                .thenReturn(1)
                .thenReturn(0)

        val actual = restaurantService.generateUrlName("url name")
        Assert.assertEquals("url.name.4", actual)
    }
}