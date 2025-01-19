package net.sixtusdev.taskmanager.controller.auth;

import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import net.sixtusdev.taskmanager.services.auth.AuthService;
import net.sixtusdev.taskmanager.services.jwt.UserService;
import net.sixtusdev.taskmanager.utils.JwtUtils;
import net.sixtusdev.taskmanager.dto.AuthenticationRequest;
import net.sixtusdev.taskmanager.dto.AuthenticationResponse;
import net.sixtusdev.taskmanager.dto.SignupRequest;
import net.sixtusdev.taskmanager.dto.UserDto;
import net.sixtusdev.taskmanager.entities.User;
import net.sixtusdev.taskmanager.repositories.UserRepositories;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor

public class AuthController {

    private final AuthService authService;

    private final UserRepositories userRepositories;

    private final JwtUtils jwtUtils;

    private final UserService userService;

    private final AuthenticationManager authenticationManager;

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

    @PostMapping("/login")
    public AuthenticationResponse login(@RequestBody AuthenticationRequest authenticationRequest) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authenticationRequest.getEmail(),
                    authenticationRequest.getPassword()));
        } catch (BadCredentialsException e) {
            throw new BadCredentialsException("Incorrect email or password", e);
        }

        final UserDetails userDetails = userService.userDetailService()
                .loadUserByUsername(authenticationRequest.getEmail());
        Optional<User> optionalUser = userRepositories.findFirstByEmail(authenticationRequest.getEmail());

        final String jwtToken = jwtUtils.generateToken(userDetails);
        AuthenticationResponse authenticationResponse = new AuthenticationResponse();

        if (optionalUser.isPresent()) {
            authenticationResponse.setJwt(jwtToken);
            authenticationResponse.setUserId(optionalUser.get().getId());
            authenticationResponse.setUserRole(optionalUser.get().getUserRole());
        }

        return authenticationResponse;
    }

}
