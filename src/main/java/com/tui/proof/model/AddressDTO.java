package com.tui.proof.model;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class AddressDTO {
  private Long addressId;
  @NotNull
  private String streetNumber;
  @NotNull
  private String streetName;
  private String postcode;
  private String city;
  private String country;
}
