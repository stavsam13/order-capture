package com.tui.proof.services;

import com.tui.proof.model.Order;
import com.tui.proof.repositories.CustomerRepo;
import com.tui.proof.util.PillotesFilteringProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@EnableConfigurationProperties(PillotesFilteringProperties.class)
public class PillotesService {

    @Autowired
    CustomerRepo customerRepo;
    public List<Order> createPilotesService(List<Order> numberOfPilotes) {
    }

    public List<Order> searchPillotes(Map<String, Object> request) {
        Map<String,Object> filterOption = (Map<String, Object>) request.get("filter");



        List<Order> customerOrders = customerRepo.findByLastName(filter);
    }
}
