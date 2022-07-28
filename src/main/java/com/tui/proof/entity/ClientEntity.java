package com.tui.proof.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

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
    private String firstName;
    private String lastName;
    private String telephone;
    @Email
    private String email;
    @OneToOne
    @JoinColumn(name = "addressId",referencedColumnName = "addressId")
    private AddressEntity address;
    @OneToMany(mappedBy = "client",fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JsonIgnoreProperties("client")
    private List<OrderEntity> order;
}
