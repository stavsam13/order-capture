package com.tui.proof.services;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.tui.proof.entity.ClientEntity;
import com.tui.proof.entity.OrderEntity;
import com.tui.proof.model.AddressDTO;
import com.tui.proof.model.ClientDTO;
import com.tui.proof.model.OrderDTO;
import com.tui.proof.repositories.OrderRepo;
import com.tui.proof.util.Constants;
import com.tui.proof.util.ErrorMessages;
import com.tui.proof.util.PillotesFilteringProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;


import javax.persistence.criteria.Join;
import javax.transaction.Transactional;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;

@Service
@Transactional
@EnableConfigurationProperties(PillotesFilteringProperties.class)
public class PillotesService {


    @Autowired
    OrderRepo orderRepo;
    @Autowired
    PillotesFilteringProperties pillotesFilteringProperties;

    private static final Logger LOGGER = LoggerFactory.getLogger(PillotesService.class);

    private static final double FIX_COST = 1.33;
    public static final ObjectMapper MAPPER =
            new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
                    .configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false)
                    .configure(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT, true);


    public static OrderEntity convertOrderDTOtoEntity(OrderDTO orderDTO) {
        return MAPPER.convertValue(orderDTO, OrderEntity.class);
    }

    public static OrderDTO convertOrderEntityToDTO(OrderEntity orderEntity) {
        return MAPPER.convertValue(orderEntity, OrderDTO.class);
    }

    public static ClientDTO convertClientEntityToDTO(ClientEntity clientEntity) {
        return MAPPER.convertValue(clientEntity, ClientDTO.class);
    }

    public static <T> Specification<T> createEqualSpecJoinClient(String key, Object value,
                                                                String joinColumn ) {
        return (root, criteriaQuery, criteriaBuilder) -> {
            Join<OrderEntity, ClientEntity> join = root.join(joinColumn);
            return criteriaBuilder.equal(join.get(key), value);
        };
    }

    public static <T> Specification<T> createLikeSpecJoinClient(String key, Object value,
                                                                  String joinColumn ) {
        return (root, criteriaQuery, criteriaBuilder) -> {
            Join<OrderEntity,ClientEntity> join = root.join(joinColumn);
            return criteriaBuilder.like(join.get(key),"%" + value + "%");
        };

    }
    public OrderDTO createPilotesService(OrderDTO orderDTO) throws Exception {

        LOGGER.info("Create Service method has started");
        if (orderDTO.getPilotes() == 5 || orderDTO.getPilotes() == 10 || orderDTO.getPilotes() == 15) {
            orderDTO.setOrderTime(LocalDateTime.now());
            LOGGER.info("Calculating cost of Order");
            orderDTO.setOrderTotal(orderDTO.getPilotes() * FIX_COST);

            OrderEntity orderEntity = convertOrderDTOtoEntity(orderDTO);
            orderEntity = orderRepo.save(orderEntity);
            orderRepo.flush();
            return convertOrderEntityToDTO(orderEntity);
        } else {
            throw new IllegalArgumentException(ErrorMessages.INVALID_NUMBER_PILLOTES);
        }
    }
    public Map<String ,Object> searchPillotes(Map<String, Object> request) {
        Map<String, Object> filterOption = (Map<String, Object>) request.get(Constants.FILTERS);
        Map<String,Object> response = new HashMap<>();
        LOGGER.info("Creating specification for the search");
        Specification<OrderEntity> specification = getSpecification(filterOption);

        List<OrderEntity> orderList = orderRepo.findAll(specification);
        if(orderList.size()!=0) {
        final List<OrderDTO> listForResponse = new ArrayList<>();
        for (OrderEntity orderEntity : orderList) {
            OrderDTO itemToAdd = convertOrderEntityToDTO(orderEntity);
            listForResponse.add(itemToAdd);
        }
        LOGGER.info("Placing the order to my response");
        response.put("Total Orders",listForResponse.size());
        response.put("Orders Result",listForResponse);
        return response;}
        else {
            throw new IllegalArgumentException(ErrorMessages.NO_RECORD_FOUND_FOR_THE_GIVEN_FILTER_CRITERIA);
        }
    }

    public Specification<OrderEntity> getSpecification(Map<String, Object> filterOption) {
        Specification<OrderEntity> specification = Specification.where(null);
        for (String filter : pillotesFilteringProperties.getFiltersKey()) {
            Object value = filterOption.get(filter);
            if (value != null) {
                if (pillotesFilteringProperties.getFirstNameKey().equals(filter)) {
                    specification = specification.
                            and(createLikeSpecJoinClient
                                    (pillotesFilteringProperties.getFirstNameKey(),value,
                                            pillotesFilteringProperties.getJoinColumnOrderKey()));
                }
                if (pillotesFilteringProperties.getLastNameKey().equals(filter)) {
                    specification = specification.
                            and(createLikeSpecJoinClient(pillotesFilteringProperties.getLastNameKey(),value,
                                    pillotesFilteringProperties.getJoinColumnOrderKey()));
                }
                if (pillotesFilteringProperties.getEmailKey().equals(filter)) {
                    specification = specification.
                            and(createLikeSpecJoinClient(pillotesFilteringProperties.getEmailKey(),value,
                                    pillotesFilteringProperties.getJoinColumnOrderKey()));
                }
                if (pillotesFilteringProperties.getTelephoneKey().equals(filter)) {
                    specification = specification.
                            and(createEqualSpecJoinClient(pillotesFilteringProperties.getTelephoneKey(),value,
                                    pillotesFilteringProperties.getJoinColumnOrderKey()));
                }
            }
        }
        return specification;
    }

    public OrderDTO updatePilotesOrder(Long orderNumber, OrderDTO updatedOrderDTO) throws Exception {
        LOGGER.info("Update Service method has started");

        OrderEntity originalOrder = orderRepo.findByOrderNumber(orderNumber);
        if (originalOrder!=null) {
            OrderDTO originalDTO = convertOrderEntityToDTO(originalOrder);
            ClientDTO originalClientDTO = convertClientEntityToDTO(originalOrder.getClient());
            AddressDTO originalAddressDTO = originalClientDTO.getAddress();

            Duration duration = Duration.between(originalDTO.getOrderTime(),LocalDateTime.now());
            if (Integer.parseInt(String.valueOf(duration.toMinutes())) < 5) {
                if(updatedOrderDTO.getPilotes() == 5 || updatedOrderDTO.getPilotes()==10
                        || updatedOrderDTO.getPilotes() == 15) {
                    updatedOrderDTO.setOrderTotal(updatedOrderDTO.getPilotes() * FIX_COST);
                    originalDTO = updatedOrderDTO;
                    originalDTO.setOrderTime(LocalDateTime.now());

                    //seting the original id so that , a new record is not created upon updating
                    originalDTO.setOrderNumber(originalOrder.getOrderNumber());
                    originalDTO.getClient().setClientId(originalClientDTO.getClientId());
                    originalDTO.getClient().getAddress().setAddressId(originalAddressDTO.getAddressId());
                    LOGGER.info("Update Service saving new records");
                    orderRepo.save(convertOrderDTOtoEntity(originalDTO));
                    orderRepo.flush();
                } else {
                    throw new IllegalArgumentException(ErrorMessages.INVALID_NUMBER_PILLOTES);
                }
            } else {
                throw new IllegalArgumentException(ErrorMessages.LATE_UPDATE_ORDER);
            }
            return originalDTO;
        } else {
            throw new IllegalArgumentException(ErrorMessages.NO_RECORD_FOUND_FOR_THE_GIVEN_ID);
        }
    }
}
