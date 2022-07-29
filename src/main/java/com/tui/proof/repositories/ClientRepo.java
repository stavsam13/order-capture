package com.tui.proof.repositories;

import com.tui.proof.entity.ClientEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClientRepo extends JpaRepository <ClientEntity,Long>  {

}
