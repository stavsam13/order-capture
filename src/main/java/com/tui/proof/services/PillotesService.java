package com.tui.proof.services;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.tui.proof.entity.AddressEntity;
import com.tui.proof.entity.ClientEntity;
import com.tui.proof.entity.OrderEntity;
import com.tui.proof.model.AddressDTO;
import com.tui.proof.model.ClientDTO;
import com.tui.proof.model.OrderDTO;
import com.tui.proof.repositories.AddressRepo;
import com.tui.proof.repositories.ClientRepo;
import com.tui.proof.repositories.OrderRepo;
import com.tui.proof.util.PillotesFilteringProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;


import javax.persistence.criteria.Join;
import java.time.LocalDateTime;
import java.util.*;

@Service
@EnableConfigurationProperties(PillotesFilteringProperties.class)
public class PillotesService {

    private static final double FIX_COST = 1.33;
    @Autowired
    ClientRepo clientRepo;
    @Autowired
    OrderRepo orderRepo;
    @Autowired
    AddressRepo addressRepo;
    @Autowired
    PillotesFilteringProperties pillotesFilteringProperties;

    private static final Logger LOGGER = LoggerFactory.getLogger(PillotesService.class);

    public static AddressEntity convertAddressDTOtoEntity(AddressDTO addressDTO) {
        return MAPPER.convertValue(addressDTO, AddressEntity.class);
    }

    public static ClientEntity convertClientDTOtoEntity(ClientDTO clientDTO) {
        return MAPPER.convertValue(clientDTO, ClientEntity.class);
    }

    public static OrderEntity convertOrderDTOtoEntity(OrderDTO orderDTO) {
        return MAPPER.convertValue(orderDTO, OrderEntity.class);
    }

    public static OrderDTO convertOrderEntityToDTO(OrderEntity orderEntity) {
        return MAPPER.convertValue(orderEntity, OrderDTO.class);
    }

    public static ClientDTO convertClientEntityToDTO(ClientEntity clientEntity) {
        return MAPPER.convertValue(clientEntity, ClientDTO.class);
    }

    public static AddressDTO convertAddressEntityToDTO(AddressEntity address) {
        return MAPPER.convertValue(address, AddressDTO.class);
    }

    public static AddressEntity convertAddressDTOToEntity(AddressDTO addressDTO) {
        return MAPPER.convertValue(addressDTO, AddressEntity.class);
    }

    public static ClientEntity convertClientDTOToEntity(ClientDTO clientDTO) {
        return MAPPER.convertValue(clientDTO, ClientEntity.class);
    }

    public static final ObjectMapper MAPPER =
            new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
                    .configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false)
                    .configure(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT, true);

    public static <T> Specification<T> createGreaterThanSpec(String key, Object value) {
        return ((root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.
                gt(root.get(key), Integer.valueOf(String.valueOf(value))));
    }

    public static <T> Specification<T> createGreaterThanSpecJoinClient(String key, Object value,
                                                                       String joinColumn ) {
        return (root, criteriaQuery, criteriaBuilder) -> {
            Join<OrderEntity,ClientEntity> join = root.join(joinColumn);
            return criteriaBuilder.gt(join.get(key),Long.valueOf(String.valueOf(value)));
        };

    }

    public static <T> Specification<T> createLikeSpec(String key, Object value) {
        return ((root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.
                like(root.get(key), "%" + value + "%"));
    }

    public static <T> Specification<T> createEqualsSpec(String key, Object value) {
        return ((root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.
                equal(root.get(key), value));
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
    public OrderDTO createPilotesService1(OrderDTO orderDTO) throws Exception {

        LOGGER.info("Create Service method has started");

        if (orderDTO.getPilotes() == 5 || orderDTO.getPilotes() == 10 || orderDTO.getPilotes() == 15) {
            orderDTO.setOrderTime(LocalDateTime.now());
            orderDTO.setOrderTotal(orderDTO.getPilotes() * FIX_COST);
            ClientDTO clientDTO = convertClientEntityToDTO(orderDTO.getClient());
            AddressDTO addressDTO = convertAddressEntityToDTO(clientDTO.getAddress());

            AddressEntity addressEntity = convertAddressDTOtoEntity(addressDTO);
            addressEntity = addressRepo.save(addressEntity);
            addressRepo.flush();

            ClientEntity clientEntity = convertClientDTOtoEntity(clientDTO);
            clientEntity.setAddress(addressEntity);
            clientEntity = clientRepo.save(clientEntity);
            clientRepo.flush();


            OrderEntity orderEntity = convertOrderDTOtoEntity(orderDTO);
            orderEntity.setClient(clientEntity);
            orderEntity = orderRepo.save(orderEntity);
            orderRepo.flush();
            return convertOrderEntityToDTO(orderEntity);
        } else {
            throw new Exception("Invalid number of Pillotes");
        }
    }

    public Map<String ,Object> searchPillotes(Map<String, Object> request) {
        Map<String, Object> filterOption = (Map<String, Object>) request.get("filters");
        Map<String, String> sorting = (Map<String, String>) request.get("sorting");
        Map<String, Integer> pages = (Map<String, Integer>) request.get("pagination");
        Map<String,Object> response = new HashMap<>();
        // I am initilizing my query
        Specification<OrderEntity> specification = getSpecification(filterOption);
        Pageable pagination = getPageable(sorting,pages,pillotesFilteringProperties);

        List<OrderEntity> orderList = orderRepo.findAll(specification);
        final List<OrderDTO> listForResponse = new ArrayList<>();
        for (OrderEntity orderEntity : orderList) {
            OrderDTO itemToAdd = convertOrderEntityToDTO(orderEntity);
            listForResponse.add(itemToAdd);
        }
        response.put("Orders",listForResponse);
        return response;
    }

    private Specification<OrderEntity> getSpecification(Map<String, Object> filterOption) {
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
                            and(createEqualSpecJoinClient(pillotesFilteringProperties.getEmailKey(),value,
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
    public static Pageable getPageable(Map<String, String> sorting, Map<String, Integer> pages,
                                       PillotesFilteringProperties pillotesFilteringProperties) {

        Integer pageNumber = pages.get(pillotesFilteringProperties.getPageNumberKey());
        Integer pageSize = pages.get(pillotesFilteringProperties.getPageSizeKey());
        if (pageNumber == null || pageSize == null) {
            throw new RuntimeException("PageNo or pageSize is zero.");
        }
        Pageable pagination = PageRequest.of(pageNumber, pageSize);
        List<Sort.Order> orders = new ArrayList<>();
        if (sorting != null) {
            for (Map.Entry<String, String> entry : sorting.entrySet()) {
                orders.add(new Sort.Order(
                        pillotesFilteringProperties.getAscSortingKey().equals(entry.getValue()) ? Sort.Direction.ASC
                                : Sort.Direction.DESC, entry.getKey()));
            }
        }

        if (!(orders.isEmpty())) {
            pagination = PageRequest.of(pageNumber, pageSize, Sort.by(orders));
        }
        return pagination;
    }

}
