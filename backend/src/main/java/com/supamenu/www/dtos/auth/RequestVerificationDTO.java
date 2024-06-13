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
public class RequestVerificationDTO {
    @Schema(example = "example@gmail.com")
    @Email(message = "Invalid email")
    @NotBlank(message = "Email cannot be blank")
    private String email;
}
