package az.unitech.controller;


import az.unitech.dto.request.UserRegisterDto;
import az.unitech.dto.response.UserResponse;
import az.unitech.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<UserResponse> registerUser(@RequestBody UserRegisterDto userRegisterDto) {
        return ResponseEntity.ok(userService.userRegister(userRegisterDto));

    }

}
