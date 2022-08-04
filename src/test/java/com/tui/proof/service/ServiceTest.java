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
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertNotNull;
@EnableConfigurationProperties(PillotesFilteringProperties.class)
@ExtendWith(MockitoExtension.class)
public class ServiceTest {

    @InjectMocks
    private PillotesService pillotesService;
    @Mock
    private OrderEntity orderEntity;
    @Mock
    private PillotesFilteringProperties pillotesFilteringProperties = new PillotesFilteringProperties();

    @Mock
    private OrderRepo orderRepo;

    OrderDTO orderDTO;
    ClientDTO clientDTO;
    AddressDTO addressDTO;

    AddressEntity addressEntity;

    ClientEntity clientEntity;

    List<String> filters;
    Map<String, Object> request;

    List<OrderEntity> orderEntityList = new ArrayList<>();
    private final String URI = "/tuiTest/createPillotes";
    @BeforeEach
    void setup() {
        request = new HashMap<>();
        orderDTO = new OrderDTO();
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
        orderEntity.setPilotes(10);
        orderEntityList.add(orderEntity);
        orderEntityList.add(orderEntity);
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
        pillotesFilteringProperties.setFirstNameKey("Stavros");
        pillotesFilteringProperties.setJoinColumnOrderKey("client");
        filters = new ArrayList<>();
        filters.add("firstName");
        filters.add("lastName");
        pillotesFilteringProperties.setFiltersKey(filters);
        Map<String,Object> map = new HashMap<>();
        map.put("firstName","Stavros");
        request.put("filters",map);
       }
    @Test
    void createPilotesServiceTest() throws Exception {
     Mockito.when(orderRepo.save(Mockito.any(OrderEntity.class))).thenReturn(orderEntity);
     OrderDTO response = pillotesService.createPilotesService(orderDTO);
     Assertions.assertNotNull(response);
    }

    @Test
    void searchPillotesService(){
        Mockito.when( pillotesFilteringProperties.getFiltersKey()).thenReturn(filters);
        Mockito.when( pillotesFilteringProperties.getFirstNameKey()).thenReturn("firstName");
        Mockito.when( pillotesFilteringProperties.getLastNameKey()).thenReturn("lastName");
        Mockito.when( pillotesFilteringProperties.getEmailKey()).thenReturn("email");
        Mockito.when( pillotesFilteringProperties.getTelephoneKey()).thenReturn("telephone");
        Mockito.when( pillotesFilteringProperties.getJoinColumnOrderKey()).thenReturn("client");
        Mockito.when(orderRepo.findAll(Mockito.any(Specification.class))).thenReturn(orderEntityList);
        Map<String,Object> response = pillotesService.searchPillotes(request);
        Assertions.assertEquals(2,response.size());
    }

    @Test
    void updatePillotesTest() throws Exception {
        Mockito.when(orderRepo.findByOrderNumber(1L)).thenReturn(orderEntity);
        OrderDTO response = pillotesService.updatePilotesOrder(1L,orderDTO);
        Assertions.assertNotNull(response);
    }
}
