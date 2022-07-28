package com.tui.proof.repositories;

import com.tui.proof.entity.ClientEntity;
import com.tui.proof.model.ClientDTO;
import com.tui.proof.model.OrderDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClientRepo extends JpaRepository <ClientEntity,Long>  {

}
