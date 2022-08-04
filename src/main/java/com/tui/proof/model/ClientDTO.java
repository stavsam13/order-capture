package com.tui.proof.model;


import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class ClientDTO {
  private Long clientId;
  @NotNull
  @NotEmpty
  private String firstName;
  @NotNull
  @NotEmpty
  private String lastName;
  @Size(min = 10 , max=15, message = "telephone should have at least 10 to 13 digits")
  private String telephone;
  @NotNull
  @Email(message = "Email is not valid")
  private String email;
  private AddressDTO address;
}
