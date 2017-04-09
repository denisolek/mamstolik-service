package pl.denisolek.User;

import lombok.Data;
import pl.denisolek.BaseEntity;

import javax.persistence.Entity;
import javax.persistence.Table;

@Data
@Entity
@Table(name="[user]")
public class User extends BaseEntity {
    String email;
    String name;
    String surname;
}
