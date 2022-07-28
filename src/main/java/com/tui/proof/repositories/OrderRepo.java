package com.tui.proof.repositories;

import com.tui.proof.entity.OrderEntity;
import com.tui.proof.model.OrderDTO;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepo extends JpaRepository<OrderEntity,Long>, JpaSpecificationExecutor<OrderEntity> {
    List<OrderDTO> findByClientClientIdIn(List<Long> clientId);

    OrderEntity findByOrderNumber(Long orderNumber);
}
