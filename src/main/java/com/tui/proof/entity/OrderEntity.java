package com.tui.proof.entity;

import com.tui.proof.model.Address;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

@Entity
@Data
public class OrderEntity {

    @Id
    @NotNull
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int orderNumber;
    private Address deliveryAddress;
    private int pilotes;
    private double orderTotal;
}
