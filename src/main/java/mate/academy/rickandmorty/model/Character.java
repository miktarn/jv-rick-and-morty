package mate.academy.rickandmorty.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Character {
    @Id
    @GeneratedValue
    private Long id;
    private String externalId;
    private String name;
    private String status;
    private String gender;
}
