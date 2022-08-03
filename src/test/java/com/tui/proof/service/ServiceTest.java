package com.tui.proof.service;

import com.tui.proof.entity.AddressEntity;
import com.tui.proof.entity.ClientEntity;
import com.tui.proof.entity.OrderEntity;
import com.tui.proof.model.AddressDTO;
import com.tui.proof.model.ClientDTO;
import com.tui.proof.model.OrderDTO;
import com.tui.proof.repositories.OrderRepo;
import com.tui.proof.services.PillotesService;
import com.tui.proof.util.PillotesFilteringProperties;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertNotNull;

@ExtendWith(MockitoExtension.class)
public class ServiceTest {

    @InjectMocks
    private PillotesService pillotesService;
    @Mock
    private OrderEntity orderEntity;
    @Mock
    private PillotesFilteringProperties pillotesFilteringProperties;

    @Mock
    private OrderRepo orderRepo;

    OrderDTO orderDTO;
    ClientDTO clientDTO;
    AddressDTO addressDTO;

    AddressEntity addressEntity;

    ClientEntity clientEntity;

    Specification<OrderEntity> specification;
    Map<String, Object> filters;
    private final String URI = "/tuiTest/createPillotes";
    @BeforeEach
    void setup() {
        specification = new Specification<OrderEntity>() {
            @Override
            public Predicate toPredicate(Root<OrderEntity> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                return null;
            }
        };
        orderDTO = new OrderDTO();
        filters = new HashMap<>();
        filters.put("firstname","Stavros");
        orderEntity = new OrderEntity();
        clientEntity = new ClientEntity();
        addressEntity = new AddressEntity();
        clientEntity.setClientId(1L);
        clientEntity.setEmail("example@gmail.com");
        clientEntity.setTelephone("6977176852");
        addressEntity.setAddressId(1L);
        addressEntity.setCity("Thessalonik");
        addressEntity.setStreetName("StreetName");
        addressEntity.setStreetNumber("12");
        clientEntity.setAddress(addressEntity);
        orderEntity.setOrderTime(LocalDateTime.now());
        orderEntity.setOrderNumber(1L);
        orderEntity.setClient(clientEntity);
        orderDTO.setPilotes(5);
        orderDTO.setOrderTime(LocalDateTime.now());
        orderDTO.setOrderNumber(1L);
        orderDTO.setOrderTotal(6.65);
        clientDTO = new ClientDTO();
        addressDTO = new AddressDTO();
        addressDTO.setAddressId(1L);
        addressDTO.setCountry("Greece");
        addressDTO.setCity("Thessaloniki");
        addressDTO.setStreetName("Ierosolimon");
        addressDTO.setStreetNumber("14");
        addressDTO.setPostcode("55134");
        clientDTO.setAddress(addressDTO);
        clientDTO.setClientId(1L);
        clientDTO.setTelephone("6977176852");
        clientDTO.setFirstName("Stavros");
        clientDTO.setLastName("Samaras");
        clientDTO.setEmail("stavsamaras@gmail.com");
        orderDTO.setClient(clientDTO);
       }
    @Test
    void createPilotesServiceTest() throws Exception {
     Mockito.when(orderRepo.save(Mockito.any(OrderEntity.class))).thenReturn(orderEntity);
     OrderDTO response = pillotesService.createPilotesService(orderDTO);
     Assertions.assertNotNull(response);
    }

    @Test
    void searchPillotesTest() {
        Mockito.when(pillotesService.getSpecification(filters)).thenReturn(specification);
        Map<String,Object> response = pillotesService.searchPillotes(filters);
        List<Map<String,Object>> responseList = new ArrayList<>();
        responseList.add(0,response);
    }

    @Test
    void updatePillotesTest() throws Exception {
        Mockito.when(orderRepo.findByOrderNumber(1L)).thenReturn(orderEntity);
        OrderDTO response = pillotesService.updatePilotesOrder(1L,orderDTO);
        Assertions.assertNotNull(response);
    }
}
