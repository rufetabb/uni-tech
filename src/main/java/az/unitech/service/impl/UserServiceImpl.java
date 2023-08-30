package az.unitech.service.impl;

import az.unitech.dao.repository.UserRepository;
import az.unitech.dto.response.UserResponse;
import az.unitech.dto.request.UserRegisterDto;
import az.unitech.exception.AccountAlreadyExistsException;
import az.unitech.mapper.UserMapper;
import az.unitech.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {


    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;


    @Override
    public UserResponse userRegister(UserRegisterDto userRegisterDto) {

        if (userRepository.findByPin(userRegisterDto.getPin()).isPresent()) {
            log.error("Account already exists");
            throw new AccountAlreadyExistsException("account already exists");
        } else {
            var userDetails = userMapper.mapRequestToEntity(userRegisterDto);
            userDetails.setPassword(bCryptPasswordEncoder.encode(userRegisterDto.getPassword()));
            userRepository.save(userDetails);
            log.info("User saved successfully");
        }

        return UserResponse.builder().name(userRegisterDto.getName()).surname(userRegisterDto.getSurname())
                .pin(userRegisterDto.getPin()).message("Successfully saved").build();


    }

    @Override
    public UserDetails loadUserByUsername(String pin) throws UsernameNotFoundException {
        var user = userRepository.findByPin(pin);
        if (user.isPresent()) {
            log.info("user found: {}", pin);
        } else {
            log.info("user not found");
            throw new UsernameNotFoundException("user not found");


        }
        return new UserDetailsService(user.get());
    }
}
