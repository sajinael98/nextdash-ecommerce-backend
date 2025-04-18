package com.saji.dashboard_backend.shared.specifications;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.data.jpa.domain.Specification;

import com.saji.dashboard_backend.shared.dtos.ValueFilter;

import jakarta.persistence.criteria.Path;
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
                    Predicate predicate;
                    Path<Object> field = (Path<Object>) root;
                    String[] splitedString = condition.getField().split("\\.");
                    for (String part : splitedString) {
                        field = field.get(part);
                    }

                    if (condition.getOperator().equals("eq")) {
                        predicate = cb.equal(
                                field,
                                condition.getValue());
                    } else if (condition.getOperator().equals("contains")) {
                        predicate = cb.like(cb.lower((Path) field), "%" + condition.getValue() + "%");
                    } else if (condition.getOperator().equals("in")) {
                        predicate = field.in((ArrayList) condition.getValue());
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