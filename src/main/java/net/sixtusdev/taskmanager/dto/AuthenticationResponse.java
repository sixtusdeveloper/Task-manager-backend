package net.sixtusdev.taskmanager.dto;

import lombok.Data;
import net.sixtusdev.taskmanager.enums.UserRole;

@Data
public class AuthenticationResponse {

    private String jwt;

    private Long userId;

    private UserRole userRole;

}
