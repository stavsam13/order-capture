package com.tui.proof.ws.controller;

import com.tui.proof.model.OrderDTO;
import com.tui.proof.services.PillotesService;
import com.tui.proof.util.ApisEndPoints;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import lombok.extern.log4j.Log4j2;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;

@Log4j2
@RestController
@RequestMapping(path = ApisEndPoints.BASE_ENDPOINT)
public class PillotesController {

  private static final Logger LOGGER = LoggerFactory.getLogger(PillotesController.class);

  @Autowired
  PillotesService pillotesService;

  @PostMapping(value = ApisEndPoints.SEARCH_ORDER_ENDPOINT)
  @SecurityRequirement(name = "user")
  public ResponseEntity<Map<String,Object>> searchPillotes(@RequestBody Map<String, Object> request) {
    LOGGER.info("Orders Retrieval");
    final Map<String,Object> responseList = pillotesService.searchPillotes(request);
    LOGGER.info("List of orders retrieved{}",responseList);
    return new ResponseEntity<>(responseList,HttpStatus.OK);
  }

  @PostMapping(value = ApisEndPoints.CREATE_ENDPOINT)
  public ResponseEntity<OrderDTO> createOrders(@Valid @RequestBody OrderDTO orderDTO) throws Exception {
    LOGGER.info("We have started creating your Pillotes!");
    final OrderDTO response = pillotesService.createPilotesService(orderDTO);
    LOGGER.info("Pillotes order have been created successfully!");
    return new ResponseEntity<>(response, HttpStatus.CREATED);
  }

  @PutMapping(value = ApisEndPoints.UPDATE_ORDER_ENDPOINT)
  public ResponseEntity<OrderDTO> updateOrder(@RequestParam Long orderNumber,
                                              @Valid @RequestBody OrderDTO orderDTO) throws Exception {
    LOGGER.info("We have started updating your Pillotes Orders!");
    final OrderDTO response = pillotesService.updatePilotesOrder(orderNumber,orderDTO);
    LOGGER.info("Pillotes order have been updated successfully!");
    return new ResponseEntity<>(response,HttpStatus.OK);}

}
