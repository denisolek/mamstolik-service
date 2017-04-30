package pl.denisolek.User;

import lombok.Data;
import lombok.EqualsAndHashCode;
import pl.denisolek.BaseEntity;

import javax.persistence.Entity;
import javax.persistence.Table;

@Data
@Entity
@Table(name="[user]")
@EqualsAndHashCode(callSuper = true)
public class User extends BaseEntity {
    String email;
    String name;
    String surname;
}
