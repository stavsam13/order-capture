package com.tui.proof.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class AddressDTO {
  private Long streetNumber;
  private String streetName;
  private String postcode;
  private String city;
  private String country;
}
