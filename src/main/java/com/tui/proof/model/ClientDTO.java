package com.tui.proof.model;

import com.tui.proof.entity.AddressEntity;
import com.tui.proof.entity.OrderEntity;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
public class ClientDTO {
  private Long clientId;
  private String firstName;
  private String lastName;
  private String telephone;
  private String email;
  private AddressEntity address;
  private OrderEntity order;
}
