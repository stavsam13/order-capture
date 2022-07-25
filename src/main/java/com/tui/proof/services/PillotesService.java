package com.tui.proof.services;

import com.tui.proof.model.Order;
import com.tui.proof.repositories.CustomerRepo;
import com.tui.proof.util.PillotesFilteringProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@EnableConfigurationProperties(PillotesFilteringProperties.class)
public class PillotesService {

    @Autowired
    CustomerRepo customerRepo;
    @Autowired
    PillotesFilteringProperties pillotesFilteringProperties;
    public List<Order> createPilotesService(List<Order> numberOfPilotes) {
    }

    public static <T> Specification<T> createGreaterThanSpec(String key ,Object value) {
        return ((root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.
                gt(root.get(key),Integer.valueOf(String.valueOf(value))));
    }

    public static <T> Specification<T> createLikeSpec(String key ,Object value) {
        return ((root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.
                like(root.get(key),"%" + value +"%"));
    }

    public static <T> Specification<T> createEqualsSpec(String key ,Object value) {
        return ((root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.
                equal(root.get(key), value));
    }

    public List<Order> searchPillotes(Map<String, Object> request) {
        Map<String,Object> filterOption = (Map<String, Object>) request.get("filter");
        // I am initilizing my query
        Specification <Order> specification = getSpecification(filterOption);

    }

    private Specification<Order> getSpecification(Map<String,Object> filterOption){
        Specification <Order> specification = Specification.
                where(createGreaterThanSpec(pillotesFilteringProperties.getClientIdKey(), 0));
        for(String filter : pillotesFilteringProperties.getFiltersKey()){
            Object value = filterOption.get(filter);
            if (value !=null){
                if(pillotesFilteringProperties.getFirstNameKey().equals(filter)) {
                    specification = specification.
                            and(createLikeSpec(pillotesFilteringProperties.getFirstNameKey(), value));
                }if(pillotesFilteringProperties.getLastNameKey().equals(filter)) {
                    specification = specification.
                            and(createLikeSpec(pillotesFilteringProperties.getLastNameKey(), value));
                }if(pillotesFilteringProperties.getEmailKey().equals(filter)) {
                    specification = specification.
                            and(createEqualsSpec(pillotesFilteringProperties.getEmailKey(), value));
                }if(pillotesFilteringProperties.getTelephoneKey().equals(filter)) {
                    specification = specification.
                            and(createEqualsSpec(pillotesFilteringProperties.getTelephoneKey(), value));
                }
            }
            return specification;
        }
    }
}
