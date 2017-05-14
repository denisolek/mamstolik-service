package pl.denisolek.Restaurant;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RestaurantRepository extends JpaRepository<Restaurant, Integer>{
    List<Restaurant> findByCity(String city);
}
