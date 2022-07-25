package com.tui.proof.model;

import lombok.Data;

@Data
public class Order {
  private String orderNumber;
  private Address deliveryAddress;
  private int pilotes;
  private double orderTotal;

}
