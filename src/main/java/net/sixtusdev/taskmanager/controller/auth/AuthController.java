package net.sixtusdev.taskmanager.controller.auth;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import net.sixtusdev.taskmanager.services.auth.AuthService;
import net.sixtusdev.taskmanager.dto.SignupRequest;
import net.sixtusdev.taskmanager.dto.UserDto;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor

public class AuthController {

    private final AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<?> signupUser(@RequestBody SignupRequest signupRequest) {
        if (authService.hasUserWithEmail(signupRequest.getEmail())) {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("User with this email already exists!");
        }
        UserDto createdUserDto = authService.signupUser(signupRequest);

        if (createdUserDto == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User registration failed!");
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(createdUserDto);
    }

}
