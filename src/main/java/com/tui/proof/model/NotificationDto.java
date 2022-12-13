package com.tui.proof.model;

import lombok.Data;

@Data
public class NotificationDto {
    private String notificationType;
    private String recipient;
    private String firstName;
    private String lastName;
    private String subject;
    private double orderTotal;

}
