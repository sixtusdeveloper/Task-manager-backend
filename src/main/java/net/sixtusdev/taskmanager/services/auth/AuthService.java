package net.sixtusdev.taskmanager.services.auth;

import net.sixtusdev.taskmanager.dto.SignupRequest;
import net.sixtusdev.taskmanager.dto.UserDto;

public interface AuthService {

    UserDto signupUser(SignupRequest signupRequest);

    boolean hasUserWithEmail(String email);

}