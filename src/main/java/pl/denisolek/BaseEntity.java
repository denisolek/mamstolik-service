package pl.denisolek;

import com.fasterxml.jackson.annotation.JsonView;
import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@Data
@MappedSuperclass
public class BaseEntity {

	@Id
	@JsonView(Views.Base.class)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Integer id;
}
