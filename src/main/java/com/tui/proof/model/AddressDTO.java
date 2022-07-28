package com.tui.proof.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@ToString
public class AddressDTO {
  @NotNull
  private Long streetNumber;
  @NotNull
  private String streetName;
  private String postcode;
  private String city;
  private String country;
}
