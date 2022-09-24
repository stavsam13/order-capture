package com.tui.proof.util;

import com.tui.proof.entity.ClientEntity;
import com.tui.proof.entity.OrderEntity;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Join;

public  class HelperClass {

    public static <T> Specification<T> createLikeSpecJoinClient(String key, Object value,
                                                                String joinColumn ) {
        return (root, criteriaQuery, criteriaBuilder) -> {
            Join<OrderEntity, ClientEntity> join = root.join(joinColumn);
            return criteriaBuilder.like(join.get(key),"%" + value + "%");
        };

    }

    public static <T> Specification<T> createEqualSpecJoinClient(String key, Object value,
                                                                 String joinColumn ) {
        return (root, criteriaQuery, criteriaBuilder) -> {
            Join<OrderEntity, ClientEntity> join = root.join(joinColumn);
            return criteriaBuilder.equal(join.get(key), value);
        };
    }
}
