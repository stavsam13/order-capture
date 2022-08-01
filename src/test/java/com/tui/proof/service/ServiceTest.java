package com.tui.proof.service;

import com.tui.proof.entity.OrderEntity;
import com.tui.proof.model.AddressDTO;
import com.tui.proof.model.ClientDTO;
import com.tui.proof.model.OrderDTO;
import com.tui.proof.repositories.OrderRepo;
import com.tui.proof.services.PillotesService;
import com.tui.proof.ws.controller.PillotesController;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDateTime;
import java.util.HashMap;

import static org.junit.Assert.assertNotNull;

@ExtendWith(MockitoExtension.class)
public class ServiceTest {

    @InjectMocks
    private PillotesService pillotesService;
    @Mock
    private OrderEntity orderEntity;

    @Autowired
    private MockMvc mockMvc;
    @Mock
    private OrderRepo orderRepo;

    OrderDTO orderDTO;
    ClientDTO clientDTO;
    AddressDTO addressDTO;

    private final String URI = "/tuiTest/createPillotes";
    @BeforeEach
    void setup() {
        orderDTO = new OrderDTO();
        orderEntity = new OrderEntity();
        orderEntity.setOrderTime(LocalDateTime.now());
        orderEntity.setOrderNumber(1L);
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
       }
    @Test
    void createPilotesServiceTest() throws Exception {
     Mockito.when(orderRepo.save(Mockito.any(OrderEntity.class))).thenReturn(orderEntity);
     OrderDTO response = pillotesService.createPilotesService(orderDTO);
     Assertions.assertNotNull(response);
    }

    @Test
    void searchPillotesTest() {

    }
}
