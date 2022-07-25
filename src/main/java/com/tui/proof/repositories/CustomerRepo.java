package com.tui.proof.repositories;

import com.tui.proof.model.Client;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerRepo {

    List<Client> findByLastName(String lastname);
    List<Client> findByFirstName(String firstname);
    List<Client> findByTelephone(String telephone);
    List<Client> findByEmail(String email);

}
