package com.tui.proof.util;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@Data
@ConfigurationProperties("filters")
public class PillotesFilteringProperties {

    private String firstNameKey;
    private String lastNameKey;
    private String emailKey;
    private String telephoneKey;
}
