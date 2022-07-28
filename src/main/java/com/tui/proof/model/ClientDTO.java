package com.tui.proof.model;

import com.tui.proof.entity.AddressEntity;
import com.tui.proof.entity.OrderEntity;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Getter
@Setter
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
  private AddressEntity address;
}
