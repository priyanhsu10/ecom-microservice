package com.pro.user_service.dtos;

import lombok.Data;

@Data
public class CreateUserRequest {
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private String password;
    private String addressLine;
    private String street;
    private String city;
    private String state;
    private String postalCode;
    private String country;

}
