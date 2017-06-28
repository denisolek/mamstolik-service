package pl.denisolek.Restaurant;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RestaurantRepository extends JpaRepository<Restaurant, Integer> {
	List<Restaurant> findByCityAndIsActive(String city, Boolean isActive);
	List<Restaurant> findByIsActive(Boolean isActive);
}
