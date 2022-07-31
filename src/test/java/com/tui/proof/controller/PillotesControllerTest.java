package com.tui.proof.controller;

import com.tui.proof.model.AddressDTO;
import com.tui.proof.model.ClientDTO;
import com.tui.proof.model.OrderDTO;
import com.tui.proof.services.PillotesService;
import com.tui.proof.ws.controller.PillotesController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.text.ParseException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(MockitoExtension.class)
public class PillotesControllerTest {

    OrderDTO orderDTO;
    ClientDTO clientDTO;
    AddressDTO addressDTO;

    Map<String,Object> response;
    Map<String, Object> request;

    @InjectMocks
    private PillotesController pillotesController;
    @Mock
    private PillotesService pillotesService;

    @BeforeEach
    void setup() {
        orderDTO = new OrderDTO();
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
        response = new HashMap<>();
        request = new HashMap<>();
    }

    @Test
    void createOrdersTest() throws Exception {
        Mockito.when(pillotesService.createPilotesService(orderDTO)).thenReturn(orderDTO);
        ResponseEntity<OrderDTO> response = pillotesController.createOrders(orderDTO);
        assertNotNull(response);
    }

    @Test
    void getAllPillotes(){
        Mockito.when(pillotesService.searchPillotes(Mockito.anyMap())).thenReturn(response);
        ResponseEntity<Map<String,Object>> listResponse = pillotesController.searchPillotes(request);
        assertNotNull(listResponse);
    }


}
