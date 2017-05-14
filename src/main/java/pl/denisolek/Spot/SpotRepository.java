package pl.denisolek.Spot;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SpotRepository extends JpaRepository<Spot, Integer> {
	List<Spot> findByRestaurantId(Integer restaurantId);
}
