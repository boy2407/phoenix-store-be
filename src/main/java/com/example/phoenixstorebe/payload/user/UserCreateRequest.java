package com.example.phoenixstorebe.payload.user;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class UserCreateRequest {
    @NotNull(message = "Username is required")
    private String username;

    @NotNull(message = "Lastname is required")
    private String lastname;

    @NotNull(message = "Firstname is required")
    private String firstname;

    @NotNull(message = "Password is required")
    private String password;

    @NotNull(message = "Repeat password is required")
    private String password2;

    @NotNull(message = "Email is required")
    @Email(message = "Email should be valid")
    private String email;

    @NotNull(message = "Phone is required")
    @Pattern(regexp = "^0\\d{9}$", message = "Phone must be a valid Vietnamese phone number (10 digits, bắt đầu bằng 0)")
    @Schema(example = "" )
    private String phone;
}
