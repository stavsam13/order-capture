package com.tui.proof.model;


import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
public class ClientDTO {
  private Long clientId;
  @NotEmpty
  private String firstName;
  @NotEmpty
  private String lastName;
  @NotEmpty
  @Size(min = 10, message = "telephone should have at least 10 characters")
  private String telephone;
  @NotEmpty
  @Email
  private String email;
  private AddressDTO address;
}
