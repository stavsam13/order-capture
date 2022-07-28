package com.tui.proof.ws.controller;

import com.tui.proof.model.OrderDTO;
import com.tui.proof.services.PillotesService;
import com.tui.proof.util.ApisEndPoints;
import lombok.extern.log4j.Log4j2;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Log4j2
@RestController
@RequestMapping(path = ApisEndPoints.BASE_ENDPOINT)
public class PillotesController {

  private static final Logger LOGGER = LoggerFactory.getLogger(PillotesController.class);

  @Autowired
  PillotesService pillotesService;

  @GetMapping(value = ApisEndPoints.SEARCH_ORDER_ENDPOINT)
  public ResponseEntity<Map<String,Object>> searchPillotes(@RequestBody Map<String, Object> request) {
    LOGGER.info("Orders Retrieval");
    final Map<String,Object> responseList = pillotesService.searchPillotes(request);
    LOGGER.info("List of orders retrieved{}",responseList);
    return new ResponseEntity<>(responseList,HttpStatus.OK);
  }

  @PostMapping(value = ApisEndPoints.CREATE_ENDPOINT)
  public ResponseEntity<OrderDTO> createOrders(@RequestBody OrderDTO orderDTO) throws Exception {
    LOGGER.info("We have started creating your Pillotes!");
    final OrderDTO response = pillotesService.createPilotesService1(orderDTO);
    LOGGER.info("Pillotes order have been created successfully!");
    return new ResponseEntity<>(response, HttpStatus.OK);
  }
}
