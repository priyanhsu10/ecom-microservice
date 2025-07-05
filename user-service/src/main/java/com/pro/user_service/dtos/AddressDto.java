package com.pro.user_service.dtos;

import com.pro.user_service.entities.Address;
import lombok.Data;
@Data
public  class AddressDto {
    private Long id;
    private String street;
    private String city;
    private String state;
    private String postalCode;
    private String country;
    private String addressLine;
    private Long userId;

    public AddressDto(Address savedAddress) {
        this.id = savedAddress.getId();
        this.street = savedAddress.getStreet();
        this.city = savedAddress.getCity();
        this.state = savedAddress.getState();
        this.postalCode = savedAddress.getPostalCode();
        this.country = savedAddress.getCountry();
        this.addressLine = savedAddress.getAddressLine();
        this.userId = savedAddress.getUserId();
    }
    // Getters and Setters
}
