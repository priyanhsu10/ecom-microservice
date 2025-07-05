package com.pro.user_service.entities;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Getter
@Setter
@Table("users")
public class User {
    @Id
    private Long id;
    @NotBlank(message = "Username cannot be blank")
    private String username;

    private String password;
    @NotBlank(message = "Email cannot be blank")
    private String email;
    @NotBlank(message = "First name cannot be blank")
    private String firstName;
    @NotBlank(message = "Last name cannot be blank")
    private String lastName;
    @Max(value = 13, message = "Phone number must be between 10 and 13 digits")
    @Min(value = 10, message = "Phone number must be between 10 and 13 digits")
    private String phoneNumber;
    private boolean isActive = true; // Default to active

    // Additional fields can be added as needed
}
