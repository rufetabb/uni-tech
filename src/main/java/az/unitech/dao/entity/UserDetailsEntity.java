package az.unitech.dao.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "user_details")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserDetailsEntity {

    @Id
    private String userId;
    private String name;
    private String surname;
    private String pin;
    private String password;


}
