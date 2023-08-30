package az.unitech.dto.request;


import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserRegisterDto {

    private String name;
    private String surname;
    private String pin;
    private String userId;
    private String password;

}
