package com.tui.proof.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
@Table(name="CLIENT")
@Getter
@Setter
public class ClientEntity {

    @Id
    @NotNull
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long clientId;
    @NotNull
    private String firstName;
    @NotNull
    private String lastName;
    @NotNull
    private String telephone;
    @Email
    @NotNull
    private String email;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "addressId",referencedColumnName = "addressId")
    private AddressEntity address;
    @OneToMany(mappedBy = "client",fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JsonIgnoreProperties("client")
    private List<OrderEntity> order;
}
