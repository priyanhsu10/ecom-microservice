package com.pro.user_service.dtos;

import lombok.Data;

@Data
public class CreateAddressRequest {
    private String street;
    private String city;
    private String state;
    private String postalCode;
    private String country;
    private String addressLine;
}
