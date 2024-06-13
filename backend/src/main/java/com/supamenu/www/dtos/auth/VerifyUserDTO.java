package com.supamenu.www.dtos.auth;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VerifyUserDTO {
    @Schema(example = "example@gmail.com")
    @NotBlank(message = "Email cannot be blank")
    @Email(message = "Invalid email")
    private String email;

    @Schema(example = "123456")
    @NotBlank(message = "Code cannot be blank")
    private String code;
}
