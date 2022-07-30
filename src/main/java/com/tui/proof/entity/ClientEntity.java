package com.tui.proof.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
@Table(name="CLIENT")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClientEntity {

    @Id
    @NotNull
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long clientId;
    @NotNull
    @NotEmpty
    private String firstName;
    @NotNull
    @NotEmpty
    private String lastName;
    @NotNull
    private String telephone;
    @NotNull
    @Email
    private String email;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "addressId",referencedColumnName = "addressId")
    private AddressEntity address;
    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL)
    @JsonIgnoreProperties("client")
    private List<OrderEntity> order;
}
