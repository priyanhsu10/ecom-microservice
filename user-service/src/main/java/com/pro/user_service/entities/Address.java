package com.pro.user_service.entities;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Getter
@Setter
@Table("user_addresses")
public class Address {
    @Id
    private Long id;
    private String street;
    private String city;
    private String state;
    private String postalCode;
    private String country;
    private  String addressLine;
    private  Long userId;



}
