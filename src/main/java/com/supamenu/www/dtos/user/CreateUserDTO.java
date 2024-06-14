package com.supamenu.www.dtos.user;

import com.supamenu.www.dtos.auth.RegisterUserDTO;
import lombok.*;

import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class CreateUserDTO extends RegisterUserDTO {
    private List<UUID> roles;

    public CreateUserDTO(RegisterUserDTO registerUserDTO) {
        this.setEmail(registerUserDTO.getEmail());
        this.setPassword(registerUserDTO.getPassword());
        this.setUsername(registerUserDTO.getUsername());
        this.setFirstName(registerUserDTO.getFirstName());
        this.setLastName(registerUserDTO.getLastName());
    }
}
