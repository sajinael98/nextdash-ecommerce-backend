package com.saji.dashboard_backend.shared.specifications;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.springframework.data.jpa.domain.Specification;

import com.saji.dashboard_backend.shared.dtos.ValueFilter;

import jakarta.persistence.criteria.Predicate;

public class EntitySpecification<T> {
    public static <T> Specification<T> findList(Collection<ValueFilter> valueFilters) {
        return (root, cq, cb) -> {
            if (valueFilters == null || valueFilters.isEmpty()) {
                return cb.conjunction(); // Return an always-true predicate if filters are empty
            }

            Predicate finalPredicate = cb.conjunction(); // Start with a true predicate
            for (ValueFilter condition : valueFilters) {
                if (condition.getField() != null && condition.getValue() != null) {
                    System.out.println("Filtering by: " + condition.getField() + " = " + condition.getValue());
                    Predicate predicate;
                    if (condition.getOperator().equals("eq")) {
                        String[] splitedString = condition.getField().split("_");
                        if (splitedString.length == 1) {
                            predicate = cb.equal(root.get(condition.getField()), condition.getValue());
                        } else {
                            predicate = cb.equal(root.get(splitedString[0]).get(splitedString[1]),
                                    condition.getValue());
                        }
                    } else if (condition.getOperator().equals("contains")) {
                        predicate = cb.like(cb.lower(root.get(condition.getField())), "%" + condition.getValue() + "%");
                    } else if (condition.getOperator().equals("in")) {
                        predicate = root.get(condition.getField()).in((ArrayList) condition.getValue());
                    } else {
                        throw new IllegalArgumentException("invalid operation: " + condition.getOperator());
                    }

                    // Combine predicates based on filter type
                    finalPredicate = cb.and(finalPredicate, predicate);
                } else {
                    System.out.println("Invalid condition: " + condition);
                }
            }

            return finalPredicate;
        };
    }
}