package com.tui.proof.service;

import com.tui.proof.entity.OrderEntity;
import com.tui.proof.ws.controller.PillotesController;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class ServiceTest {

    @InjectMocks
    private PillotesController pillotesController;

    @Mock
    private OrderEntity orderEntity;
}
