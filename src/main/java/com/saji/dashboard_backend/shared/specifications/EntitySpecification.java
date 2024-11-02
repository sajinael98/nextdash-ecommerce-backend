package com.saji.dashboard_backend.shared.specifications;

import java.util.Collection;

import org.springframework.data.jpa.domain.Specification;

import com.saji.dashboard_backend.shared.dtos.ValueFilter;

import jakarta.persistence.criteria.Predicate;

public class EntitySpecification<T> {
    public static <T> Specification<T> findList(Collection<ValueFilter> valueFilters) {
        return (root, cq, cb) -> {
            if (valueFilters == null || valueFilters.isEmpty()) {
                return cb.conjunction();  // Return an always-true predicate if filters are empty
            }

            Predicate finalPredicate = cb.conjunction(); // Start with a true predicate
            
            for (ValueFilter condition : valueFilters) {
                if (condition.getField() != null && condition.getValue() != null) {
                    System.out.println("Filtering by: " + condition.getField() + " = " + condition.getValue());
                    Predicate predicate = cb.equal(root.get(condition.getField()), condition.getValue());
                    finalPredicate = cb.and(finalPredicate, predicate);
                } else {
                    System.out.println("Invalid condition: " + condition);
                }
            }
            
            return finalPredicate;
        };
    }
}