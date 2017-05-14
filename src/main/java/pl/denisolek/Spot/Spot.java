package pl.denisolek.Spot;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;
import pl.denisolek.BaseEntity;
import pl.denisolek.Restaurant.Restaurant;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Data
@Entity
@EqualsAndHashCode(callSuper = true)
public class Spot extends BaseEntity{

	Integer capacity;

	@ManyToOne
	@JoinColumn
	@JsonIgnore
	Restaurant restaurant;
}
