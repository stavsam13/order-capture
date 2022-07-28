package com.tui.proof.util;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Data
@ConfigurationProperties("filters")
public class PillotesFilteringProperties {

    private String clientIdKey;
    private String firstNameKey;
    private String lastNameKey;
    private String emailKey;
    private String telephoneKey;
    private String joinColumnOrderKey;
    private String pageNumberKey;
    private String pageSizeKey;
    private String ascSortingKey;
    private String descSortingKey;
    private List<String> filtersKey;

}
