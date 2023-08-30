package az.unitech.service;

import az.unitech.dto.response.UserResponse;
import az.unitech.dto.request.UserRegisterDto;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {


    UserResponse userRegister(UserRegisterDto userRegisterDto);

}

