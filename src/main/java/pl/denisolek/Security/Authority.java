package pl.denisolek.Security;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@Entity
public class Authority {
	@Id
	@NotNull
	@Size(max = 50)
	private String name;
}
