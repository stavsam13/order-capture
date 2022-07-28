package com.tui.proof.entity;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Getter
@Setter
@Table(name="ADDRESS")
public class AddressEntity {

    @Id
    @NotNull
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long addressId;
    private String streetName;
    private String streetNumber;
    private String postcode;
    private String city;
    private String country;
}
